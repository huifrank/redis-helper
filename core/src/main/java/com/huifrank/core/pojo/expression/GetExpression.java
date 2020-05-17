package com.huifrank.core.pojo.expression;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class GetExpression extends SoloExpression{



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



    @Override
    public Type ExpressionType() {
        return Type.GET;
    }
}
