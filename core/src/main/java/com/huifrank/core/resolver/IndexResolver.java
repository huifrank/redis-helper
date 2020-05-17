package com.huifrank.core.resolver;

import com.huifrank.core.context.CacheContext;
import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.expression.GetExpression;
import com.huifrank.core.pojo.ParamMap;
import com.huifrank.core.pojo.expression.Term;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class IndexResolver {




    public List<GetExpression> resolverAllIndex(List<ParamMap> paramMaps, Map<String, CacheIndex> indexMap, final String prefix){

        //入参所关联的普通索引
        List<ParamMap> normal = paramMaps.stream()
                .filter(f -> indexMap.containsKey(f.getName())
                        && CacheIndexType.NormalIndex.equals(indexMap.get(f.getName()).getIndexType()))
                .collect(Collectors.toList());

        //入参关联的聚簇索引
        List<ParamMap> cluster = paramMaps.stream()
                .filter(f->indexMap.containsKey(f.getName()))
                .filter(f -> CacheIndexType.ClusterIndex.equals(indexMap.get(f.getName()).getIndexType()))
                .collect(Collectors.toList());
        //找出聚簇索引
        List<CacheIndex> clusterType = indexMap.entrySet().stream()
                .filter(p -> CacheIndexType.ClusterIndex.equals(p.getValue().getIndexType()))
                .map(p -> p.getValue()).collect(Collectors.toList());

        List<GetExpression> byNormalKeys = normal.stream().map(p -> normalIndexWithRef(prefix, clusterType, p,indexMap))
                .collect(Collectors.toList());

        List<GetExpression> byClusterKeys = cluster.stream().map(p -> clusterIndex(prefix, clusterType, p))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


        List<GetExpression> res = new ArrayList<>();
        res.addAll(byClusterKeys);
        res.addAll(byNormalKeys);

        complementOtherIndex(prefix,res,indexMap);

        return res;
    }


    private void complementOtherIndex(final String prefix, List<GetExpression> curExp, Map<String, CacheIndex> indexMap){

        GetExpression first = curExp.stream()
                .filter(c -> CacheIndexType.ClusterIndex.equals(c.getCacheIndexType()))
                .findFirst().orElseThrow(()->new RuntimeException("普通索引必须关联有聚簇索引"));
        List<String> allFieldNames = curExp.stream()
                .map(GetExpression::getExpNames)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        //过滤出入参中没有覆盖到的其他缓存key
        List<String> needComplement = indexMap.keySet().stream().filter(f -> !allFieldNames.contains(f)).collect(Collectors.toList());


        //补全
        List<GetExpression> getExpressions = needComplement.stream().map(name -> {
            CacheIndex cacheIndex = indexMap.get(name);
            switch (cacheIndex.getIndexType()) {
                case ClusterIndex:
                    return complementClusterIndexByCluster(prefix, Collections.singletonList(cacheIndex), first,name).get(0);
                case NormalIndex:
                    return complementNormalIndexByCluster(prefix, first,name );
                default:
                    throw new RuntimeException("不支持的索引类型");
            }
        }).collect(Collectors.toList());


        curExp.addAll(getExpressions);


    }


    private GetExpression complementNormalIndexByCluster(final String prefix, GetExpression before, String refName){


        String normal = prefix+ CacheContext.CACHE_SPLIT+refName+CacheContext.CACHE_SPLIT;

        GetExpression getExpression = new GetExpression();
        getExpression.setTerm(new Term(normal).setBefore(before).setRefBeforeName(refName))
                .setCacheIndexType(CacheIndexType.NormalIndex)
                .setName(refName);

        return getExpression;
    }

    /**
     * 仅普通索引，不做关联
     */
    private GetExpression normalIndexOnly(final String prefix, ParamMap curParam){


        String normal = prefix+CacheContext. CACHE_SPLIT+curParam.getName()+CacheContext.CACHE_SPLIT;

        GetExpression before = new GetExpression();
        before.setTerm(new Term(normal).setValueIndex(curParam.getIndex()))
                .setCacheIndexType(CacheIndexType.NormalIndex)
                .setName(curParam.getName());

        return before;
    }


    /**
     * 根据普通索引，关联至聚簇索引
     * @return
     */
    private GetExpression normalIndexWithRef(final String prefix, List<CacheIndex> clusterIndex, ParamMap curParam, Map<String, CacheIndex> indexMap){

        CacheIndex cacheIndex = indexMap.get(curParam.getName());
        CacheIndex clusterType = clusterIndex.stream().collect(Collectors.toMap(  CacheIndex::getName, Function.identity()))
                .get(cacheIndex.getRefIndex());
        String cluster = prefix+ CacheContext. CACHE_SPLIT+clusterType.getName()+CacheContext.CACHE_SPLIT;

        GetExpression before = normalIndexOnly(prefix,curParam);
        //关联到聚簇索引
        GetExpression getExpression = new GetExpression();
        getExpression.setTerm(new Term(cluster).setBefore(before))
                .setName(clusterType.getName())
                .setCacheIndexType(CacheIndexType.ClusterIndex);


        return getExpression;
    }

    private List<GetExpression> complementClusterIndexByCluster(final String prefix, List<CacheIndex> clusterIndex, GetExpression before, String refName){
        Map<String, CacheIndex> clusterMap = clusterIndex.stream().collect(Collectors.toMap(CacheIndex::getName, Function.identity()));
        CacheIndex clu = clusterMap.get(refName);
        String cluster = prefix+ CacheContext.CACHE_SPLIT+clu.getName()+CacheContext.CACHE_SPLIT;
        GetExpression getExpression = new GetExpression();
        getExpression.setTerm(new Term(cluster).setBefore(before).setRefBeforeName(refName))
                .setName(clu.getName())
                .setCacheIndexType(CacheIndexType.ClusterIndex);

        return Collections.singletonList(getExpression);

    }

    private List<GetExpression> clusterIndex(final String prefix, List<CacheIndex> clusterIndex, ParamMap curParam){
        Map<String, CacheIndex> clusterMap = clusterIndex.stream().collect(Collectors.toMap(CacheIndex::getName, Function.identity()));
        CacheIndex clu = clusterMap.get(curParam.getName());
        String cluster =prefix+ CacheContext.CACHE_SPLIT+clu.getName()+CacheContext.CACHE_SPLIT;
        GetExpression getExpression = new GetExpression();
        getExpression.setTerm(new Term(cluster).setValueIndex(curParam.getIndex()))
                .setName(clu.getName())
                .setCacheIndexType(CacheIndexType.ClusterIndex);

        return Collections.singletonList(getExpression);

    }
}
