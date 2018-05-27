package cn.iflyapi.validator.demo.controller;

import cn.iflyapi.validator.demo.dto.UserDTO;
import cn.iflyapi.validator.demo.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: qfwang
 * @date: 2018-05-27 下午1:53
 */
@RestController
public class DemoController {

    @Autowired
    private IDemoService iDemoService;

    @GetMapping("demo")
    public String test1(){
        UserDTO userDTO = new UserDTO();
        userDTO.setName("flyhero");
        iDemoService.setUser(userDTO);
        return "ok";
    }
}
