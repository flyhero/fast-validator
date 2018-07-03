package cn.iflyapi.validator.element;

import java.util.Objects;

/**
 * 待验证元素
 *
 * @author flyhero <http://www.iflyapi.cn>
 * @date 2018/5/23 下午1:27
 */
public class RangeElement {

    private Object value;
    private int min;
    private int max;
    private String desc;

    public RangeElement(Object value, int min, int max) {
        this.value = Objects.requireNonNull(value);
        this.min = min;
        this.max = max;
    }

    public RangeElement(Object value, int min, int max, String desc) {
        this.value = Objects.requireNonNull(value);;
        this.min = min;
        this.max = max;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
