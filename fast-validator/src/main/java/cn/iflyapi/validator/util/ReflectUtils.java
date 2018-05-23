package cn.iflyapi.validator.util;

import java.lang.reflect.Field;

/**
 * @author: qfwang
 * @date: 2018-05-18 上午10:26
 */
public class ReflectUtils {

    public static String[] types = {"java.lang.Integer",
            "java.lang.Double",
            "java.lang.Float",
            "java.lang.Long",
            "java.lang.Short",
            "java.lang.Byte",
            "int", "double", "long", "short", "byte", "float"};

    /**
     * 是否是数值类型
     * @param field
     * @return
     */
    public static boolean isNumber(Field field) {
        for (String type : types) {
            if (type.equals(field.getType().getName())) {
                return true;
            }
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

    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
    }
}
