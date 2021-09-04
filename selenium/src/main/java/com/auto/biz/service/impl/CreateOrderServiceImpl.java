package com.auto.biz.service.impl;

import com.auto.biz.bo.CreateOrderContext;
import com.auto.biz.bo.CreateOrderRequest;
import com.auto.biz.bo.CreateOrderResponse;
import com.auto.biz.service.CreateOrderService;

import com.auto.common.Result;
import com.auto.core.EngineResolver;
import com.auto.core.aop.ServerCatch;
import com.auto.core.engine.EngineExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderServiceImpl implements CreateOrderService {

    @Autowired
    private EngineResolver engineResolver;

//    @ServerCatch
    @Override
    public Result<CreateOrderResponse> createOrder(CreateOrderRequest request) {

        EngineExecutor engineExecutor = engineResolver.getExecutor(request.getOrderType());
        if (engineExecutor == null) {
            throw new RuntimeException("未找到创建订单执行器 orderType = " + request.getOrderType());
        }

        return engineExecutor.execute(request, new CreateOrderContext(), new CreateOrderResponse());
    }

}
