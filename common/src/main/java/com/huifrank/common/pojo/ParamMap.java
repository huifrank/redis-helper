package com.huifrank.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
@Data
public class ParamMap {

    private String name;

    /**
     * 对象中属性的话，使用.分隔
     */
    private String index;

    private String valueTypeName;


    public int getObjIndex(){
        String[] split = StringUtils.split(index, '.');
        return Integer.parseInt(split[0]);
    }
    public String getObjName(){
        String[] split = StringUtils.split(index, '.');
        if(split.length>=2){
            return split[1];
        }
        return null;
    }



}
