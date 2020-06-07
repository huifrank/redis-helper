package com.huifrank.core.action;

import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.action.Update;
import com.huifrank.core.context.CacheContext;
import com.huifrank.core.executor.impl.DeleteExe4Test;
import com.huifrank.core.executor.DeleteOpsExe;
import com.huifrank.core.executor.ops.DelOps;
import com.huifrank.core.executor.ops.Values;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.ParamMap;
import com.huifrank.core.pojo.expression.SoloExpression;
import com.huifrank.core.resolver.IndexResolver;
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

/**
 * 更新缓存
 * 还没写完
 */
@Aspect
@Service
@Slf4j
public class UpdateAction {



    IndexResolver indexResolver = new IndexResolver();

    DeleteOpsExe deleteOpsExe = DeleteExe4Test.getInstance();

    CacheContext cacheContext = new CacheContext();


    @Around(value = "@annotation(com.huifrank.annotation.action.Update)")
    public Object doUpdateAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodCode = "update@" + cacheContext.getMethodSignature(method);

        List<SoloExpression> getExpressions = cacheContext.getExpressionsCacheOnly(methodCode);

        if(getExpressions != null){
            return proceedAndExecute(getExpressions,joinPoint.getArgs(),joinPoint);
        }
        //未命中缓存，重新解析
        CacheFor cacheFor = method.getAnnotation(CacheFor.class);
        Update update = method.getAnnotation(Update.class);
        //缓存实体
        Class entity = cacheFor.bufferEntity();
        BufferEntity bufferEntity = (BufferEntity) entity.getAnnotation(BufferEntity.class);
        //索引前缀
        String prefix = bufferEntity.keyPrefix();

        List<CacheIndex> cacheIndices = cacheContext.getCacheIndex(entity);
        List<ParamMap> paramMaps = cacheContext.getParamMaps(method,methodCode,update.where());

        Map<String, CacheIndex> indexMap = cacheIndices.stream()
                .collect(Collectors.toMap(CacheIndex::getName, Function.identity()));

        getExpressions = indexResolver.resolverAllIndex(paramMaps, indexMap, prefix);





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
