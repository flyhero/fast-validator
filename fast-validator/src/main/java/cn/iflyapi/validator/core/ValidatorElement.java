package cn.iflyapi.validator.core;

/**
 * 待验证元素
 * @author flyhero <http://www.iflyapi.cn>
 * @date 2018/5/23 下午1:27
 */
public class ValidatorElement {

    private Object value;
    private int min;
    private int max;

    public ValidatorElement(Object value, int min, int max) {
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
