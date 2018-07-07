package cn.iflyapi.validator.core;

import cn.iflyapi.validator.enums.MsgConstant;
import cn.iflyapi.validator.exception.FastValidatorException;
import cn.iflyapi.validator.util.RegexUtils;

/**
 * @author: flyhero
 * @date: 2018-07-02 下午9:24
 */
public class ValidatorHandler {

    private boolean isFastFail;

    private static final String EMPTY = "";

    public ValidatorHandler(boolean isFastFail) {
        this.isFastFail = isFastFail;
    }

    public String email(String email) {
        if (!RegexUtils.checkEmail(email)) {
            if (isFastFail) {
                throw new FastValidatorException(MsgConstant.EMAIL);
            }
            return MsgConstant.EMAIL;
        }
        return EMPTY;
    }

    public String phone(String phone) {
        if (!RegexUtils.checkPhone(phone)) {
            if (isFastFail) {
                throw new FastValidatorException(MsgConstant.PHONE);
            }
            return MsgConstant.PHONE;
        }
        return EMPTY;
    }

    public String idCard(String idCard) {
        if (!RegexUtils.checkIdCard(idCard)) {
            if (isFastFail) {
                throw new FastValidatorException(MsgConstant.ID_CARD);
            }
            return MsgConstant.ID_CARD;
        }
        return EMPTY;
    }

}
