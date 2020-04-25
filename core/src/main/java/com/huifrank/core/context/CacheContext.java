package com.huifrank.core.context;

import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.Expression;
import com.huifrank.core.pojo.ParamMap;
import com.huifrank.core.resolver.BufferEntityResolver;
import com.huifrank.core.resolver.ParamsResolver;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheContext {

    public static final String CACHE_SPLIT =":";

    BufferEntityResolver entityResolver = new BufferEntityResolver();

    ParamsResolver paramsResolver = new ParamsResolver();


    private static Map<String, List<CacheIndex>> entityIndexCache = new ConcurrentHashMap<>();
    private static Map<String, List<ParamMap>> paramMapsCache = new ConcurrentHashMap<>();
    private static Map<String,List<Expression>> expressionCache = new ConcurrentHashMap<>();




    public List<CacheIndex> getCacheIndex(Class entityClazz){
        List<CacheIndex> cacheIndices = entityIndexCache.get(entityClazz.getName());

        if(cacheIndices == null){
            cacheIndices = entityResolver.resolverEntity(entityClazz);
            entityIndexCache.put(entityClazz.getName(),cacheIndices);
        }
        return cacheIndices;
    }

    public List<ParamMap> getParamMaps(Method method,String key){
        List<ParamMap> paramMaps = paramMapsCache.get(key);
        if(paramMaps == null){
            paramMaps = paramsResolver.resolverParameters(method.getParameters());
            paramMapsCache.put(key,paramMaps);
        }
        return paramMaps;

    }

    public List<Expression> getExpressionsCacheOnly(String key){
        List<Expression> expressions = expressionCache.get(key);
        return expressions;
    }
    public void addExpressionsCache(String key,List<Expression> expressions){
        expressionCache.put(key, expressions );
    }

    /**
     * 缓存key
     * 参数列表_method.hashCode
     * @param method
     * @return
     */
    public String getMethodSignature(Method method){
        String reduce = Arrays.stream(method.getParameterTypes()).map(Class::getTypeName).reduce("", String::concat);
        String methodCode = String.valueOf(method.hashCode());

        return reduce+"_"+methodCode;
    }


}
