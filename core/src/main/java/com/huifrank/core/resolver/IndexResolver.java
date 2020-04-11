package com.huifrank.core.resolver;

import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.ParamMap;
import com.huifrank.core.typeHandler.TypeHandler;
import com.huifrank.core.typeHandler.impl.StringTypeHandler;
import org.springframework.boot.autoconfigure.cache.CacheType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<CacheIndex> clusterType = indexMap.entrySet().stream()
                .filter(p -> CacheIndexType.ClusterIndex.equals(p.getValue().getIndexType()))
                .map(p -> p.getValue()).collect(Collectors.toList());

        if(cluster.size() > 1 ){
            throw new RuntimeException("目前一个类仅支持设置一个聚簇索引");
        }
        List<String> byNormalKeys = normal.stream().map(p -> normalIndex(prefix, clusterType.get(0), p)).collect(Collectors.toList());

        List<String> byClusterKeys = cluster.stream().map(p -> clusterIndex(prefix, clusterType.get(0), p)).collect(Collectors.toList());


        List<String> res = new ArrayList<>(byNormalKeys.size()+byClusterKeys.size());
        res.addAll(byClusterKeys);
        res.addAll(byNormalKeys);

        return res;
    }

    private String normalIndex( String prefix,CacheIndex clusterIndex,ParamMap curParam){

        String normal =prefix+ CACHE_SPLIT+curParam.getName()+CACHE_SPLIT+curParam.getValue();
        String cluster =prefix+  CACHE_SPLIT+clusterIndex.getName()+CACHE_SPLIT+"("+normal+")";

        return cluster;
    }

    private String clusterIndex( String prefix,CacheIndex clusterIndex,ParamMap curParam){

        String cluster =prefix+ CACHE_SPLIT+clusterIndex.getName()+CACHE_SPLIT+curParam.getValue();

        return cluster;
    }
}
