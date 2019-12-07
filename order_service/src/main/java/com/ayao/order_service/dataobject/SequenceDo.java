package com.ayao.order_service.dataobject;

import java.io.Serializable;

public class SequenceDo implements Serializable {
    private static final long serialVersionUID = -6732085443144096207L;
    private String name;

    private Integer currentValue;

    private Integer step;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}