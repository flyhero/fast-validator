package cn.iflyapi.validator.aspect;


import cn.iflyapi.validator.annotation.NotNull;
import cn.iflyapi.validator.annotation.NumRange;
import cn.iflyapi.validator.annotation.StrRange;
import cn.iflyapi.validator.annotation.Validator;
import cn.iflyapi.validator.exception.FlyValidatorException;
import cn.iflyapi.validator.util.ReflectUtils;
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

        StrRange[] strRanges = validator.strRange();

        NumRange[] numRanges = validator.numRange();

        isValid(targetObj, vs, strRanges, numRanges);

        return joinPoint.proceed();
    }

    /**
     * 验证是否有效
     *
     * @param targetObj
     * @param vs
     * @param strRanges
     * @param numRanges
     */
    private void isValid(Object[] targetObj, String[] vs, StrRange[] strRanges, NumRange[] numRanges) {
        for (Object b : targetObj) {
            Class<?> targetClass = b.getClass();
            if (targetClass.isPrimitive()) {
                continue;
            }
            checkNull(vs, b);

            for (StrRange strRange : strRanges) {

                String field = strRange.value();
                if (!ReflectUtils.hasField(targetClass, field)) {
                    continue;
                }
                Field field1 = null;
                try {
                    field1 = targetClass.getDeclaredField(field);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                Object o = ReflectUtils.fieldValue(b, field1);
                if (Objects.isNull(o)) {
                    throw new FlyValidatorException(field + " can not be null");
                }

                System.out.println(o.getClass().getTypeName());
                //如果不是字符串
                if (!o.getClass().getTypeName().equals("java.lang.String")) {
                    continue;
                }
                int min = strRange.min();
                int max = strRange.max();
                //如果设置了min或max
                if (min != Integer.MIN_VALUE || max != Integer.MAX_VALUE) {
                    if (min != Integer.MIN_VALUE && max != Integer.MAX_VALUE) {
                        if (String.valueOf(o).length() <= min && String.valueOf(o).length() >= max) {
                            throw new FlyValidatorException(field + " length can not in between " + min + " and " + max);
                        }
                    }
                    if (min != Integer.MIN_VALUE) {
                        if (String.valueOf(o).length() < min) {
                            throw new FlyValidatorException(field + " length can not less than " + min);
                        }
                    }
                    if (min == Integer.MIN_VALUE) {
                        if (String.valueOf(o).length() > max) {
                            throw new FlyValidatorException(field + " length can not greater than " + max);
                        }
                    }
                } else {
                    String range = strRange.range();

                    checkStrRange(o, range, field);
                }

            }

            for (NumRange numRange : numRanges) {
                String field = numRange.value();
                if (!ReflectUtils.hasField(targetClass, field)) {
                    continue;
                }
                Field field1 = null;
                try {
                    field1 = targetClass.getDeclaredField(field);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                if (!ReflectUtils.isNumber(field1)) {
                    continue;
                }
                Object o = ReflectUtils.fieldValue(b, field1);

                if (Objects.isNull(o)) {
                    throw new FlyValidatorException(field + " can not be null");
                }

                //TODO 这里把所有类型都强制转换为int，存在一定隐患
                int targetInt = (int) o;
                int min = numRange.min();
                int max = numRange.max();
                //如果设置了min或max
                if (min != Integer.MIN_VALUE || max != Integer.MAX_VALUE) {
                    if (min != Integer.MIN_VALUE && max != Integer.MAX_VALUE) {
                        if (targetInt <= min || targetInt >= max) {
                            throw new FlyValidatorException(field + " not in between " + min + "and " + max);
                        }
                    }
                    if (min != Integer.MIN_VALUE) {
                        if (targetInt < min) {
                            throw new FlyValidatorException(field + " can not less than " + min);
                        }
                    }
                    if (min == Integer.MIN_VALUE) {
                        if (targetInt > max) {
                            throw new FlyValidatorException(field + " can not greater than " + max);
                        }
                    }
                } else {
                    String range = numRange.range();

                    checkNumRange(o, range, field);
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
        if (left.equals(Operation.LEFT_CLOSE) && right.equals(Operation.RIGHT_CLOSE)) {
            if (String.valueOf(o).length() < min || String.valueOf(o).length() > max) {
                throw new FlyValidatorException(field + " not in " + range);
            }
        } else if (left.equals(Operation.LEFT_CLOSE) && right.equals(Operation.RIGHT_OPEN)) {
            if (String.valueOf(o).length() < min || String.valueOf(o).length() >= max) {
                throw new FlyValidatorException(field + " not in " + range);
            }
        } else if (left.equals(Operation.LEFT_OPEN) && right.equals(Operation.RIGHT_OPEN)) {
            if (String.valueOf(o).length() <= min || String.valueOf(o).length() >= max) {
                throw new FlyValidatorException(field + " not in " + range);
            }
        } else if (left.equals(Operation.LEFT_OPEN) && right.equals(Operation.RIGHT_CLOSE)) {
            if (String.valueOf(o).length() <= min || String.valueOf(o).length() > max) {
                throw new FlyValidatorException(field + " not in " + range);
            }
        }

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
        if (left.equals(Operation.LEFT_CLOSE) && right.equals(Operation.RIGHT_CLOSE)) {
            if (targetInt < min || targetInt > max) {
                throw new FlyValidatorException(field + " not in " + range);
            }
        } else if (left.equals(Operation.LEFT_CLOSE) && right.equals(Operation.RIGHT_OPEN)) {
            if (targetInt < min || targetInt >= max) {
                throw new FlyValidatorException(field + " not in " + range);
            }
        } else if (left.equals(Operation.LEFT_OPEN) && right.equals(Operation.RIGHT_OPEN)) {
            if (targetInt <= min || targetInt >= max) {
                throw new FlyValidatorException(field + " not in " + range);
            }
        } else if (left.equals(Operation.LEFT_OPEN) && right.equals(Operation.RIGHT_CLOSE)) {
            if (targetInt <= min || targetInt > max) {
                throw new FlyValidatorException(field + " not in " + range);
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
                throw new FlyValidatorException(s + " can not be null");
            }

        }
    }

}
