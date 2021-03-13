package com.auto.core.engine;

/**
 * 引擎名枚举
 */
public enum EngineName {

    CREATE_BARGAIN_ORDER(1, "砍价订单(推单)"),
    CREATE_B2C_ORDER(2, "B2C普通订单(推单)"),
    CREATE_B2C_OWN_ORDER(3, "B2C自营订单（核销订单）"),
    CREATE_B2C_OWN_ACTIVITY_ORDER(4, "B2C自营活动订单（核销订单）"),
    ;

    private Integer code;
    private String desc;

    EngineName(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static EngineName getByCode(Integer code) {

        for (EngineName each : values()) {
            if (code.equals(each.code)) {
                return each;
            }
        }

        throw new RuntimeException("engine code error");
    }

}
