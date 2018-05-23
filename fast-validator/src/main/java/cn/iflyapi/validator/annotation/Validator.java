package cn.iflyapi.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: qfwang
 * @date: 2018-05-17 下午5:17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {
    String value() default "";
    NotNull notNull() default @NotNull;
    StrRange[] strRange() default {@StrRange};
    NumRange[] numRange() default {@NumRange()};
}
