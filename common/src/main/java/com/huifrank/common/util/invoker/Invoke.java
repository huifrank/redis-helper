package com.huifrank.common.util.invoker;

import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

@Persistent
@Inherited
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.METHOD })
public @interface Invoke {
}
