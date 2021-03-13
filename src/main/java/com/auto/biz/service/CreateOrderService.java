package com.auto.biz.service;

import com.auto.biz.bo.CreateOrderRequest;
import com.auto.biz.bo.CreateOrderResponse;
import com.auto.common.Result;


public interface CreateOrderService {

    Result<CreateOrderResponse> createOrder(CreateOrderRequest request);

}
