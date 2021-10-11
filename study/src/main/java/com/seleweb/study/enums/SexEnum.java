package com.seleweb.study.enums;



public enum SexEnum {
    /**
     * 未审核
     */
    SEX_1("1", "未审核"),
    /**
     * 已审核
     */
    SEX_2("2", "已审核"),

    ;

    private String code;
    private String name;

    SexEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
