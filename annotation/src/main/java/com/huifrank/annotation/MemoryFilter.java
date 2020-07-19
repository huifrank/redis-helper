package com.huifrank.annotation;

import com.huifrank.common.pojo.ParamMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 针对查询入参有多个查询参数的情况下：
 *  如果根据其中一个入参去关联索引查询
 *  那么 在查询缓存后，就需要再用其他参数在内存中额外做一次过滤
 */
public interface MemoryFilter<T> {

    List<T> doFilter(List<T> list,List<Param> params);

    @Data
    @AllArgsConstructor
    class Param{
        Object value;

        String attributeName;
    }

    static List<Param> ofParams(Object[] args,List<ParamMap> paramMaps){

        paramMaps.stream().map(m->{
            Object arg = args[m.getObjIndex()];
            if(m.getObjName() != null){
                try {
                    //调用get方法取属性值
                    String getMethod = "get"+m.getObjName().substring(0, 1).toUpperCase() + m.getObjName().substring(1);
                    Method method = arg.getClass().getMethod(getMethod);
                    Object invoke = method.invoke(arg);
                    return new Param(invoke,m.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new Param(arg,m.getName());

        });

        return Collections.emptyList();
    }

    class DefaultFilter implements MemoryFilter{

        @Override
        public List doFilter(List list, List params) {
            return list;
        }
    }
}
