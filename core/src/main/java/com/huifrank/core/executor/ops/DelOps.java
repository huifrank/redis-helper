package com.huifrank.core.executor.ops;

import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.Expression;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DelOps implements CacheOps{

    Expression expression;

    String name;

    CacheIndexType cacheIndexType;

    public DelOps(Expression expression){
        this.expression = expression;
    }




}
