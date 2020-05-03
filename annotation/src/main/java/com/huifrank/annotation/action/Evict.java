package com.huifrank.annotation.action;

import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;


/**
 * 用于生产删除缓存动作的注解
 * @where: 可空参数，默认会按照方法参数名与@CacheFor中实体属性关联，生成要删除的key,
 *          如果不为空，只按给定属性生成删除key,如果where中属性不存在，抛异常。
 */
@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Evict {

    String[] where() default {};

}
