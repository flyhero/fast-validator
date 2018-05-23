package cn.iflyapi.validator.annotation;

import java.lang.annotation.*;

/**
 * @author: qfwang
 * @date: 2018-05-18 上午10:45
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrRange {
    String value() default "";
    String range() default "(0,255]";
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
}
