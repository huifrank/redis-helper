package com.huifrank.core.pojo.expression;

import com.huifrank.core.CacheIndexType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * 由单个参数组成的表达式
 * get xxx
 * del xxx
 */
@Data
@Accessors(chain = true)
public abstract class SoloExpression implements Expression {


    private Term term;

    private String name;

    private CacheIndexType cacheIndexType;
}
