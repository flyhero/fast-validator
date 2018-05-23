package cn.iflyapi.validator.core;

import cn.iflyapi.validator.exception.FastValidatorException;
import cn.iflyapi.validator.util.ReflectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flyhero <http://www.iflyapi.cn>
 * @date 2018/5/23 下午1:28
 */
public class FastValidator {

    private List<ValidatorElement> veLsit = new ArrayList<>();


    /**
     * 构建验证器
     *
     * @return
     */
    public static FastValidator start() {
        return new FastValidator();
    }

    /**
     * 验证null字符串
     *
     * @param strings
     */
    public void notNull(String... strings) {
        for (String s : strings) {
            if (StringUtils.isEmpty(s)) {
                throw new FastValidatorException(s + " can not be null or \" \"");
            }
        }
    }

    /**
     * 添加验证元素
     * @param target
     * @param min
     * @param max
     * @return
     */
    public FastValidator on(Object target, int min, int max) {
        this.veLsit.add(new ValidatorElement(target, min, max));
        return this;
    }

    public void end(){
        for (ValidatorElement validatorElement : veLsit) {
        }
    }
}
