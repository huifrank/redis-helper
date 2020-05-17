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


    private Term term;

    private String name;

    @Override
    public String toString() {

        if (getTerm().getBefore() != null) {
            if (getTerm().getRefBeforeName() != null) {
                return "->" + getTerm() + "(" + getTerm().getBefore() + ")." + getTerm().getRefBeforeName();
            }
            return "->" + getTerm() + "(" + getTerm().getBefore() + ")";
        } else {
            return "->" + getTerm();
        }
    }

}
