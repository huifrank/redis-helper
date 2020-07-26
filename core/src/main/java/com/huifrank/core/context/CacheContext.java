package com.huifrank.core.context;

import com.huifrank.common.pojo.CacheIndex;
import com.huifrank.common.pojo.ParamMap;
import com.huifrank.common.pojo.expression.SoloExpression;
import com.huifrank.core.resolver.BufferEntityResolver;
import com.huifrank.core.resolver.ParamsResolver;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheContext {

    public static final String CACHE_SPLIT =":";
    public static final String HASHES_SPLIT =",";

    BufferEntityResolver entityResolver = new BufferEntityResolver();

    ParamsResolver paramsResolver = new ParamsResolver();


    private static Map<String, List<CacheIndex>> entityIndexCache = new ConcurrentHashMap<>();
    private static Map<String, List<ParamMap>> paramMapsCache = new ConcurrentHashMap<>();
    private static Map<String,List<SoloExpression>> expressionCache = new ConcurrentHashMap<>();




    public List<CacheIndex> getCacheIndex(Class entityClazz){
        List<CacheIndex> cacheIndices = entityIndexCache.get(entityClazz.getName());

        if(cacheIndices == null){
            cacheIndices = entityResolver.resolverEntity(entityClazz);
            entityIndexCache.put(entityClazz.getName(),cacheIndices);
        }
        return cacheIndices;
    }

    /**
     * 解析入参
     */
    public List<ParamMap> getParamMaps(Method method,String key,String[] where){
        List<ParamMap> paramMaps = paramMapsCache.get(key);
        if(paramMaps == null){
            paramMaps = paramsResolver.resolverParameters(method.getParameters(),where);
            paramMapsCache.put(key,paramMaps);
        }
        return paramMaps;

    }

    public List<SoloExpression> getExpressionsCacheOnly(String key){
        List<SoloExpression> getExpressions = expressionCache.get(key);
        return getExpressions;
    }
    public void addExpressionsCache(String key,List<SoloExpression> getExpressions){
        expressionCache.put(key, getExpressions);
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
