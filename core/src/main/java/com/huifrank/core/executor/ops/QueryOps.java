package com.huifrank.core.executor.ops;

import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.expression.SoloExpression;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueryOps implements CacheOps{


    SoloExpression getExpression;

    String name;

    CacheIndexType cacheIndexType;

    public QueryOps(SoloExpression getExpression){
        this.getExpression = getExpression;
    }

}

