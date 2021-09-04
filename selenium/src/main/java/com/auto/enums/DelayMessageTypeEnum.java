package com.auto.enums;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:延时消息类型
 **/
public enum DelayMessageTypeEnum {
    /**
     * 订单支付
     */
    PAY_DELAY("1", "支付延时"),
    FINISH_DELAY("2", "完成延时"),
    ;

    private String code;
    private String name;

    DelayMessageTypeEnum(String code, String name) {
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
