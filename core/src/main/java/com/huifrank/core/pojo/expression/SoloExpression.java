package com.huifrank.core.pojo.expression;

import com.huifrank.core.CacheIndexType;
import com.huifrank.core.pojo.term.CacheTerm;
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


    private CacheTerm cacheTerm;

    private String name;

    private CacheIndexType cacheIndexType;


    /**
     * 获取当前Exp所关联的索引属性名
     * @return
     */
    public List<String> getExpNames(){
        List<String> curNames = new ArrayList<>();
        if(getCacheTerm().getBefore() != null){
            curNames.addAll(getCacheTerm().getBefore().getExpNames());
        }
        curNames.add(getName());
        return curNames;
    }

    @Override
    public String toString() {

        if (getCacheTerm().getBefore() != null) {
            if (getCacheTerm().getRefBeforeName() != null) {
                return "->" + getCacheTerm() + "(" + getCacheTerm().getBefore() + ")." + getCacheTerm().getRefBeforeName();
            }
            return "->" + getCacheTerm() + "(" + getCacheTerm().getBefore() + ")";
        } else {
            return "->" + getCacheTerm();
        }
    }

}
