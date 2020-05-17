package com.huifrank.annotation.action;

import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

/**
 * 更新缓存
 */
@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Put {

    String[] where() default {};
}
