package com.auto.enums;

import java.util.Objects;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单的状态
 **/
public enum OrderStatusEnum {

    /**
     * 创建订单后订单待付款
     */
    WAITING_FOR_PAY("0", "待付款"),

    /**
     * 预定下单之后取消订单
     */
    CANCELED("1", "已取消"),

    /**
     * 已支付
     */
    PAID("2", "已支付"),

    /**
     * 待评价
     */
    WAITING_FOR_COMMENT("3", "待评价"),

    /**
     * 已完成
     */
    FINISHED("4", "已评价")
    ;

    private String status;

    private String desc;

    OrderStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 根据订单状态获取描述信息
     *
     * @param status 状态
     * @return 结果
     */
    public static String getDescByStatus(String status) {
        OrderStatusEnum[] orderStatusEnums = OrderStatusEnum.values();
        for (OrderStatusEnum orderStatusEnum : orderStatusEnums) {
            if (Objects.equals(orderStatusEnum.getStatus(), status)) {
                return orderStatusEnum.getDesc();
            }
        }
        return null;
    }
}