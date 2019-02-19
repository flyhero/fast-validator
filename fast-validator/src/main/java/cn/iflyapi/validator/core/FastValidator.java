package cn.iflyapi.validator.core;

import cn.iflyapi.validator.element.RangeElement;
import cn.iflyapi.validator.exception.FastValidatorException;

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
     * 验证空
     *
     * @param target
     * @param fieldName
     * @return
     */
    public FastValidator notEmpty(Object target, String fieldName) {
        if (null == target) {
            emptyResult(fieldName);
        }
        if (target instanceof String) {
            String s = (String) target;
            if (s.isEmpty()) {
                emptyResult(fieldName);
            }
        } else if (target instanceof Collection) {
            Collection collection = (Collection) target;
            if (collection.isEmpty()) {
                emptyResult(fieldName);
            }
        } else if (target instanceof Map) {
            Map map = (Map) target;
            if (map.isEmpty()) {
                emptyResult(fieldName);
            }
        }
        return this;
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

    public FastValidator on(Object target, int min, int max, String fieldName) {

        notEmpty(target, fieldName);

        if (target instanceof String) {
            String s = (String) target;
            if (s.length() > max || s.length() < min) {
                rangeResult(fieldName, min, max);
            }
        } else if (target instanceof Collection) {
            Collection collection = (Collection) target;
            if (collection.size() > max || collection.size() < min) {
                rangeResult(fieldName, min, max);
            }
        } else if (target instanceof Map) {
            Map map = (Map) target;
            if (map.size() > max || map.size() < min) {
                rangeResult(fieldName, min, max);
            }
        }else if (target instanceof Number) {
            if (target instanceof Integer) {
                int num = (int) target;
                if (num < min || num > max){
                    rangeResult(fieldName, min, max);
                }
            } else if (target instanceof Double) {
                double num = (double) target;
                if (num < min || num > max){
                    rangeResult(fieldName, min, max);
                }
            } else if (target instanceof Float) {
                float num = (float) target;
                if (num < min || num > max){
                    rangeResult(fieldName, min, max);
                }
            } else if (target instanceof Long) {
                long num = (long) target;
                if (num < min || num > max){
                    rangeResult(fieldName, min, max);
                }
            } else if (target instanceof Short) {
                short num = (short) target;
                if (num < min || num > max){
                    rangeResult(fieldName, min, max);
                }
            } else if (target instanceof Byte) {
                byte num = (byte) target;
                if (num < min || num > max){
                    rangeResult(fieldName, min, max);
                }
            }
        }
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
    public FastValidator onMax(Object target, int max, String fieldName) {
        return on(target, Integer.MIN_VALUE, max, fieldName);
    }

    /**
     * 验证邮箱
     *
     * @param target
     * @return
     */
    public FastValidator mustEmail(String target) {
        formatResult(validatorHandler.email(target));
        return this;
    }

    /**
     * 验证身份证
     *
     * @param target
     * @return
     */
    public FastValidator mustIdCard(String target) {
        formatResult(validatorHandler.idCard(target));
        return this;
    }

    /**
     * 验证手机号
     *
     * @param target
     * @return
     */
    public FastValidator mustPhone(String target) {
        formatResult(validatorHandler.phone(target));
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

    /**
     * 参数为空的结果
     *
     * @param fieldName
     */
    private void emptyResult(String fieldName) {
        if (isFailFast) {
            throw new FastValidatorException(fieldName + "不能为空");
        } else {
            formatResult(fieldName + "不能为空");
        }
    }

    /**
     * 参数超出范围的接口
     *
     * @param fieldName
     * @param min
     * @param max
     */
    private void rangeResult(String fieldName, int min, int max) {
        if (isFailFast) {
            throw new FastValidatorException(fieldName + "不能超出" + min + "到" + max + "的范围");
        } else {
            formatResult(fieldName + "不能超出" + min + "到" + max + "的范围");
        }
    }

    private void formatResult(String msg) {
        if (!msg.isEmpty()) {
            result.getErrors().add(msg);
        }
    }

}
