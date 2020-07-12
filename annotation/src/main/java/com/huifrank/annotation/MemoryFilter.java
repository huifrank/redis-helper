package com.huifrank.annotation;

import lombok.Data;

import java.util.List;

/**
 * 针对查询入参有多个查询参数的情况下：
 *  如果根据其中一个入参去关联索引查询
 *  那么 在查询缓存后，就需要再用其他参数在内存中额外做一次过滤
 */
public interface MemoryFilter<T> {

    List<T> doFilter(List<T> list,List<Param> params);

    @Data
    class Param{
        Object value;

        String attributeName;

    }
}
