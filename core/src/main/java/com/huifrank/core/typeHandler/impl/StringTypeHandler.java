package com.huifrank.core.typeHandler.impl;

import com.huifrank.core.typeHandler.TypeHandler;

public class StringTypeHandler implements TypeHandler<String> {


    @Override
    public String resolve2String(String value) {
        return value;
    }
}
