package com.huifrank.core.executor.ops;

import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.expression.GetExpression;
import com.huifrank.core.pojo.expression.SoloExpression;
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
