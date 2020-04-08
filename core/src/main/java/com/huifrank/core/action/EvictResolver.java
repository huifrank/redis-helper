package com.huifrank.core.action;


import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.CacheFor;
import com.huifrank.core.BufferEntityResolver;
import com.huifrank.core.ParamsResolver;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.ParamMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Aspect
@Service
public class EvictResolver {

    BufferEntityResolver entityResolver = new BufferEntityResolver();

    ParamsResolver paramsResolver = new ParamsResolver();

    @Around(value = "@annotation(com.huifrank.annotation.action.Evict)")
    public Object doEvictAdvice(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        CacheFor cacheFor = signature.getMethod().getAnnotation(CacheFor.class);

        Class entity = cacheFor.bufferEntity();

        BufferEntity bufferEntity = (BufferEntity) entity.getAnnotation(BufferEntity.class);
        //索引前缀
        String prefix = bufferEntity.keyPrefix();
        //当前前缀索引列表
        List<CacheIndex> cacheIndices = entityResolver.resolverEntity(entity);

        //解析入参
        Parameter[] parameters = signature.getMethod().getParameters();
        List<ParamMap> paramMaps = paramsResolver.resolverParameters(parameters);

        Map<String, CacheIndex> indexMap = cacheIndices.stream()
                .collect(Collectors.toMap(f -> f.getName(), Function.identity()));

        //建立有对应索引的参数(目前还未考虑覆盖索引)
        List<ParamMap> needClear = paramMaps.stream()
                .filter(f -> indexMap.containsKey(f.getName())).collect(Collectors.toList());


        return null;
    }


}