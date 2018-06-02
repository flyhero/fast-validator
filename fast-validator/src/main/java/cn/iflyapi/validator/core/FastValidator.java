package cn.iflyapi.validator.core;

import cn.iflyapi.validator.exception.FastValidatorException;
import cn.iflyapi.validator.util.ArrayUtils;
import cn.iflyapi.validator.util.ReflectUtils;
import cn.iflyapi.validator.util.ValidatorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author flyhero <http://www.iflyapi.cn>
 * @date 2018/5/23 下午1:28
 */
public class FastValidator {

    private List<ValidatorElement> veLsit = new ArrayList<>();

    private Object[] objects = new Object[0];

    private boolean isFailFast = true;

    private FastValidator(boolean isFailFast) {
        this.isFailFast = isFailFast;
    }

    /**
     * 构建验证器
     *
     * @return
     */
    public static FastValidator start() {
        return start(true);
    }


    public static FastValidator start(boolean isFailFast) {
        return new FastValidator(isFailFast);
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
        this.veLsit.add(new ValidatorElement(target, min, max, desc));
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
     * 结束并验证
     */
    public void end() {

        if (null != objects) {
            for (Object o : objects) {
                if (Objects.isNull(o)) {
                    throw new FastValidatorException("params can not be null or \" \"");
                }
            }
        }

        veLsit.forEach(validatorElement -> {
            Object o = validatorElement.getValue();
            int min = validatorElement.getMin();
            int max = validatorElement.getMax();
            if (ReflectUtils.isNumber(o) || ReflectUtils.isString(o)) {
                ValidatorUtils.checkRange(o, min, max, validatorElement.getDesc());
            }
        });
    }

}
