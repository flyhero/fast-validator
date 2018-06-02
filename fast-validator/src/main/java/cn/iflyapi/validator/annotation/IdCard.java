package cn.iflyapi.validator.annotation;

import java.lang.annotation.*;

/**
 * @author: flyhero
 * @date: 2018-06-02 下午5:38
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IdCard {
    String value();
}
