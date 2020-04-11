package com.huifrank.core.action;


import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.CacheFor;
import com.huifrank.core.resolver.BufferEntityResolver;
import com.huifrank.core.resolver.IndexResolver;
import com.huifrank.core.resolver.ParamsResolver;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.ParamMap;
import com.huifrank.core.typeHandler.TypeHandler;
import com.huifrank.core.typeHandler.impl.StringTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Aspect
@Service
@Slf4j
public class EvictAction {

    BufferEntityResolver entityResolver = new BufferEntityResolver();

    ParamsResolver paramsResolver = new ParamsResolver();

    IndexResolver indexResolver = new IndexResolver();

    static Map<String, TypeHandler> typeHandlers;
    static {
        typeHandlers = new HashMap<>();
        typeHandlers.put("java.lang.String",new StringTypeHandler());
    }

    private static String CACHE_SPLIT =":";

    @Around(value = "@annotation(com.huifrank.annotation.action.Evict)")
    public Object doEvictAdvice(ProceedingJoinPoint joinPoint) throws Throwable {



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
        List<ParamMap> paramMaps = paramsResolver.resolverParameters(parameters,joinPoint.getArgs());

        Map<String, CacheIndex> indexMap = cacheIndices.stream()
                .collect(Collectors.toMap(f -> f.getName(), Function.identity()));


        List<String> collect = indexResolver.resolverAllIndex(paramMaps, indexMap, prefix);


        //执行原方法
        Object proceed = joinPoint.proceed();

        log.info("-----Do Delete------");
        collect.forEach(log::info);
        log.info("-------Delete Finish-------");


        return proceed;
    }


}
