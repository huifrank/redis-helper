package com.huifrank.annotation.action;

import com.huifrank.common.pojo.execute.TypeReference;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Query {

    String[] where() default {};

    String result();

}
