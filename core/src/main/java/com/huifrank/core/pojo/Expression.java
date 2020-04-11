package com.huifrank.core.pojo;

import com.huifrank.core.CacheIndexType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Expression {

    private Expression before;

    private String term;

    private String name;

    private CacheIndexType cacheIndexType;

    @Override
    public String toString(){

        if(before != null) {
            return cacheIndexType + "->" + term + "(" + before.toString() + ")";
        }else {
            return cacheIndexType + "->" + term;
        }

    }
}
