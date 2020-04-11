package com.huifrank.core.resolver;

import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.ParamMap;
import com.huifrank.core.typeHandler.TypeHandler;
import com.huifrank.core.typeHandler.impl.StringTypeHandler;
import org.springframework.boot.autoconfigure.cache.CacheType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        //建立有对应索引的参数(目前还未考虑覆盖索引)
        List<ParamMap> needClear = paramMaps.stream()
                .filter(f -> indexMap.containsKey(f.getName()))
                .collect(Collectors.toList());


        List<CacheIndex> cluster = indexMap.entrySet().stream()
                .filter(p -> CacheIndexType.ClusterIndex.equals(p.getValue().getIndexType()))
                .map(p -> p.getValue()).collect(Collectors.toList());

        if(cluster.size() > 1 ){
            throw new RuntimeException("目前一个类仅支持设置一个聚簇索引");
        }
        List<String> keys = paramMaps.stream().map(p -> normalIndex(prefix, cluster.get(0), p)).collect(Collectors.toList());



        return keys;
    }

    private String normalIndex( String prefix,CacheIndex clusterIndex,ParamMap curParam){

        String normal =prefix+ CACHE_SPLIT+curParam.getName()+CACHE_SPLIT+curParam.getValue();
        String cluster =prefix+  CACHE_SPLIT+clusterIndex.getName()+CACHE_SPLIT+"("+normal+")";

        return cluster;


    }
}
