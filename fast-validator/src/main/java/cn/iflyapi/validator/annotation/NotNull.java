package cn.iflyapi.validator.annotation;

import java.lang.annotation.*;

/**
 * @author: qfwang
 * @date: 2018-05-17 下午5:16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {
    String[] value() default {};
}
