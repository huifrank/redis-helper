package com.huifrank.common.pojo.expression;

import com.huifrank.annotation.CacheStructure;
import com.huifrank.common.pojo.term.ReflectTerm;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PutExpression extends BinaryExpression {


    private ReflectTerm valueTerm;

    private CacheStructure cacheStructure = CacheStructure.Strings;

    private Long expireIn;

    @Override
    public Type ExpressionType() {
        return Type.PUT;
    }
    @Override
    public String toString(){
        return cacheStructure +"["+ super.toString() + " # " + valueTerm;
    }
}
