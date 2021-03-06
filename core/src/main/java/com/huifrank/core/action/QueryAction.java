package com.huifrank.core.action;

import com.huifrank.annotation.BufferEntity;
import com.huifrank.annotation.CacheFor;
import com.huifrank.annotation.action.Query;
import com.huifrank.core.CacheIndexType;
import com.huifrank.core.context.CacheContext;
import com.huifrank.core.executor.*;
import com.huifrank.core.executor.impl.PutExe4Test;
import com.huifrank.core.executor.impl.QueryExe4Test;
import com.huifrank.core.executor.ops.PutOps;
import com.huifrank.core.executor.ops.QueryOps;
import com.huifrank.core.executor.ops.Values;
import com.huifrank.core.pojo.CacheIndex;
import com.huifrank.core.pojo.ParamMap;
import com.huifrank.core.pojo.Result;
import com.huifrank.core.pojo.expression.GetExpression;
import com.huifrank.core.pojo.expression.PutExpression;
import com.huifrank.core.pojo.term.CacheTerm;
import com.huifrank.core.pojo.term.ReflectTerm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 查询的切面
 * 先查缓存 -> 缓存为空 -> 执行原方法  -> 返回值加缓存
 */
@Aspect
@Service
@Slf4j
public class QueryAction {




    QueryOpsExe queryOpsExe = QueryExe4Test.getInstance();

    PutOpsExe  putOpsExe = PutExe4Test.getInstance();

    CacheContext cacheContext = new CacheContext();


    @Around(value = "@annotation(com.huifrank.annotation.action.Query)")
    public Object doQueryAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodCode = "query@" + cacheContext.getMethodSignature(method);

        Class returnType = signature.getReturnType();

        CacheFor cacheFor = method.getAnnotation(CacheFor.class);
        Query query = method.getAnnotation(Query.class);
        //缓存实体
        Class entity = cacheFor.bufferEntity();
        BufferEntity bufferEntity = (BufferEntity) entity.getAnnotation(BufferEntity.class);
        //索引前缀
        String prefix = bufferEntity.keyPrefix();

        Result result = new Result(entity,query.result());

        List<CacheIndex> cacheIndices = cacheContext.getCacheIndex(entity);
        List<ParamMap> paramMaps = cacheContext.getParamMaps(method,methodCode,query.where());


        GetExpression expression = decideQueryCachePlan(cacheIndices, paramMaps, result,prefix);


        Object execute = executeGet(expression,joinPoint.getArgs());

        //是否需要执行原方法
        if(needProceed(execute)){
            Object proceed = joinPoint.proceed();
            if(proceed != null) {
                //加缓存
                List<PutExpression> putExpressions = decidePutCachePlan(prefix, result, proceed, cacheIndices);
                setExpireTimesIfNeed(putExpressions,bufferEntity.expireIn());
                executePut(putExpressions,proceed);
            }

            return proceed;

        }

        return execute;

    }

    /**
     * 只有返回完整对象的情况下才加缓存
     * @param result
     * @param execute
     * @return
     */
    private List<PutExpression> decidePutCachePlan(String prefix,Result result,Object execute,List<CacheIndex> cacheIndices){

        if(StringUtils.isBlank(result.getProperty())){

            return cacheIndices.stream().map(index -> {
                PutExpression putExpression = new PutExpression();
                switch (index.getIndexType()){
                    case NormalIndex:
                        GetExpression nBefore = new GetExpression();
                        nBefore.setCacheTerm(new CacheTerm().setValueIndex("0"));
                        putExpression.setKeyCacheTerm(new CacheTerm(prefix+ CacheContext.CACHE_SPLIT+index.getName()+CacheContext.CACHE_SPLIT).setBefore(nBefore).setRefBeforeName(index.getName()));
                        putExpression.setName(index.getName());
                        putExpression.setValueTerm(new ReflectTerm().setFieldName(index.getName()).setValueIndex("0"));
                        return putExpression;
                    case ClusterIndex:
                        GetExpression cBefore = new GetExpression();
                        cBefore.setCacheTerm(new CacheTerm().setValueIndex("0"));
                        putExpression.setKeyCacheTerm(new CacheTerm(prefix+ CacheContext.CACHE_SPLIT+index.getName()+CacheContext.CACHE_SPLIT).setBefore(cBefore).setRefBeforeName(index.getName()));
                        putExpression.setName(index.getName());
                        putExpression.setValueTerm(new ReflectTerm().setValueIndex("0"));
                        return putExpression;

                    default: throw new RuntimeException("不支持的索引类型");
                }

            }).collect(Collectors.toList());

        }

        return Collections.EMPTY_LIST;

    }



    /**
     * 1 查询整个缓存对象  --> 入参关联聚集索引
     * 2 查询部分缓存属性  --> 入参为普通索引
     *                  \_---> 普通索引命中
     *                      \_---> 直接普通索引取到结果
     *                      -> 无普通索引命中
     *                      \_---> 普通索引关联到聚集索引
     *                  --> 入参为聚集索引
     *                  \_---> 聚集索引取结果
     */
    private GetExpression decideQueryCachePlan(List<CacheIndex> cacheIndices,List<ParamMap> params,Result result,String prefix){


        //如果有多个查询入参 选取一个由关联索引的
        ParamMap param = null;
        Optional<CacheIndex> relateIndex = Optional.empty();
        for(ParamMap cur : params) {
            relateIndex = cacheIndices.stream().filter(index -> index.getName().equals(cur.getName())).findFirst();
            if(relateIndex.isPresent()){
                param = cur;
                break;
            }
        }

        CacheIndex cacheIndex = relateIndex.orElseThrow(()->new RuntimeException("入参未关联索引"));
        GetExpression getExpression;
        switch (cacheIndex.getIndexType()){
            case ClusterIndex:
                    getExpression = clusterIndex(cacheIndex,param,prefix);
                    break;
                case NormalIndex:
                    getExpression = normalIndex(cacheIndices,cacheIndex,param,prefix,result);
                    break;
            default: throw new RuntimeException("不支持的索引类型");

        }
        //根据返回结果

        return getExpression;

    }

    public GetExpression clusterIndex(CacheIndex cacheIndex , ParamMap param,String prefix){
        String normal = prefix+ CacheContext.CACHE_SPLIT+cacheIndex.getName()+CacheContext.CACHE_SPLIT;

        GetExpression before = new GetExpression();
        before.setCacheTerm(new CacheTerm(normal).setValueIndex(param.getIndex()))
                .setCacheIndexType(CacheIndexType.ClusterIndex)
                .setName(param.getName());

        return before;

    }

    /**
     *
     * @param cacheIndices
     * @param cacheIndex
     * @param param
     * @param prefix 索引前缀
     * @param result 用来判断方法返回值是整个对象，还是部分属性
     * @return
     */
    public GetExpression normalIndex(List<CacheIndex>  cacheIndices,CacheIndex cacheIndex,ParamMap param,String prefix,Result result){

        CacheIndex cluster = cacheIndices.stream().filter(c -> c.getName().equals(cacheIndex.getRefIndex())).findFirst().orElseThrow(()->new RuntimeException("普通索引必须关联有聚簇索引"));
        String clusterName = prefix+ CacheContext. CACHE_SPLIT+cluster.getName() + CacheContext.CACHE_SPLIT;
        GetExpression after = new GetExpression();

        CacheTerm cacheTerm = new CacheTerm(clusterName);
        after.setCacheTerm(cacheTerm)
                .setCacheIndexType(CacheIndexType.ClusterIndex)
                .setName(cluster.getName());

        String normal = prefix+ CacheContext.CACHE_SPLIT+cacheIndex.getName()+CacheContext.CACHE_SPLIT;
        GetExpression before = new GetExpression().setMapping(cacheIndex.getMapping()).setCacheStructure(cacheIndex.getStructure());
        before.setCacheTerm(new CacheTerm(normal).setValueIndex(param.getIndex()))
                .setCacheIndexType(CacheIndexType.NormalIndex)
                .setName(param.getName());

        //属性为空  或  索引关联的属性不是要取的值时，需要关联到聚集索引
        if(StringUtils.isBlank(result.getProperty()) || !StringUtils.equals(cacheIndex.getRefIndex(),result.getPropertyName())){

            cacheTerm.setBefore(before);
            return after;
        }

        return before;
    }


    private boolean needProceed(Object cacheResult){
        //todo impl
        return true;
    }
    private void setExpireTimesIfNeed(List<PutExpression> expressions,long expireIn){
        if(expireIn > 0){
            expressions.forEach( ex -> ex.setExpireIn(expireIn));
        }
    }

    private void executePut(List<PutExpression> putExpression,Object args) {
        List<PutOps> opsList = putExpression.stream().map(o-> new PutOps(o)).collect(Collectors.toList());
        putOpsExe.execute(opsList,new Values().setArgsList(Collections.singletonList(args)));
    }


    private Object executeGet(GetExpression getExpressions,Object [] args) {

        List<Object> execute = queryOpsExe.execute(Collections.singletonList(new QueryOps(getExpressions)),new Values().setArgsList(Arrays.asList(args)));

        return execute;

    }
}
