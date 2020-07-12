package com.huifrank.executor.ops;

import com.huifrank.common.CacheIndexType;
import com.huifrank.common.pojo.expression.BinaryExpression;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PutOps implements CacheOps{

    BinaryExpression getExpression;

    String name;

    CacheIndexType cacheIndexType;

    public PutOps(BinaryExpression getExpression){
        this.getExpression = getExpression;
    }




}
