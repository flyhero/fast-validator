package cn.iflyapi.validator.annotation;

import java.lang.annotation.*;

/**
 * @author flyhero
 * @date 2018-05-17 下午5:16
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotEmpty {
    String[] value() default {};
}
