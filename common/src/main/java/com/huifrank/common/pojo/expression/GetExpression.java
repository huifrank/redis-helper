package com.huifrank.common.pojo.expression;

import com.huifrank.annotation.CacheStructure;
import com.huifrank.annotation.Mapping;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetExpression extends SoloExpression{

    private CacheStructure cacheStructure = CacheStructure.Strings;

    private Mapping mapping = Mapping.one;



    @Override
    public Type ExpressionType() {
        return Type.GET;
    }

    @Override
    public String toString(){
        return mapping + ":" + super.toString() ;
    }
}
