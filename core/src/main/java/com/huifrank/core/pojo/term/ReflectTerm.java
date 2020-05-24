package com.huifrank.core.pojo.term;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReflectTerm {

    private String valueIndex;

    private String fieldName;


    @Override
    public String toString(){
        if(fieldName != null && valueIndex != null){
            return "["+valueIndex+"]"+"."+fieldName;
        }
        if(fieldName != null){
            return fieldName;
        }
        if(valueIndex != null){
            return "["+valueIndex+"]";
        }
        return null;
    }
}
