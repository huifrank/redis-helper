package com.huifrank.core.action;


import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.CacheFor;
import com.huifrank.core.context.CacheContext;
import com.huifrank.core.executor.DeleteExe4Test;
import com.huifrank.core.executor.DeleteOpsExe;
import com.huifrank.core.executor.ops.DelOps;
import com.huifrank.core.pojo.Expression;
import com.huifrank.core.resolver.BufferEntityResolver;
import com.huifrank.core.resolver.IndexResolver;
import com.huifrank.core.resolver.ParamsResolver;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.ParamMap;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Aspect
@Service
@Slf4j
public class EvictAction {


    IndexResolver indexResolver = new IndexResolver();

    DeleteOpsExe deleteOpsExe = DeleteExe4Test.getInstance();

    CacheContext cacheContext = new CacheContext();


    @Around(value = "@annotation(com.huifrank.annotation.action.Evict)")
    public Object doEvictAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodCode = cacheContext.getMethodSignature(method);

        List<Expression> expressions = cacheContext.getExpressionsCacheOnly(methodCode);
        if(expressions != null){
            return proceedAndExecute(expressions,joinPoint);
        }

        //未命中缓存，重新解析
        CacheFor cacheFor = method.getAnnotation(CacheFor.class);
        //缓存实体
        Class entity = cacheFor.bufferEntity();

        BufferEntity bufferEntity = (BufferEntity) entity.getAnnotation(BufferEntity.class);
        //索引前缀
        String prefix = bufferEntity.keyPrefix();
        //当前前缀索引列表
        List<CacheIndex> cacheIndices = cacheContext.getCacheIndex(entity);
        Map<String, CacheIndex> indexMap = cacheIndices.stream()
                .collect(Collectors.toMap(CacheIndex::getName, Function.identity()));

        //解析入参属性
        List<ParamMap> paramMaps = cacheContext.getParamMaps(method,methodCode);

        expressions = indexResolver.resolverAllIndex(paramMaps, indexMap, prefix);

        cacheContext.addExpressionsCache(methodCode,expressions);

        return proceedAndExecute(expressions,joinPoint);

    }

    private Object proceedAndExecute( List<Expression> expressions,ProceedingJoinPoint joinPoint) throws Throwable {
        //执行原方法
        Object proceed = joinPoint.proceed();
        List<DelOps> opsList = expressions.stream().map(o-> new DelOps(o)).collect(Collectors.toList());
        deleteOpsExe.execute(opsList);

        return proceed;
    }


}
