package com.huifrank.annotation;

import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CacheFor {

    Class bufferEntity();
}
