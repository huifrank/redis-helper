package com.huifrank.core.action;


import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.CacheFor;
import com.huifrank.core.BufferEntityResolver;
import com.huifrank.core.pojo.CacheIndex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Parameter;
import java.util.List;


@Aspect
@Service
public class EvictResolver {

    BufferEntityResolver entityResolver = new BufferEntityResolver();

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


        return null;
    }


}
