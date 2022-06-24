package com.wry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author drgs
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ApiAnnotation {
    /**
     * @return 作者
     */
    String author() default "wry";

    /**
     * 默认1
     *
     * @return 版本
     */
    int version() default 1;

    /**
     * @return 消息
     */
    String msg() default "";
}
