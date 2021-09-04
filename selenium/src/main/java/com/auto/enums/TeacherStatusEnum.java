package com.auto.enums;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:教师状态枚举值
 **/
public enum TeacherStatusEnum {
    /**
     * 空闲
     */
    FREE("1", "空闲"),
    ;

    private String code;
    private String name;

    TeacherStatusEnum(String code, String name) {
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
