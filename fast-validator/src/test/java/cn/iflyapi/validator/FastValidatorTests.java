package cn.iflyapi.validator;

import cn.iflyapi.validator.core.FastValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: qfwang
 * @date: 2018-05-23 下午3:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FastValidatorTests {

    @Test
    public void testOnNumber(){
        int num = 10;
        Double d = 20.5d;
        FastValidator.start().on(num, 1, 10).end();
        FastValidator.start().on(d, 1, 10).end();
        FastValidator.start().on(num, 1, 9).end();
    }

    @Test
    public void testOnStr(){
        String s = "hello world";
        String s1 = "welcome to iflyapi";
        FastValidator.start().on(s, 1, 20).on(s1,1,8).end();
        FastValidator.start().on(s, 1, 10).end();
    }
}
