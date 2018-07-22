package cn.iflyapi.validator;

import cn.iflyapi.validator.core.FastValidator;
import cn.iflyapi.validator.core.Result;
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


    @Test(expected = Exception.class)
    public void testOnNumber() {
        int num = 10;
        Double d = 20.5d;
        FastValidator.start().on(num, 1, 10).end();
        FastValidator.start().on(d, 1, 10, "this is a test").end();
        FastValidator.start().on(num, 1, 9).end();
    }

    @Test(expected = Exception.class)
    public void testOnStr() {
        String s = "hello world";
        String s1 = "welcome to iflyapi";
        FastValidator.start().on(s, 1, 20).on(s1, 1, 8).end();
        FastValidator.start().on(s, 1, 10).end();
    }

    @Test(expected = Exception.class)
    public void testNull() {
        FastValidator.start().notNull("nihao", "haha", null).end();
        Integer integer = new Integer(10);
        Integer b = null;
        int c = 0;
        FastValidator.start().notNull(b).notNull(c).end();
    }

    @Test(expected = Exception.class)
    public void testOnMin() {
        int num = 10;
        Result r = FastValidator.start().failSafe().onMin(num, 2).onMax(num, 9).end();
        System.out.println(r.toString());
    }

    @Test
    public void testCheck() {
        User user = new User();
        user.setEmail("qfwang@163.com");
        user.setCard("23");
        FastValidator.start().check(user).end();
    }
}
