package cn.demo.controller;

import cn.demo.dto.Test2;
import cn.iflyapi.validator.annotation.FastValid;
import cn.demo.dto.Test;
import cn.demo.dto.User;
import cn.demo.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: flyhero
 * @date: 2018-05-27 下午1:53
 */
@RestController
public class DemoController {

    @Autowired
    private IDemoService iDemoService;

    @GetMapping("test/service")
    public String testServiceValid() {

        User user = new User();
        user.setName("flyhero");
        user.setPassword("3jkwehrwq9812332");
        iDemoService.setUser(user);

        return "ok";
    }

    @GetMapping("test/controller")
    public String testControllerValid(@FastValid Test test, @FastValid Test2 test2){
        System.out.println(test.getName());
        return "test";
    }

}
