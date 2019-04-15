package cn.iflyapi.validator.annotation;

import java.lang.annotation.*;

/**
 * @author flyhero
 * @date 2018-05-18 上午10:50
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Range {
    String value() default "";

    String range() default "[0,2147483647)";

    int min() default Integer.MIN_VALUE;

    int max() default Integer.MAX_VALUE;
}
