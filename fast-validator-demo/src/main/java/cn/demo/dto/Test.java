package cn.demo.dto;

import cn.iflyapi.validator.annotation.Email;
import cn.iflyapi.validator.annotation.NotEmpty;

/**
 * @author flyhero
 * @date 2019-04-06 10:13 AM
 */
public class Test {

    @Email
    private String mail;

    @NotEmpty
    private String name;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
