package cn.iflyapi.validator.util;

import cn.iflyapi.validator.exception.FastValidatorException;
import org.springframework.util.StringUtils;

/**
 * @author flyhero <http://www.iflyapi.cn>
 * @date 2018/5/23 下午3:04
 */
public class ValidatorUtils {


    /**
     * 验证范围
     *
     * @param o
     * @param min
     * @param max
     */
    public static String checkRange(Object o, int min, int max, String desc, boolean isFailFast) {
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        if (o instanceof Integer) {
            int num = (int) o;
            flag1 = (num < min || num > max);
            flag2 = num < min;
            flag3 = num > max;
        } else if (o instanceof Double) {
            double num = (double) o;
            flag1 = (num < min || num > max);
            flag2 = num < min;
            flag3 = num > max;
        } else if (o instanceof Float) {
            float num = (float) o;
            flag1 = (num < min || num > max);
            flag2 = num < min;
            flag3 = num > max;
        } else if (o instanceof Long) {
            long num = (long) o;
            flag1 = (num < min || num > max);
            flag2 = num < min;
            flag3 = num > max;
        } else if (o instanceof Short) {
            short num = (short) o;
            flag1 = (num < min || num > max);
            flag2 = num < min;
            flag3 = num > max;
        } else if (o instanceof Byte) {
            byte num = (byte) o;
            flag1 = (num < min || num > max);
            flag2 = num < min;
            flag3 = num > max;
        } else if (o instanceof String) {
            int num = String.valueOf(o).length();
            flag1 = (num < min || num > max);
            flag2 = num < min;
            flag3 = num > max;
        }
        if (min != Integer.MIN_VALUE && max != Integer.MAX_VALUE) {
            if (flag1) {
                String s = "[" + o + "] not in between " + min + " and " + max;
                if (!isFailFast) {
                    return !StringUtils.isEmpty(desc)? desc : s;
                }
                if (!StringUtils.isEmpty(desc)) {
                    throw new FastValidatorException(desc);
                }
                throw new FastValidatorException(s);
            }
            return "";
        }
        if (min != Integer.MIN_VALUE) {
            if (flag2) {
                String s = "[" + o + "] can not less than " + min;
                if (!isFailFast) {
                    return !StringUtils.isEmpty(desc)? desc : s;
                }
                if (!StringUtils.isEmpty(desc)) {
                    throw new FastValidatorException(desc);
                }
                throw new FastValidatorException(s);
            }
        }
        if (min == Integer.MIN_VALUE) {
            if (flag3) {
                String s = "[" + o + "] can not greater than " + max;
                if (!isFailFast) {
                    return !StringUtils.isEmpty(desc)? desc : s;
                }
                if (!StringUtils.isEmpty(desc)) {
                    throw new FastValidatorException(desc);
                }
                throw new FastValidatorException(s);
            }
        }
        return "";
    }


}
