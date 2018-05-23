package cn.iflyapi.validator.core;

import cn.iflyapi.validator.exception.FlyValidatorException;
import org.springframework.util.StringUtils;

/**
 * @author: qfwang
 * @date: 2018-05-23 上午10:13
 */
public class FlyValidator {

    public static void notNull(String... strings){
        for (String s : strings) {
            if (StringUtils.isEmpty(s)) {
                throw new FlyValidatorException(s +" can not be null or \" \"");
            }
        }
    }
}
