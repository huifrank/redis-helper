package com.huifrank.core.pojo.expression;

import com.huifrank.core.CacheIndexType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 由单个参数组成的表达式
 * get xxx
 * del xxx
 */
@Data
@Accessors(chain = true)
public abstract class SoloExpression implements Expression {


    private Term term;

    private String name;

    private CacheIndexType cacheIndexType;


    /**
     * 获取当前Exp所关联的索引属性名
     * @return
     */
    public List<String> getExpNames(){
        List<String> curNames = new ArrayList<>();
        if(getTerm().getBefore() != null){
            curNames.addAll(getTerm().getBefore().getExpNames());
        }
        curNames.add(getName());
        return curNames;
    }

    @Override
    public String toString() {

        if (getTerm().getBefore() != null) {
            if (getTerm().getRefBeforeName() != null) {
                return "->" + getTerm() + "(" + getTerm().getBefore() + ")." + getTerm().getRefBeforeName();
            }
            return "->" + getTerm() + "(" + getTerm().getBefore() + ")";
        } else {
            return "->" + getTerm();
        }
    }

}
