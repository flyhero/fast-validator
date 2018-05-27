package cn.iflyapi.validator.demo.service;

import cn.iflyapi.validator.annotation.NotNull;
import cn.iflyapi.validator.annotation.Range;
import cn.iflyapi.validator.annotation.Validator;
import cn.iflyapi.validator.demo.dto.UserDTO;

/**
 * @author: qfwang
 * @date: 2018-05-27 下午1:54
 */
public interface IDemoService {

    void setUser(UserDTO user);
}
