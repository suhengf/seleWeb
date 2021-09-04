package com.auto.enums;


public enum CommentStatusEnum {
    /**
     * 未审核
     */
    NON_CHECKED("1", "未审核"),
    /**
     * 已审核
     */
    CHECKED("2", "已审核"),
    /**
     * 未采纳
     */
    NOT_ADOPTED("3", "未采纳"),
    ;

    private String code;
    private String name;

    CommentStatusEnum(String code, String name) {
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
