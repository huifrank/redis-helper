package com.huifrank.core.pojo.expression;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReflectTerm {

    private String valueIndex;

    private String fieldName;


    @Override
    public String toString(){
        return "["+valueIndex+"]"+"."+fieldName;
    }
}
