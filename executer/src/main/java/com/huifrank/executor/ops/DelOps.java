package com.huifrank.executor.ops;

import com.huifrank.common.CacheIndexType;
import com.huifrank.common.pojo.expression.SoloExpression;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DelOps implements CacheOps{

    SoloExpression getExpression;

    String name;

    CacheIndexType cacheIndexType;

    public DelOps(SoloExpression getExpression){
        this.getExpression = getExpression;
    }




}
