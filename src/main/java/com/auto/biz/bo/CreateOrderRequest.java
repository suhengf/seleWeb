package com.auto.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    private Integer orderType;

    private Long userId;

    private Long addressId;

    private Long cartId;

    private List<SkuInfo> skuInfoList;

    private Long bargainInfoId;

    @Getter
    @Setter
    public static class SkuInfo {

        private Long skuId;

        private Integer num;

    }

}
