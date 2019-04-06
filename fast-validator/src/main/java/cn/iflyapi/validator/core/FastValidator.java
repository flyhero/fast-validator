package cn.iflyapi.validator.core;

import cn.iflyapi.validator.exception.FastValidatorException;
import cn.iflyapi.validator.util.RegexUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author flyhero <http://www.iflyapi.cn>
 * @date 2018/5/23 下午1:28
 */
public class FastValidator {

    private Result result = new Result();

    private boolean isFailFast = true;


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
        return this;
    }

    /**
     * 安全失败
     *
     * @return
     */
    public FastValidator failSafe() {
        this.isFailFast = false;
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
        } else if (target.getClass().isArray()) {
            if (target instanceof String[]) {
                String[] s = (String[]) target;
                if (s.length == 0) {
                    emptyResult(fieldName);
                }
            } else if (target instanceof Integer[]) {
                Integer[] s = (Integer[]) target;
                if (s.length == 0) {
                    emptyResult(fieldName);
                }
            } else if (target instanceof Long[]) {
                Long[] s = (Long[]) target;
                if (s.length == 0) {
                    emptyResult(fieldName);
                }
            } else if (target instanceof Double[]) {
                Double[] s = (Double[]) target;
                if (s.length == 0) {
                    emptyResult(fieldName);
                }
            } else if (target instanceof Byte[]) {
                Byte[] s = (Byte[]) target;
                if (s.length == 0) {
                    emptyResult(fieldName);
                }
            } else if (target instanceof Short[]) {
                Short[] s = (Short[]) target;
                if (s.length == 0) {
                    emptyResult(fieldName);
                }
            } else {
                throw new RuntimeException("不支持该类型");
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

    //============================ 范围验证 开始=============================

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
        } else if (target instanceof Number) {
            if (target instanceof Integer) {
                int num = (int) target;
                if (num < min || num > max) {
                    rangeResult(fieldName, min, max);
                }
            } else if (target instanceof Double) {
                double num = (double) target;
                if (num < min || num > max) {
                    rangeResult(fieldName, min, max);
                }
            } else if (target instanceof Float) {
                float num = (float) target;
                if (num < min || num > max) {
                    rangeResult(fieldName, min, max);
                }
            } else if (target instanceof Long) {
                long num = (long) target;
                if (num < min || num > max) {
                    rangeResult(fieldName, min, max);
                }
            } else if (target instanceof Short) {
                short num = (short) target;
                if (num < min || num > max) {
                    rangeResult(fieldName, min, max);
                }
            } else if (target instanceof Byte) {
                byte num = (byte) target;
                if (num < min || num > max) {
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
     * 目标值必须为正数
     *
     * @param target
     * @param fieldName
     * @return
     */
    public FastValidator positive(Object target, String fieldName) {
        if (!(target instanceof Number)) {
            throw new FastValidatorException(fieldName + "必须为数字！");
        } else {
            if (target instanceof Integer) {
                int num = (int) target;
                if (num > 0) {
                    positiveResult(fieldName);
                }
            } else if (target instanceof Double) {
                double num = (double) target;
                if (num > 0) {
                    positiveResult(fieldName);
                }
            } else if (target instanceof Float) {
                float num = (float) target;
                if (num > 0) {
                    positiveResult(fieldName);
                }
            } else if (target instanceof Long) {
                long num = (long) target;
                if (num > 0) {
                    positiveResult(fieldName);
                }
            } else if (target instanceof Short) {
                short num = (short) target;
                if (num > 0) {
                    positiveResult(fieldName);
                }
            } else if (target instanceof Byte) {
                byte num = (byte) target;
                if (num > 0) {
                    positiveResult(fieldName);
                }
            }
        }
        return this;
    }

    /**
     * 目标值必须为负数
     *
     * @param target
     * @param fieldName
     * @return
     */
    public FastValidator negative(Object target, String fieldName) {
        if (!(target instanceof Number)) {
            throw new FastValidatorException(fieldName + "必须为数字！");
        } else {
            if (target instanceof Integer) {
                int num = (int) target;
                if (num < 0) {
                    negativeResult(fieldName);
                }
            } else if (target instanceof Double) {
                double num = (double) target;
                if (num < 0) {
                    negativeResult(fieldName);
                }
            } else if (target instanceof Float) {
                float num = (float) target;
                if (num < 0) {
                    negativeResult(fieldName);
                }
            } else if (target instanceof Long) {
                long num = (long) target;
                if (num < 0) {
                    negativeResult(fieldName);
                }
            } else if (target instanceof Short) {
                short num = (short) target;
                if (num < 0) {
                    negativeResult(fieldName);
                }
            } else if (target instanceof Byte) {
                byte num = (byte) target;
                if (num < 0) {
                    negativeResult(fieldName);
                }
            }
        }
        return this;
    }

    //============================ 范围验证 结束=============================


    //============================ 格式验证 开始=============================

    /**
     * 验证邮箱
     *
     * @param target
     * @return
     */
    public FastValidator mustEmail(String target) {
        if (!RegexUtils.checkEmail(target)) {
            typeResult("邮箱");
        }
        return this;
    }

    /**
     * 验证身份证
     *
     * @param target
     * @return
     */
    public FastValidator mustIdCard(String target) {
        if (!RegexUtils.checkIdCard(target)) {
            typeResult("身份证号");
        }
        return this;
    }

    /**
     * 验证手机号
     *
     * @param target
     * @return
     */
    public FastValidator mustPhone(String target) {
        if (!RegexUtils.checkPhone(target)) {
            typeResult("手机号");
        }
        return this;
    }

    //============================ 格式验证 结束=============================

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

    private void positiveResult(String fieldName) {
        if (isFailFast) {
            throw new FastValidatorException(fieldName + "必须为正数！");
        } else {
            formatResult(fieldName + "必须为正数！");
        }
    }

    private void negativeResult(String fieldName) {
        if (isFailFast) {
            throw new FastValidatorException(fieldName + "必须为负数！");
        } else {
            formatResult(fieldName + "必须为负数！");
        }
    }

    private void typeResult(String fieldName) {
        if (isFailFast) {
            throw new FastValidatorException(fieldName + " 格式不正确！");
        } else {
            formatResult(fieldName + " 格式不正确！");
        }
    }

    private void formatResult(String msg) {
        if (!msg.isEmpty()) {
            result.getErrors().add(msg);
        }
    }

}
