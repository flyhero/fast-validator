package cn.iflyapi.validator;

import cn.iflyapi.validator.annotation.*;

/**
 * @author: qfwang
 * @date: 2018-07-22 上午8:33
 */
public class User {

    @Email("邮箱")
    private String email;

//    @IdCard
    private String card;

    @Phone
    private String phone;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", card='" + card + '\'' +
                '}';
    }
}
