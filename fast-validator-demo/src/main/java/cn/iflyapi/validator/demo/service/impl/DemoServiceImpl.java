package cn.iflyapi.validator.demo.service.impl;

import cn.iflyapi.validator.annotation.NotNull;
import cn.iflyapi.validator.annotation.Range;
import cn.iflyapi.validator.annotation.Validator;
import cn.iflyapi.validator.demo.dto.UserDTO;
import cn.iflyapi.validator.demo.service.IDemoService;
import org.springframework.stereotype.Service;

/**
 * @author: qfwang
 * @date: 2018-05-27 下午1:54
 */
@Service
public class DemoServiceImpl implements IDemoService {

    @Override
    @Validator(value = "描述", notNull = @NotNull({"name", "password"}), range = {@Range(value = "age", range = "(20,50)"), @Range(value = "hight", min = 10)})
    public void setUser(UserDTO user) {
//        FastValidator.start().notNull(user.getName(),user.getPassword()).end();
        System.out.println(user.toString());
    }
}
