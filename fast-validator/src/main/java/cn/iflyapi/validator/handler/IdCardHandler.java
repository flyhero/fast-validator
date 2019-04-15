package cn.iflyapi.validator.handler;

import cn.iflyapi.validator.core.FastValidator;
import cn.iflyapi.validator.exception.NotSupportException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author flyhero
 * @date 2019-04-14 7:57 PM
 */
public class IdCardHandler implements IHandler {
    @Override
    public void handle(Annotation annotation, Field field, Object target) throws IllegalAccessException {
        field.setAccessible(true);
        Object o1 = field.get(target);

        if (field.getType() != String.class) {
            throw new NotSupportException("身份证验证参数必须是字符串类型");
        }
        FastValidator.doit().mustIdCard(o1.toString());
    }

}
