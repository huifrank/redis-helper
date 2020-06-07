package com.huifrank.core.pojo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class Result {

    private Class clazz;

    private String property;


    public Result(Class clazz,String result){
        this.clazz = clazz;
        if(!result.startsWith(clazz.getSimpleName())){
            throw new RuntimeException("期望范围结果与缓存实体不匹配");
        }
        property = StringUtils.substringAfter(result,clazz.getSimpleName());
    }

    public String getPropertyName(){
        if(StringUtils.isNotBlank(property)){
            return property.substring(1);
        }
        return StringUtils.EMPTY;
    }







}
