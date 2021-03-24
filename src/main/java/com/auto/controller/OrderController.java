package com.auto.controller;


import com.auto.biz.bo.CreateOrderRequest;
import com.auto.biz.bo.CreateOrderResponse;
import com.auto.biz.service.CreateOrderService;
import com.auto.chain.CreditApplyMqChain;
import com.auto.chain.CreditTradeMqChainHandler;
import com.auto.chain.Request;
import com.auto.chain.TradeContext;
import com.auto.common.Result;
import com.auto.core.EngineResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CreateOrderService createOrderService;
    @Autowired
    private EngineResolver engineResolver;

    @Autowired
    @Qualifier("creditApplyMqHandlerList")
    @Lazy
    private List<CreditApplyMqChain> creditApplyMqChainList;






    @PostMapping("/createOrder")
    public Result<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        return createOrderService.createOrder(request);
    }

    @PostMapping("/chainTest")
    public void createOrder1() {
        List<CreditApplyMqChain> chains = creditApplyMqChainList;
        Request request = new Request();
        TradeContext response = new TradeContext();
        CreditTradeMqChainHandler creditTradeMqChainHandler = new CreditTradeMqChainHandler(chains, 0, request, response);
        response = creditTradeMqChainHandler.process(request, response);
    }




}
