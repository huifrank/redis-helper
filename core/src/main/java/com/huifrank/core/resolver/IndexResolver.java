package com.huifrank.core.resolver;

import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.Expression;
import com.huifrank.core.pojo.ParamMap;
import com.huifrank.core.typeHandler.TypeHandler;
import com.huifrank.core.typeHandler.impl.StringTypeHandler;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IndexResolver {

    private static String CACHE_SPLIT =":";

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

        List<Expression> byNormalKeys = normal.stream().map(p -> normalIndex(prefix, clusterType, p,indexMap))
                .collect(Collectors.toList());

        List<Expression> byClusterKeys = cluster.stream().map(p -> clusterIndex(prefix, clusterType, p))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


        List<Expression> res = new ArrayList<>(byNormalKeys.size()+byClusterKeys.size());
        res.addAll(byClusterKeys);
        res.addAll(byNormalKeys);

        return res;
    }

    private Expression normalIndex(String prefix,List<CacheIndex> clusterIndex,ParamMap curParam,Map<String, CacheIndex> indexMap){

        CacheIndex cacheIndex = indexMap.get(curParam.getName());
        CacheIndex clusterType = clusterIndex.stream().collect(Collectors.toMap(  CacheIndex::getName, Function.identity()))
                .get(cacheIndex.getRefIndex());

        String normal = prefix+ CACHE_SPLIT+curParam.getName()+CACHE_SPLIT+curParam.getValue();
        String cluster = prefix+  CACHE_SPLIT+clusterType.getName()+CACHE_SPLIT;

        Expression before = new Expression();
        before.setTerm(normal)
                .setCacheIndexType(CacheIndexType.NormalIndex)
                .setName(curParam.getName());
        Expression expression = new Expression();
        expression.setTerm(cluster)
                .setName(clusterType.getName())
                .setCacheIndexType(CacheIndexType.ClusterIndex)
                .setBefore(before);


        return expression;
    }

    private List<Expression> clusterIndex( String prefix,List<CacheIndex> clusterIndex,ParamMap curParam){
        Map<String, CacheIndex> clusterMap = clusterIndex.stream().collect(Collectors.toMap(CacheIndex::getName, Function.identity()));
        CacheIndex clu = clusterMap.get(curParam.getName());
        String cluster =prefix+ CACHE_SPLIT+clu.getName()+CACHE_SPLIT+curParam.getValue();
        Expression expression = new Expression();
        expression.setTerm(cluster)
                .setName(clu.getName())
                .setCacheIndexType(CacheIndexType.ClusterIndex);

        return Collections.singletonList(expression);

    }
}
