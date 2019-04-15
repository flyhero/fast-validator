package cn.iflyapi.validator.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author flyhero
 * @date 2019-04-14 7:40 PM
 */
public interface IHandler {

    void handle(Annotation annotation, Field field, Object target) throws IllegalAccessException;
}
