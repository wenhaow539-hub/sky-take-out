package com.sky.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 匿名访问注解：标有该注解的接口或类，不需要进行 JWT 校验
 */
// 标识这个注解可以加在方法上，也可以加在类上
@Target({ElementType.METHOD, ElementType.TYPE})
// 标识这个注解在程序运行时生效
@Retention(RetentionPolicy.RUNTIME)
public @interface Anonymous {

}