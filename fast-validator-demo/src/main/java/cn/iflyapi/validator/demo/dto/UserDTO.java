package cn.iflyapi.validator.demo.dto;

/**
 * @author: qfwang
 * @date: 2018-05-27 下午2:02
 */
public class UserDTO {

    private String name;

    private String password;

    private Integer age;

    private Double higth;

    private Byte isLock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHigth() {
        return higth;
    }

    public void setHigth(Double higth) {
        this.higth = higth;
    }

    public Byte getIsLock() {
        return isLock;
    }

    public void setIsLock(Byte isLock) {
        this.isLock = isLock;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", higth=" + higth +
                ", isLock=" + isLock +
                '}';
    }
}
