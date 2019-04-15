package cn.iflyapi.validator.annotation;

import java.lang.annotation.*;

/**
 * @author flyhero
 * @date 2019-04-06 9:05 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface On {

    long min() default Long.MIN_VALUE;

    long max() default Long.MAX_VALUE;

    String desc() default "";
}
