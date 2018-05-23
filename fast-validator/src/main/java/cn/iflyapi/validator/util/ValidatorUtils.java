package cn.iflyapi.validator.util;

import cn.iflyapi.validator.exception.FastValidatorException;

/**
 *
 * @author flyhero <http://www.iflyapi.cn>
 * @date 2018/5/23 下午3:04
 */
public class ValidatorUtils {

    /**
     * 验证字符串
     * @param o
     * @param min
     * @param max
     */
    public static void checkStr(Object o, int min, int max) {
        int strLength = String.valueOf(o).length();
        checkRange(strLength, min, max, o);
    }

    /**
     * 验证数值
     * @param o
     * @param min
     * @param max
     */
    public static void checkNumber(Object o, int min, int max) {
        //TODO 这里把所有类型都强制转换为int，存在一定隐患
        int num = (int) o;
        checkRange(num, min, max, o);
    }

    /**
     * 验证范围
     * @param num
     * @param min
     * @param max
     * @param o
     */
    private static void checkRange(int num, int min, int max, Object o) {
        if (min != Integer.MIN_VALUE && max != Integer.MAX_VALUE) {
            if (num < min || num > max) {
                throw new FastValidatorException(o + " not in between " + min + "and " + max);
            }
            return;
        }
        if (min != Integer.MIN_VALUE) {
            if (num < min) {
                throw new FastValidatorException(o + " can not less than " + min);
            }
        }
        if (min == Integer.MIN_VALUE) {
            if (num > max) {
                throw new FastValidatorException(o + " can not greater than " + max);
            }
        }
    }
}
