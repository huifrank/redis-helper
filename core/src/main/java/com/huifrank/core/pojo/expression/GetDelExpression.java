package com.huifrank.core.pojo.expression;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetDelExpression extends SoloExpression{
    @Override
    public Type ExpressionType() {
        return Type.GET_DEL;
    }

    public static GetDelExpression of(GetExpression exp){

        GetDelExpression getDelExpression = new GetDelExpression();
        getDelExpression.setCacheIndexType(exp.getCacheIndexType());
        getDelExpression.setName(exp.getName());
        getDelExpression.setTerm(exp.getTerm());

        return getDelExpression;

    }
    public static GetDelExpression of(SoloExpression exp){

        GetDelExpression getDelExpression = new GetDelExpression();
        getDelExpression.setCacheIndexType(exp.getCacheIndexType());
        getDelExpression.setName(exp.getName());
        getDelExpression.setTerm(exp.getTerm());

        return getDelExpression;

    }

    @Override
    public String toString(){
        return super.toString();
    }

}
