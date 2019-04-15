package cn.iflyapi.validator.handler;

import cn.iflyapi.validator.annotation.On;
import cn.iflyapi.validator.core.FastValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author flyhero
 * @date 2019-04-14 7:57 PM
 */
public class OnHandler implements IHandler {
    @Override
    public void handle(Annotation annotation, Field field, Object target) throws IllegalAccessException {
        On on = (On) annotation;
        field.setAccessible(true);
        Object o1 = field.get(target);
        FastValidator.doit().on(o1, on.min(), on.max(), on.desc());
    }
}
