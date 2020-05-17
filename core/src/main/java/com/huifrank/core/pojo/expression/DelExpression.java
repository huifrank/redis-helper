package com.huifrank.core.pojo.expression;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DelExpression extends SoloExpression{


    private Term term;

    private String name;


    @Override
    public Type ExpressionType() {
        return Type.DEL;
    }
}
