package cn.iflyapi.validator.handler;

import cn.iflyapi.validator.annotation.NotEmpty;
import cn.iflyapi.validator.core.FastValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author flyhero
 * @date 2019-04-14 7:40 PM
 */
public class NotEmptyHandler implements IHandler {
    @Override
    public void handle(Annotation annotation, Field field, Object target) throws IllegalAccessException {
        NotEmpty notEmpty = (NotEmpty) annotation;
        field.setAccessible(true);
        Object o1 = field.get(target);
        FastValidator.doit().notEmpty(o1, notEmpty.value());
    }
}
