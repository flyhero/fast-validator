package cn.iflyapi.validator.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flyhero
 * @date 2018-06-02 下午5:58
 */
public class Result {
    private boolean isPassed;
    private List<String> errors = new ArrayList<>();

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "Result{" +
                "isPassed=" + isPassed +
                ", errors=" + errors +
                '}';
    }
}
