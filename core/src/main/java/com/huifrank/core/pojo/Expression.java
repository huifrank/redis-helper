package com.huifrank.core.pojo;

import com.huifrank.core.CacheIndexType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Expression {



    private Term term;

    private String name;

    private CacheIndexType cacheIndexType;

    @Override
    public String toString(){

        if(term.getBefore() != null) {
            if(term.getRefBeforeName() != null){
                return  "->" + term + "(" +term.getBefore() + ")."+term.getRefBeforeName();
            }
            return   "->" + term + "(" + term.getBefore() + ")";
        }else {
            return   "->" + term;
        }

    }


    /**
     * 获取当前Exp所关联的索引属性名
     * @return
     */
    public List<String> getExpNames(){
        List<String> curNames = new ArrayList<>();
        if(term.getBefore() != null){
            curNames.addAll(term.getBefore().getExpNames());
        }
        curNames.add(name);
        return curNames;
    }
}
