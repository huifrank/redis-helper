package com.huifrank.core.executor.ops;

import com.huifrank.core.CacheIndexType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DelOps implements CacheOps{

    String expression;

    String name;

    CacheIndexType cacheIndexType;

    public DelOps(String expression){
        this.expression = expression;
    }




}
