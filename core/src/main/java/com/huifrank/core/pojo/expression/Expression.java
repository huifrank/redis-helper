package com.huifrank.core.pojo.expression;

public interface Expression {

    Type ExpressionType();


    enum Type{
        GET,
        PUT,
        MERGE,
        DEL,
        GET_DEL,
    }
}
