package com.huifrank.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Term {

    private String indexName;
    private String value;

    @Override
    public String toString(){
        if(value == null){
            return indexName;
        }
        return indexName+value;
    }
}
