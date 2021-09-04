package com.auto.enums;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:教师状态枚举值
 **/
public enum YesOrNoEnum {
    /**
     * 是
     */
    NO("0", "否"),
    YES("1", "是"),
    ;

    private String code;
    private String name;

    YesOrNoEnum(String code, String name) {
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
