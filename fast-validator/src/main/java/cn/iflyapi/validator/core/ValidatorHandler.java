package cn.iflyapi.validator.core;

import cn.iflyapi.validator.enums.MsgConstant;
import cn.iflyapi.validator.exception.FastValidatorException;
import cn.iflyapi.validator.util.RegexUtils;

/**
 * @author: flyhero
 * @date: 2018-07-02 下午9:24
 */
public class ValidatorHandler {

    public static String email(String email, boolean isFastFail) {
        if (!RegexUtils.checkEmail(email)) {
            if (isFastFail) {
                throw new FastValidatorException(MsgConstant.EMAIL);
            }
            return MsgConstant.EMAIL;
        }
        return "";
    }

    public static String phone(String phone, boolean isFastFail) {
        if (!RegexUtils.checkPhone(phone)) {
            if (isFastFail) {
                throw new FastValidatorException(MsgConstant.PHONE);
            }
            return MsgConstant.PHONE;
        }
        return "";
    }

    public static String idCard(String idCard, boolean isFastFail) {
        if (!RegexUtils.checkIdCard(idCard)) {
            if (isFastFail) {
                throw new FastValidatorException(MsgConstant.ID_CARD);
            }
            return MsgConstant.ID_CARD;
        }
        return "";
    }

}
