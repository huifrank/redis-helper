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

    private String refBeforeName;

    private String term;

    private String name;

    private CacheIndexType cacheIndexType;

    @Override
    public String toString(){

        if(before != null) {
            if(refBeforeName != null){
                return  "->" + term + "(" + before.toString() + ")."+refBeforeName;
            }
            return   "->" + term + "(" + before.toString() + ")";
        }else {
            return   "->" + term;
        }

    }

    /**
     * 复制一个对象出来
     * @return
     */
    public Expression newInstance(){
        Expression expression = new Expression();
        expression.setRefBeforeName(this.refBeforeName)
                .setTerm(this.term)
                .setName(this.name)
                .setCacheIndexType(this.cacheIndexType);
        if(this.before != null) {
            expression.setBefore(this.before.newInstance());
        }
        return expression;
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
