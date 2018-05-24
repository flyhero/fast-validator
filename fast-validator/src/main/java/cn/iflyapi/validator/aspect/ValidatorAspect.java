package cn.iflyapi.validator.aspect;


import cn.iflyapi.validator.annotation.NotNull;
import cn.iflyapi.validator.annotation.Range;
import cn.iflyapi.validator.annotation.Validator;
import cn.iflyapi.validator.exception.FastValidatorException;
import cn.iflyapi.validator.util.ReflectUtils;
import cn.iflyapi.validator.util.ValidatorUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author: qfwang
 * @date: 2018-05-18 下午2:35
 */
@Aspect
@Component
public class ValidatorAspect {

    @Pointcut(value = "@annotation(cn.iflyapi.validator.annotation.Validator)")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] targetObj = joinPoint.getArgs();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Validator validator = method.getAnnotation(Validator.class);

        //不能为空的属性
        NotNull notNull = validator.notNull();
        String[] vs = notNull.value();

        Range[] ranges = validator.range();

        isValid(targetObj, vs, ranges);

        return joinPoint.proceed();
    }

    /**
     * 验证是否有效
     *
     * @param targetObj
     * @param vs
     * @param ranges
     */
    private void isValid(Object[] targetObj, String[] vs, Range[] ranges) {
        for (Object b : targetObj) {
            Class<?> targetClass = b.getClass();
            if (ReflectUtils.isNumber(b) || ReflectUtils.isString(b)) {

            } else { //TODO 判断非布尔和字符
                checkNull(vs, b);

                for (Range range : ranges) {
                    String field = range.value();
                    if (!ReflectUtils.hasField(targetClass, field)) {
                        continue;
                    }
                    Field field1 = null;
                    try {
                        field1 = targetClass.getDeclaredField(field);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    if (!ReflectUtils.isNumber(field1) && !field1.getType().getName().equals(ReflectUtils.STRING_TYPE_NAME)) {
                        continue;
                    }
                    Object o = ReflectUtils.fieldValue(b, field1);

                    if (Objects.isNull(o)) {
                        throw new FastValidatorException(field + " can not be null");
                    }

                    int min = range.min();
                    int max = range.max();
                    //如果设置了min或max
                    if (min != Integer.MIN_VALUE || max != Integer.MAX_VALUE) {
                        ValidatorUtils.checkRange(o, min, max);
                    } else {
                        String srange = range.range();
                        if (ReflectUtils.isNumber(field1)) {
                            checkNumRange(o, srange, field);
                        } else {
                            checkStrRange(o, srange, field);
                        }
                    }
                }
            }


        }

    }

    /**
     * 检查字符串是否在有效的范围
     *
     * @param o
     * @param range
     * @param field
     */
    public void checkStrRange(Object o, String range, String field) {
        String strTrim = range.trim();
        String left = strTrim.substring(0, 1);
        String right = strTrim.substring(strTrim.length() - 1, strTrim.length());
        String[] values = range.split(",");
        Integer min = Integer.valueOf(values[0].replace("(", "").replace("[", ""));
        Integer max = Integer.valueOf(values[1].replace(")", "").replace("]", ""));
        int num = String.valueOf(o).length();
        parse(num, left, right, min, max, range, field);

    }

    /**
     * 检查数字是否在有效的范围
     *
     * @param o
     * @param range
     * @param field
     */
    public void checkNumRange(Object o, String range, String field) {
        String strTrim = range.trim();
        String left = strTrim.substring(0, 1);
        String right = strTrim.substring(strTrim.length() - 1, strTrim.length());
        String[] values = range.split(",");
        Integer min = Integer.valueOf(values[0].replace("(", "").replace("[", ""));
        Integer max = Integer.valueOf(values[1].replace(")", "").replace("]", ""));
        int targetInt = (int) o;
        parse(targetInt, left, right, min, max, range, field);
    }


    public void parse(int targetInt, String left, String right, int min, int max, String range, String field) {
        if (left.equals(ValidatorAspect.Operation.LEFT_CLOSE) && right.equals(ValidatorAspect.Operation.RIGHT_CLOSE)) {
            if (targetInt < min || targetInt > max) {
                throw new FastValidatorException(field + " not in " + range);
            }
        } else if (left.equals(ValidatorAspect.Operation.LEFT_CLOSE) && right.equals(ValidatorAspect.Operation.RIGHT_OPEN)) {
            if (targetInt < min || targetInt >= max) {
                throw new FastValidatorException(field + " not in " + range);
            }
        } else if (left.equals(ValidatorAspect.Operation.LEFT_OPEN) && right.equals(ValidatorAspect.Operation.RIGHT_OPEN)) {
            if (targetInt <= min || targetInt >= max) {
                throw new FastValidatorException(field + " not in " + range);
            }
        } else if (left.equals(ValidatorAspect.Operation.LEFT_OPEN) && right.equals(ValidatorAspect.Operation.RIGHT_CLOSE)) {
            if (targetInt <= min || targetInt > max) {
                throw new FastValidatorException(field + " not in " + range);
            }
        }
    }

    interface Operation {
        String LEFT_OPEN = "(";
        String RIGHT_OPEN = ")";
        String LEFT_CLOSE = "[";
        String RIGHT_CLOSE = "]";
    }

    public void checkNull(String[] vs, Object target) {
        for (String s : vs) {
            //不含有此属性
            if (!ReflectUtils.hasField(target.getClass(), s)) {
                continue;
            }
            Field field = null;
            try {
                field = target.getClass().getDeclaredField(s);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            Object o = ReflectUtils.fieldValue(target, field);
            if (Objects.isNull(o)) {
                throw new FastValidatorException(s + " can not be null");
            }

        }
    }

}
