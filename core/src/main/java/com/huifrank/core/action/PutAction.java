package com.huifrank.core.action;

import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.action.Put;
import com.huifrank.core.context.CacheContext;
import com.huifrank.executor.impl.PutExe4Test;
import com.huifrank.executor.PutOpsExe;
import com.huifrank.executor.ops.PutOps;
import com.huifrank.executor.ops.Values;
import com.huifrank.common.pojo.CacheIndex;
import com.huifrank.common.pojo.ParamMap;
import com.huifrank.common.pojo.expression.*;
import com.huifrank.common.pojo.term.CacheTerm;
import com.huifrank.common.pojo.term.ReflectTerm;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 写入缓存
 */
@Aspect
@Service
@Slf4j
public class PutAction {




    PutOpsExe  putOpsExe = PutExe4Test.getInstance();

    CacheContext cacheContext = new CacheContext();


    @Around(value = "@annotation(com.huifrank.annotation.action.Put)")
    public Object doPutAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodCode = "put@" + cacheContext.getMethodSignature(method);

        CacheFor cacheFor = method.getAnnotation(CacheFor.class);
        Put put = method.getAnnotation(Put.class);
        //缓存实体
        Class entity = cacheFor.bufferEntity();
        BufferEntity bufferEntity = (BufferEntity) entity.getAnnotation(BufferEntity.class);
        //索引前缀
        String prefix = bufferEntity.keyPrefix();

        List<CacheIndex> cacheIndices = cacheContext.getCacheIndex(entity);
        List<ParamMap> paramMaps = cacheContext.getParamMaps(method,methodCode, new String[]{});
        if(paramMaps.size() > 1){
            throw new RuntimeException("put目前仅支持一个参数");

        }
        ParamMap paramMap = paramMaps.get(0);

        List<PutExpression> expressions = cacheIndices.stream().map(index -> {
            switch (index.getIndexType()) {
                case NormalIndex:
                    return normalIndex(prefix, index, paramMap);
                case ClusterIndex:
                    return clusterIndex(prefix, index, paramMap);
                default:
                    throw new RuntimeException("不支持的索引类型");
            }

        }).collect(Collectors.toList());

        setExpireTimesIfNeed(expressions,bufferEntity.expireIn());

        return proceedAndExecute(expressions,joinPoint.getArgs(),joinPoint);
    }

    private PutExpression clusterIndex(final String prefix, CacheIndex clusterIndex, ParamMap curParam){
        PutExpression putExpression = new PutExpression();
        GetExpression before = new GetExpression();
        before.setCacheTerm(new CacheTerm().setValueIndex("0"));
        String cluster = prefix+ CacheContext.CACHE_SPLIT+clusterIndex.getName()+CacheContext.CACHE_SPLIT;
        putExpression.setKeyCacheTerm(new CacheTerm(cluster).setBefore(before).setRefBeforeName(clusterIndex.getName()))
                .setName(clusterIndex.getName());

        putExpression.setValueTerm(new ReflectTerm().setValueIndex(curParam.getIndex()));

        return putExpression;

    }
    private PutExpression normalIndex(final String prefix, CacheIndex normalIndex, ParamMap curParam){
        PutExpression putExpression = new PutExpression();
        GetExpression before = new GetExpression();
        before.setCacheTerm(new CacheTerm().setValueIndex("0"));
        String cluster = prefix+ CacheContext.CACHE_SPLIT+normalIndex.getName()+CacheContext.CACHE_SPLIT;
        putExpression.setKeyCacheTerm(new CacheTerm(cluster).setBefore(before).setRefBeforeName(normalIndex.getName()))
                .setName(normalIndex.getName());

        putExpression.setValueTerm(new ReflectTerm().setFieldName(normalIndex.getName()).setValueIndex(curParam.getIndex()));
        putExpression.setCacheStructure(normalIndex.getStructure());

        return putExpression;

    }

    private void setExpireTimesIfNeed(List<PutExpression> expressions,long expireIn){
       if(expireIn > 0){
           expressions.forEach( ex -> ex.setExpireIn(expireIn));
       }
    }


    private Object proceedAndExecute(List<PutExpression> getExpressions,Object [] args, ProceedingJoinPoint joinPoint) throws Throwable {
        //执行原方法
        Object proceed = joinPoint.proceed();
        List<PutOps> opsList = getExpressions.stream().map(o-> new PutOps(o)).collect(Collectors.toList());
        putOpsExe.execute(opsList,new Values().setArgsList(Arrays.asList(args)));

        return proceed;
    }
}
