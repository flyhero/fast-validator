package cn.iflyapi.validator.annotation;

import java.lang.annotation.*;

/**
 * @author flyhero
 * @date 2019-04-06 9:15 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {
    String value() default "";
}
