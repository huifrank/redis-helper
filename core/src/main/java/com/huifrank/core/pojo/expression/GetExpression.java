package com.huifrank.core.pojo.expression;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class GetExpression extends SoloExpression{





    @Override
    public Type ExpressionType() {
        return Type.GET;
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
