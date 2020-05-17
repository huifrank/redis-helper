package com.huifrank.core.executor.ops;

import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.expression.GetExpression;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DelOps implements CacheOps{

    GetExpression getExpression;

    String name;

    CacheIndexType cacheIndexType;

    public DelOps(GetExpression getExpression){
        this.getExpression = getExpression;
    }




}
