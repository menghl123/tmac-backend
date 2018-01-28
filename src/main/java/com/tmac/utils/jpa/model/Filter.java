package com.tmac.utils.jpa.model;

import org.apache.commons.lang3.StringUtils;

public class Filter {
    private String field;
    private String compareType;
    private String value;

    public Boolean isPresent() {
        return StringUtils.isNotEmpty(this.value);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCompareType() {
        return compareType;
    }

    public void setCompareType(String compareType) {
        this.compareType = compareType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
