package com.huifrank.annotation.index;

import com.huifrank.annotation.CacheStructure;
import com.huifrank.annotation.Mapping;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Indexed {

    String ref();

    Mapping mapping() default Mapping.one;

    CacheStructure structure() default CacheStructure.Strings;


}
