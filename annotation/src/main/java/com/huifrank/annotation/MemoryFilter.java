package com.huifrank.annotation;

/**
 * 针对查询入参有多个查询参数的情况下：
 *  如果根据其中一个入参去关联索引查询
 *  那么 在查询缓存后，就需要再用其他参数在内存中额外做一次过滤
 */
public interface MemoryFilter<T> {

    T doFilter(T obj);
}
