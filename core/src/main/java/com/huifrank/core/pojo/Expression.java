package com.huifrank.core.pojo;

import com.huifrank.core.CacheIndexType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 获取当前Exp所关联的索引属性名
     * @return
     */
    public List<String> getExpNames(){
        List<String> curNames = new ArrayList<>();
        if(before != null){
            curNames.addAll(before.getExpNames());
        }
        curNames.add(name);
        return curNames;
    }
}
