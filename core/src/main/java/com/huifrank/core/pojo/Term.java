package com.huifrank.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Term {

    private String indexName;

    private Integer valueIndex;

    private String refBeforeName;

    private Expression before;


    public Term(String indexName){
        this.indexName = indexName;
    }

    @Override
    public String toString(){
        if(valueIndex == null){
            return indexName;
        }
        return indexName+"["+valueIndex+"]";
    }
}
