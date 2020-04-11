package com.huifrank.core.resolver;

import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.CacheIndex;
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


    public List<String> resolverAllIndex(List<ParamMap> paramMaps, Map<String, CacheIndex> indexMap,String prefix){

        List<ParamMap> normal = paramMaps.stream()
                .filter(f -> indexMap.containsKey(f.getName())
                        && CacheIndexType.NormalIndex.equals(indexMap.get(f.getName()).getIndexType()))
                .collect(Collectors.toList());

        List<ParamMap> cluster = paramMaps.stream()
                .filter(f -> indexMap.containsKey(f.getName())
                        && CacheIndexType.ClusterIndex.equals(indexMap.get(f.getName()).getIndexType()))
                .collect(Collectors.toList());
        //找出聚簇索引
        List<CacheIndex> clusterType = indexMap.entrySet().stream()
                .filter(p -> CacheIndexType.ClusterIndex.equals(p.getValue().getIndexType()))
                .map(p -> p.getValue()).collect(Collectors.toList());

        if(cluster.size() > 1 ){
            throw new RuntimeException("目前一个类仅支持设置一个聚簇索引");
        }
        List<String> byNormalKeys = normal.stream().map(p -> normalIndex(prefix, clusterType, p,indexMap))
                .collect(Collectors.toList());

        List<String> byClusterKeys = cluster.stream().map(p -> clusterIndex(prefix, clusterType, p))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


        List<String> res = new ArrayList<>(byNormalKeys.size()+byClusterKeys.size());
        res.addAll(byClusterKeys);
        res.addAll(byNormalKeys);

        return res;
    }

    private String normalIndex(String prefix,List<CacheIndex> clusterIndex,ParamMap curParam,Map<String, CacheIndex> indexMap){

        CacheIndex cacheIndex = indexMap.get(curParam.getName());
        CacheIndex clusterType = clusterIndex.stream().collect(Collectors.toMap(  CacheIndex::getName, Function.identity()))
                .get(cacheIndex.getRefIndex());


        String normal =prefix+ CACHE_SPLIT+curParam.getName()+CACHE_SPLIT+curParam.getValue();
        String cluster =prefix+  CACHE_SPLIT+clusterType.getName()+CACHE_SPLIT+"("+normal+")";

        return cluster;
    }

    private List<String> clusterIndex( String prefix,List<CacheIndex> clusterIndex,ParamMap curParam){
        return clusterIndex.stream().map(clu->{
            String cluster =prefix+ CACHE_SPLIT+clu.getName()+CACHE_SPLIT+curParam.getValue();
            return cluster;
        }).collect(Collectors.toList());


    }
}
