package cn.demo.service.impl;

import cn.demo.dto.User;
import cn.demo.service.IDemoService;
import cn.iflyapi.validator.core.FastValidator;
import cn.iflyapi.validator.core.Result;
import org.springframework.stereotype.Service;

/**
 * @author flyhero
 * @date 2018-05-27 下午1:54
 */
@Service
public class DemoServiceImpl implements IDemoService {

    @Override
    public void setUser(User user) {
        Result result = FastValidator.doit().failSafe()
                .notEmpty(user.getName(), "name")
                .onMax(user.getPassword(), 10)
                .toResult();
        System.out.println(result.getErrors().toString());
    }
}
