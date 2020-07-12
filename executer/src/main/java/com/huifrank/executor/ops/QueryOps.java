package com.huifrank.executor.ops;

import com.huifrank.common.CacheIndexType;
import com.huifrank.common.pojo.expression.SoloExpression;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueryOps implements CacheOps {


    SoloExpression getExpression;

    String name;

    CacheIndexType cacheIndexType;

    public QueryOps(SoloExpression getExpression){
        this.getExpression = getExpression;
    }

}

