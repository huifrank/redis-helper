package com.huifrank.core.pojo.expression;

import com.huifrank.annotation.CacheStructure;
import com.huifrank.annotation.Mapping;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

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
