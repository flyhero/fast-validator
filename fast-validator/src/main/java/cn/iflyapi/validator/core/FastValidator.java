package cn.iflyapi.validator.core;

import cn.iflyapi.validator.annotation.Email;
import cn.iflyapi.validator.annotation.IdCard;
import cn.iflyapi.validator.annotation.NotNull;
import cn.iflyapi.validator.annotation.Phone;
import cn.iflyapi.validator.element.RangeElement;
import cn.iflyapi.validator.element.StringElement;
import cn.iflyapi.validator.enums.ValidateDataEnum;
import cn.iflyapi.validator.exception.FastValidatorException;
import cn.iflyapi.validator.util.ArrayUtils;
import cn.iflyapi.validator.util.ReflectUtils;
import cn.iflyapi.validator.util.ValidatorUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * TODO 快速失败时抛出异常，否则汇集结果返回
 *
 * @author flyhero <http://www.iflyapi.cn>
 * @date 2018/5/23 下午1:28
 */
public class FastValidator {

    /**
     * 待验证的范围
     */
    private List<RangeElement> veLsit = new ArrayList<>();

    private Object[] objects = new Object[0];

    /**
     * 待验证的字符串（email,ip,phone等）
     */
    private List<StringElement> stringElements = new ArrayList<>();

    private Result result = new Result();

    private boolean isFailFast = true;

    private ValidatorHandler validatorHandler = new ValidatorHandler(true);


    /**
     * 构建验证器
     *
     * @return
     */
    public static FastValidator doit() {
        return new FastValidator();
    }

    /**
     * 快速失败
     *
     * @return
     */
    public FastValidator failFast() {
        this.isFailFast = true;
        this.validatorHandler = new ValidatorHandler(isFailFast);
        return this;
    }

    /**
     * 安全失败
     *
     * @return
     */
    public FastValidator failSafe() {
        this.isFailFast = false;
        this.validatorHandler = new ValidatorHandler(isFailFast);
        return this;
    }

    /**
     * 验证null
     *
     * @param objects
     */
    public FastValidator notNull(Object... objects) {
        this.objects = ArrayUtils.concat(this.objects, objects);
        return this;
    }

    /**
     * 验证空
     *
     * @param target
     * @param fieldName
     * @return
     */
    public FastValidator notEmpty(Object target, String fieldName) {
        if (null == target) {
            result(fieldName);
        }
        if (target instanceof String) {
            String s = (String) target;
            if (s.isEmpty()) {
                result(fieldName);
            }
        } else if (target instanceof Collection) {
            Collection collection = (Collection) target;
            if (collection.isEmpty()) {
                result(fieldName);
            }
        } else if (target instanceof Map) {
            Map map = (Map) target;
            if (map.isEmpty()) {
                result(fieldName);
            }
        }
        return this;
    }

    /**
     * 获取验证结果
     *
     * @return
     */
    public Result toResult() {
        return result;
    }

    private void result(String fieldName) {
        if (isFailFast) {
            throw new FastValidatorException(fieldName + "不能为空");
        } else {
            result.getErrors().add(fieldName + "不能为空");
        }
    }

    /**
     * 如果不为空再进行验证最大值
     *
     * @param target
     * @param max
     * @param fieldName
     * @return
     */
    public FastValidator ifNotEmptyOnMax(Object target, int max, String fieldName) {
        if (null == target) {
            return this;
        } else {
            onMax(target, max);
        }
        return this;
    }

    /**
     * 如果不为空再进行验证最小值
     *
     * @param target
     * @param min
     * @param fieldName
     * @return
     */
    public FastValidator ifNotEmptyOnMin(Object target, int min, String fieldName) {
        if (null == target) {
            return this;
        } else {
            onMin(target, min);
        }
        return this;
    }

    /**
     * 添加验证元素(包含最大小值)
     *
     * @param target
     * @param min
     * @param max
     * @return
     */
    public FastValidator on(Object target, int min, int max) {
        return on(target, min, max, "");
    }

    public FastValidator on(Object target, int min, int max, String desc) {
        this.veLsit.add(new RangeElement(target, min, max, desc));
        return this;
    }

    /**
     * 验证最小值
     *
     * @param target
     * @param min
     * @return
     */
    public FastValidator onMin(Object target, int min) {
        return on(target, min, Integer.MAX_VALUE);
    }

    /**
     * 验证最小值(包含自定义描述)
     *
     * @param target
     * @param min
     * @return
     */
    public FastValidator onMin(Object target, int min, String desc) {
        return on(target, min, Integer.MAX_VALUE, desc);
    }

    /**
     * 验证最大值
     *
     * @param target
     * @param max
     * @return
     */
    public FastValidator onMax(Object target, int max) {
        return on(target, Integer.MIN_VALUE, max);
    }

    /**
     * 验证最大值(包含自定义描述)
     *
     * @param target
     * @param max
     * @return
     */
    public FastValidator onMax(Object target, int max, String desc) {
        return on(target, Integer.MIN_VALUE, max, desc);
    }

    /**
     * 验证邮箱
     *
     * @param target
     * @return
     */
    public FastValidator onEmail(String target) {
        this.stringElements.add(new StringElement(ValidateDataEnum.EMAIL.getCode(), target));
        return this;
    }

    /**
     * 验证IP
     *
     * @param target
     * @return
     */
    public FastValidator onIP(String target) {
        this.stringElements.add(new StringElement(ValidateDataEnum.IP.getCode(), target));
        return this;
    }

    /**
     * 验证身份证
     *
     * @param target
     * @return
     */
    public FastValidator onIdCard(String target) {
        this.stringElements.add(new StringElement(ValidateDataEnum.ID_CARD.getCode(), target));
        return this;
    }

    /**
     * 验证手机号
     *
     * @param target
     * @return
     */
    public FastValidator onPhone(String target) {
        this.stringElements.add(new StringElement(ValidateDataEnum.PHONE.getCode(), target));
        return this;
    }

    /**
     * 结束并验证
     */
    public Result end() {

        List<String> errors = new ArrayList<>();

        if (null != objects) {
            for (Object o : objects) {
                if (Objects.isNull(o)) {
                    throw new FastValidatorException("params can not be null or \" \"");
                }
            }
        }

        veLsit.forEach(rangeElement -> {
            Object o = rangeElement.getValue();
            int min = rangeElement.getMin();
            int max = rangeElement.getMax();
            if (ReflectUtils.isNumber(o) || ReflectUtils.isString(o)) {
                String s = ValidatorUtils.checkRange(o, min, max, rangeElement.getDesc(), isFailFast);
                if (!StringUtils.isEmpty(s)) {
                    errors.add(s);
                }
            }
        });

        stringElements.forEach(stringElement -> {
            String msg = "";
            switch (stringElement.getType()) {
                case 1:
                    msg = validatorHandler.email(stringElement.getValue());
                    break;
                case 2:
                    msg = validatorHandler.idCard(stringElement.getValue());
                    break;
                case 3:

                    break;
                case 4:
                    msg = validatorHandler.phone(stringElement.getValue());
                    break;
                case 5:

                    break;
                case 6:

                    break;
                default:
                    break;
            }
            if (!"".equals(msg)) {
                errors.add(msg);
            }

        });

        result.setErrors(errors);
        result.setPassed(CollectionUtils.isEmpty(errors));
        return result;
    }

    /**
     * 校验类中属性注解
     *
     * @param value
     */
    public FastValidator check(Object value) {
        Class clazz = value.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String valid = (String) ReflectUtils.fieldValue(value, field);
            Annotation[] annotations = field.getAnnotations();
            if (null == annotations) {
                continue;
            }

            for (Annotation annotation : annotations) {
                if (annotation instanceof NotNull) {
                    notNull(valid);
                } else if (annotation instanceof Email) {
                    onEmail(valid);
                } else if (annotation instanceof IdCard) {
                    onIdCard(valid);
                } else if (annotation instanceof Phone) {
                    onPhone(valid);
                }
            }
        }

        return this;
    }

}
