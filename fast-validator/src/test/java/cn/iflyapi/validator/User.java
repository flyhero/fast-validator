package cn.iflyapi.validator;

import cn.iflyapi.validator.annotation.Email;
import cn.iflyapi.validator.annotation.IdCard;
import cn.iflyapi.validator.annotation.NotNull;
import cn.iflyapi.validator.annotation.Range;

/**
 * @author: qfwang
 * @date: 2018-07-22 上午8:33
 */
public class User {

    @Email("邮箱")
    private String email;

    @IdCard
    private String card;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", card='" + card + '\'' +
                '}';
    }
}
