package cn.iflyapi.validator.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     *
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
     *
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
     * 是否含有指定属性(不包含父类)
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
     * 是否含有指定属性(包含父类)
     *
     * @param target
     * @param field
     * @return
     */
    public static boolean hasFieldIncludeSuper(Class<?> target, String field) {
        List<Field> fieldList = allField(target);
        for (Field f : fieldList) {
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

    /**
     * 获取属性值（包含父类）
     *
     * @param targetObj
     * @param field
     * @return
     */
    public static Object fieldValueSuper(Object targetObj, String field) {
        Object value = null;
        for (Class<?> clazz = targetObj.getClass(); hasSuperClass(clazz); ) {
            if (hasField(clazz, field)) {
                try {
                    Field field1 = targetObj.getClass().getDeclaredField(field);
                    field1.setAccessible(true);
                    value = field1.get(targetObj);
                    return value;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            clazz = clazz.getSuperclass();
        }
        return value;
    }

    /**
     * 获取类的属性（包含父类）
     *
     * @param targetObj
     * @param field
     * @return
     */
    public static Field getAllDeclaredField(Object targetObj, String field) {
        Field field1 = null;
        for (Class<?> clazz = targetObj.getClass(); hasSuperClass(clazz); ) {
            if (hasField(clazz, field)) {
                try {
                    field1 = targetObj.getClass().getDeclaredField(field);
                    return field1;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

            }
            clazz = clazz.getSuperclass();
        }
        return field1;
    }

    /**
     * 获取对象（包含父类）的所有属性
     *
     * @param clazz
     * @return
     */
    public static List<Field> allField(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        for (Class<?> it = clazz; hasSuperClass(it); ) {
            fieldList.addAll(Arrays.asList(it.getDeclaredFields()));
            it = clazz.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 是否存在父类
     *
     * @param clazz
     * @return
     */
    public static boolean hasSuperClass(Class<?> clazz) {
        return (clazz != null) && (!clazz.equals(Object.class));
    }

    public static void main(String[] args) {
        int a = 10;
        System.out.println(isNumber(a));
    }
}
