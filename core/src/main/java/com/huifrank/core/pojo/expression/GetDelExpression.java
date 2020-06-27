package com.huifrank.core.pojo.expression;

import com.huifrank.annotation.CacheStructure;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetDelExpression extends SoloExpression{
    @Override
    public Type ExpressionType() {
        return Type.GET_DEL;
    }
    private CacheStructure cacheStructure = CacheStructure.Strings;



    public static GetDelExpression of(SoloExpression exp){

        GetDelExpression getDelExpression = new GetDelExpression();
        getDelExpression.setCacheIndexType(exp.getCacheIndexType());
        getDelExpression.setName(exp.getName());
        getDelExpression.setCacheTerm(exp.getCacheTerm());

        return getDelExpression;

    }

    @Override
    public String toString(){
        return cacheStructure+"["+super.toString();
    }

}
