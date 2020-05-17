package com.huifrank.core.pojo.expression;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class GetExpression extends SoloExpression{



    @Override
    public String toString(){

        if(getTerm().getBefore() != null) {
            if(getTerm().getRefBeforeName() != null){
                return  "->" + getTerm() + "(" +getTerm().getBefore() + ")."+getTerm().getRefBeforeName();
            }
            return   "->" + getTerm() + "(" + getTerm().getBefore() + ")";
        }else {
            return   "->" + getTerm();
        }

    }


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
    public Type ExpressionType() {
        return Type.GET;
    }
}
