package cn.iflyapi.validator.enums;

/**
 * @author: qfwang
 * @date: 2018-07-03 下午8:51
 */
public enum ValidateDataEnum {
    EMAIL(1, "email"),
    ID_CARD(2, "id card"),
    IP(3, "IP"),
    PHONE(4, "phone");

    private int code;
    private String desc;

    ValidateDataEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
