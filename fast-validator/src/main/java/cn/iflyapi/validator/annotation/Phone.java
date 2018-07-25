package cn.iflyapi.validator.annotation;

import java.lang.annotation.*;

/**
 * @author: flyhero
 * @date: 2018-06-02 下午5:35
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Phone {
    String value() default "";
}
