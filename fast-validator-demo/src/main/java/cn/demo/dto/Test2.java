package cn.demo.dto;

import cn.iflyapi.validator.annotation.On;

/**
 * @author flyhero
 * @date 2019-04-14 7:23 PM
 */
public class Test2 {

    @On(min = 10,max = 20,desc = "年龄")
    private int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
