package cn.iflyapi.validator.handler;

import cn.iflyapi.validator.annotation.OnMax;
import cn.iflyapi.validator.core.FastValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author flyhero
 * @date 2019-04-14 7:57 PM
 */
public class OnMaxHandler implements IHandler {
    @Override
    public void handle(Annotation annotation, Field field, Object target) throws IllegalAccessException {
        OnMax on = (OnMax) annotation;
        field.setAccessible(true);
        Object o1 = field.get(target);
        FastValidator.doit().onMax(o1, on.value(), on.desc());
    }

}
