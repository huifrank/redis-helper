package com.huifrank.core.context;

import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.resolver.BufferEntityResolver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheContext {

    public static final String CACHE_SPLIT =":";

    BufferEntityResolver entityResolver = new BufferEntityResolver();

    private static Map<String, List<CacheIndex>> entityIndexCache = new ConcurrentHashMap<>();

    public List<CacheIndex> getCacheIndex(Class entityClazz){
        List<CacheIndex> cacheIndices = entityIndexCache.get(entityClazz.getName());

        if(cacheIndices == null){

            List<CacheIndex> resolver = entityResolver.resolverEntity(entityClazz);
            entityIndexCache.put(entityClazz.getName(),resolver);
            return resolver;
        }
        return cacheIndices;
    }


}
