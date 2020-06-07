package com.huifrank.core.action;


import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.action.Evict;
import com.huifrank.core.context.CacheContext;
import com.huifrank.core.executor.impl.DeleteExe4Test;
import com.huifrank.core.executor.DeleteOpsExe;
import com.huifrank.core.executor.ops.DelOps;
import com.huifrank.core.executor.ops.Values;
import com.huifrank.core.pojo.expression.SoloExpression;
import com.huifrank.core.resolver.IndexResolver;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.ParamMap;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
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
        String methodCode = "evict@"+cacheContext.getMethodSignature(method);

        List<SoloExpression> getExpressions = cacheContext.getExpressionsCacheOnly(methodCode);
        if(getExpressions != null){
            return proceedAndExecute(getExpressions,joinPoint.getArgs(),joinPoint);
        }

        //未命中缓存，重新解析
        CacheFor cacheFor = method.getAnnotation(CacheFor.class);
        Evict evict = method.getAnnotation(Evict.class);
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
        List<ParamMap> paramMaps = cacheContext.getParamMaps(method,methodCode,evict.where());

        getExpressions = indexResolver.resolverAllIndex(paramMaps, indexMap, prefix);

        cacheContext.addExpressionsCache(methodCode, getExpressions);

        return proceedAndExecute(getExpressions,joinPoint.getArgs(),joinPoint);

    }

    private Object proceedAndExecute(List<SoloExpression> getExpressions,Object [] args, ProceedingJoinPoint joinPoint) throws Throwable {
        //执行原方法
        Object proceed = joinPoint.proceed();
        List<DelOps> opsList = getExpressions.stream().map(o-> new DelOps(o)).collect(Collectors.toList());
        deleteOpsExe.execute(opsList,new Values().setArgsList(Arrays.asList(args)));

        return proceed;
    }


}
