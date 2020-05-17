package com.huifrank.core.pojo.expression;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DelExpression extends SoloExpression{




    @Override
    public Type ExpressionType() {
        return Type.DEL;
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
