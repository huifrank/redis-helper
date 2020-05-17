package com.huifrank.core.executor.ops;

import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.expression.BinaryExpression;
import com.huifrank.core.pojo.expression.SoloExpression;
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
