package cn.iflyapi.validator.annotation;

import java.lang.annotation.*;

/**
 * @author flyhero
 * @date 2018-07-02 下午9:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IP {
    String value() default "";
}
