package cn.iflyapi.validator.util;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author: qfwang
 * @date: 2018-05-18 上午10:26
 */
public class ReflectUtils {

    public static String[] number_types = {"java.lang.Integer",
            "java.lang.Double",
            "java.lang.Float",
            "java.lang.Long",
            "java.lang.Short",
            "java.lang.Byte",
            "int", "double", "long", "short", "byte", "float"};


    public static String[] other_types = {
            "java.lang.Boolean",
            "java.lang.Character",
            "java.lang.String",
            "boolean", "char"};

    public static final String STRING_TYPE_NAME = "java.lang.String";

    /**
     * 是否是数值类型
     *
     * @param field
     * @return
     */
    public static boolean isNumber(Field field) {
        for (String type : number_types) {
            if (type.equals(field.getType().getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 当前对象是否是数值
     * @param o
     * @return
     */
    public static boolean isNumber(Object o) {
        String type = o.getClass().getTypeName();
        for (String s : number_types) {
            if (s.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 当前对象是否是字符串
     * @param o
     * @return
     */
    public static boolean isString(Object o) {
        String type = o.getClass().getTypeName();
            if (STRING_TYPE_NAME.equals(type)) {
                return true;
            }
        return false;
    }


    /**
     * 是否含有指定属性
     *
     * @param target
     * @param field
     * @return
     */
    public static boolean hasField(Class<?> target, String field) {
        Field[] targetFeild = target.getDeclaredFields();
        for (Field f : targetFeild) {
            if (f.getName().equals(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定属性的值
     *
     * @param targetObj
     * @param field
     * @return
     */
    public static Object fieldValue(Object targetObj, Field field) {
        Object value = null;
        try {
            field.setAccessible(true);
            value = field.get(targetObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return value;
    }

}
