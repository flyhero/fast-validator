package cn.iflyapi.validator.util;

import java.util.Arrays;

/**
 * @author flyhero <http://www.iflyapi.cn>
 * @date 2018/5/23 下午1:59
 */
public class ArrayUtils {

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
