package cn.iflyapi.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: flyhero
 * @date: 2018-05-17 下午5:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {
    String value() default "";

    NotEmpty notNull() default @NotEmpty;

    Range[] range() default {@Range()};
}
