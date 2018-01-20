package com.tmac.enums;

public enum SexEnum {
    //男 女
    MAN("man"), WOMAN("woman");
    private String name;

    private SexEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SexEnum getByName(final String name) {
        for(SexEnum sexEnum : SexEnum.values()) {
            if(sexEnum.name == name) {
                return sexEnum;
            }
        }
        throw new IllegalArgumentException("No element matches " + name);
    }
}
