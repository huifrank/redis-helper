package com.huifrank.common.pojo.expression;

import com.huifrank.common.pojo.term.CacheTerm;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 由两个参数组成的表达式
 * set xx xxx
 */
@Data
@Accessors(chain = true)
public abstract class BinaryExpression implements Expression{


    private CacheTerm keyCacheTerm;

    private String name;

    @Override
    public String toString() {

        if (getKeyCacheTerm().getBefore() != null) {
            if (getKeyCacheTerm().getRefBeforeName() != null) {
                return "->" + getKeyCacheTerm() + "(" + getKeyCacheTerm().getBefore() + ")." + getKeyCacheTerm().getRefBeforeName();
            }
            return "->" + getKeyCacheTerm() + "(" + getKeyCacheTerm().getBefore() + ")";
        } else {
            return "->" + getKeyCacheTerm();
        }
    }

}
