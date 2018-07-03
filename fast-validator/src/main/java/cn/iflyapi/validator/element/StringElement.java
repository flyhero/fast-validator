package cn.iflyapi.validator.element;

/**
 * @author: flyhero
 * @date: 2018-07-03 下午8:45
 */
public class StringElement {

    private int type;
    private String value;

    public StringElement(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
