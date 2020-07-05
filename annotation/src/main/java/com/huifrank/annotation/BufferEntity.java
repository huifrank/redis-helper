package com.huifrank.annotation;

import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface BufferEntity {

    String keyPrefix();

    long expireIn() default -1L;
}
