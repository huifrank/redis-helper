package com.huifrank.core.resolver;

import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.Expression;
import com.huifrank.core.pojo.ParamMap;
import com.huifrank.core.typeHandler.TypeHandler;
import com.huifrank.core.typeHandler.impl.StringTypeHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class IndexResolver {

    private static final String CACHE_SPLIT =":";

    static Map<String, TypeHandler> typeHandlers;
    static {
        typeHandlers = new HashMap<>();
        typeHandlers.put("java.lang.String",new StringTypeHandler());
    }


    public List<Expression> resolverAllIndex(List<ParamMap> paramMaps, Map<String, CacheIndex> indexMap,String prefix){

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

        List<Expression> byNormalKeys = normal.stream().map(p -> normalIndexWithRef(prefix, clusterType, p,indexMap))
                .collect(Collectors.toList());

        List<Expression> byClusterKeys = cluster.stream().map(p -> clusterIndex(prefix, clusterType, p))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


        List<Expression> res = new ArrayList<>();
        res.addAll(byClusterKeys);
        res.addAll(byNormalKeys);

        complementOtherIndex(prefix,res,indexMap);

        return res;
    }


    private void complementOtherIndex(final String prefix,List<Expression> curExp, Map<String, CacheIndex> indexMap){

        Expression first = curExp.stream()
                .filter(c -> CacheIndexType.ClusterIndex.equals(c.getCacheIndexType()))
                .findFirst().orElseThrow(()->new RuntimeException("普通索引必须关联有聚簇索引"));
        List<String> allFieldNames = curExp.stream()
                .map(Expression::getExpNames)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        //过滤出入参中没有覆盖到的其他缓存key
        List<String> needComplement = indexMap.keySet().stream().filter(f -> !allFieldNames.contains(f)).collect(Collectors.toList());


        //补全
        List<Expression> expressions = needComplement.stream().map(name -> {
            CacheIndex cacheIndex = indexMap.get(name);
            ParamMap paramMap = new ParamMap(name, 0, "(" + first + ")." + name, String.class.getName());
            switch (cacheIndex.getIndexType()) {
                case ClusterIndex:
                    return clusterIndex(prefix, Collections.singletonList(cacheIndex), paramMap).get(0);
                case NormalIndex:
                    return normalIndexOnly(prefix, paramMap );
                default:
                    throw new RuntimeException("不支持的索引类型");
            }
        }).collect(Collectors.toList());


        curExp.addAll(expressions);


    }

    private Expression normalIndexOnly(final String prefix,ParamMap curParam){

        TypeHandler typeHandler = typeHandlers.get(curParam.getValueTypeName());

        String normal = prefix+ CACHE_SPLIT+curParam.getName()+CACHE_SPLIT+typeHandler.resolve2String(curParam.getValue());

        Expression before = new Expression();
        before.setTerm(normal)
                .setCacheIndexType(CacheIndexType.NormalIndex)
                .setName(curParam.getName());

        return before;
    }


    private Expression normalIndexWithRef(final String prefix,List<CacheIndex> clusterIndex,ParamMap curParam,Map<String, CacheIndex> indexMap){

        CacheIndex cacheIndex = indexMap.get(curParam.getName());
        CacheIndex clusterType = clusterIndex.stream().collect(Collectors.toMap(  CacheIndex::getName, Function.identity()))
                .get(cacheIndex.getRefIndex());
        String cluster = prefix+  CACHE_SPLIT+clusterType.getName()+CACHE_SPLIT;

        Expression before = normalIndexOnly(prefix,curParam);
        //关联到聚簇索引
        Expression expression = new Expression();
        expression.setTerm(cluster)
                .setName(clusterType.getName())
                .setCacheIndexType(CacheIndexType.ClusterIndex)
                .setBefore(before);


        return expression;
    }

    private List<Expression> clusterIndex(final String prefix,List<CacheIndex> clusterIndex,ParamMap curParam){
        Map<String, CacheIndex> clusterMap = clusterIndex.stream().collect(Collectors.toMap(CacheIndex::getName, Function.identity()));
        CacheIndex clu = clusterMap.get(curParam.getName());
        TypeHandler typeHandler = typeHandlers.get(curParam.getValueTypeName());
        String cluster =prefix+ CACHE_SPLIT+clu.getName()+CACHE_SPLIT+typeHandler.resolve2String(curParam.getValue());
        Expression expression = new Expression();
        expression.setTerm(cluster)
                .setName(clu.getName())
                .setCacheIndexType(CacheIndexType.ClusterIndex);

        return Collections.singletonList(expression);

    }
}
