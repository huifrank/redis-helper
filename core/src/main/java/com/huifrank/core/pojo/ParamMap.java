package com.huifrank.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ParamMap {

    private String name;

    private int index;

    private Object value;

    private String valueTypeName;



}
