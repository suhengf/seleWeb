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
import com.auto.entity.QueryParam;
import com.auto.entity.User;
import com.auto.mapper.UserMapper;
import com.auto.observer.MyPubisher;
import com.auto.observer.MyTestEvent;
import com.auto.task.EventReflector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
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


    @Autowired
    private EventReflector eventReflector;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyPubisher myPubisher;


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


    @PostMapping("/demo")
    public void createDemo() {
        eventReflector.handler();
    }

    @PostMapping("/createData")
    public void createDate() {
        log.info("开始插入数据");
        for (int i = 70000; i < 1800000; i++) {
            User user = User.builder().userName("刘凌峰" + i).id(i).realName("llf"+i).passWord("password" + i).build();
            userMapper.insert(user);
        }
        log.info("数据插入成功");


    }


    @PostMapping("/selectSlice")
    public void selectSlice() {
        log.info("100页一分片");
        Long startTime = System.currentTimeMillis();
        int count = 100;
        List<String> userStr = userMapper.selectSlice(QueryParam.builder().userName("刘凌峰").fixNum(count).build());
        userStr.forEach(idStr -> {
            List<User> userList = userMapper.selectSliceById(QueryParam.builder().id(Integer.parseInt(idStr)).userName("刘凌峰").fixNum(count).build());
            for (User user : userList) {
                log.info("当前用户id{},密码{}", user.getId(), user.getPassWord());
            }
        });

        log.info((System.currentTimeMillis() - startTime) / 1000 + "秒");
    }


    @PostMapping("/selectAll")
    public void selectAll() {
        log.info("查询所有");
        Long startTime = System.currentTimeMillis();
        int count =1000;
        for (int i = 0; i <60 ; i++) {
            count=count*i;
            List<User> userList =userMapper.selectAll(QueryParam.builder().userName("刘凌峰").fixNum(count).build());
            for (User user : userList) {
                log.info("当前用户id{},密码{}", user.getId(), user.getPassWord());
            }

        }


        log.info((System.currentTimeMillis() - startTime) / 1000 + "秒");
    }


    @PostMapping("/testObserver")
    public void testObserver() {
        log.info("开始测试");
        myPubisher.publishEvent(new MyTestEvent(User.builder().userName("llf").build()));
        log.info("结束测试");

    }


}
