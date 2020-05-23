package com.huifrank.core.pojo.expression;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 由两个参数组成的表达式
 * set xx xxx
 */
@Data
@Accessors(chain = true)
public abstract class BinaryExpression implements Expression{


    private Term keyTerm;

    private String name;

    @Override
    public String toString() {

        if (getKeyTerm().getBefore() != null) {
            if (getKeyTerm().getRefBeforeName() != null) {
                return "->" + getKeyTerm() + "(" + getKeyTerm().getBefore() + ")." + getKeyTerm().getRefBeforeName();
            }
            return "->" + getKeyTerm() + "(" + getKeyTerm().getBefore() + ")";
        } else {
            return "->" + getKeyTerm();
        }
    }

}
