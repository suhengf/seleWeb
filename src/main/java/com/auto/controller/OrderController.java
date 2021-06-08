package com.auto.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auto.async.AsyncUtil;
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
import com.auto.redis.RedisUtils;
import com.auto.task.EventReflector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisUtils redisUtils;


    @PostMapping("/createOrder")
    public Result<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        return createOrderService.createOrder(request);
    }

    //责任链加递归
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


    @GetMapping("/selectSlice/{count}")
    public void selectSlice(@PathVariable("count") Long count) {
        log.info(count+"页一分片");
        Long startTime = System.currentTimeMillis();
        List<String> userStr = userMapper.selectSlice(QueryParam.builder().userName("刘凌峰").fixNum(Integer.parseInt(String.valueOf(count))).build());
        userStr.forEach(idStr -> {
            List<User> userList = userMapper.selectSliceById(QueryParam.builder().id(Integer.parseInt(idStr)).userName("刘凌峰").fixNum(Integer.parseInt(String.valueOf(count))).build());
            for (User user : userList) {
                log.info("当前用户id{},密码{}", user.getId(), user.getPassWord());
            }
        });

        log.info((System.currentTimeMillis() - startTime) / 1000 + "秒");
    }


    @GetMapping("/selectAll/{count}")
    public void selectAll(@PathVariable("count") Long count) {
        log.info("查询所有");
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i <60 ; i++) {
            count=count*i;
            List<User> userList =userMapper.selectAll(QueryParam.builder().userName("刘凌峰").fixNum(Integer.parseInt(String.valueOf(count))).build());
            for (User user : userList) {
                log.info("当前用户id{},密码{}", user.getId(), user.getPassWord());
            }

        }


        log.info((System.currentTimeMillis() - startTime) / 1000 + "秒");
    }

    //观察者模式  事件发布  mq消息
    @PostMapping("/testObserver")
    public void testObserver() {
        log.info("开始测试");

        try {
            myPubisher.publishEvent(new MyTestEvent(User.builder().userName("llf").build()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        log.info("结束测试");

    }


    @PostMapping("/testAsync")
    public void testAsync() throws InterruptedException {
        log.info("执行A方法");
        Thread.sleep(10000);
        log.info("执行A方法结束");
        AsyncUtil.runAsync(() -> {
            try {
                log.info("执行B方法开始");
                Thread.sleep(20000);
                log.info("执行B方法结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        log.info("testAsync方法结束");

    }


    @PostMapping("/testAsyncDelay")
    public void testAsyncDelay() throws InterruptedException {
        log.info("执行A方法");
        Thread.sleep(10000);
        log.info("执行A方法结束");
        long startTime = System.currentTimeMillis();
        AsyncUtil.runAsyncDelay(() -> {
            try {
                log.info(String.valueOf(System.currentTimeMillis() - startTime));
                log.info("执行B方法开始");
                Thread.sleep(1000);
                log.info("执行B方法结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 60, TimeUnit.SECONDS);

        log.info("testAsync方法结束");

    }


    @PostMapping("/testcallAsync")
    public void testcallAsync() throws InterruptedException, ExecutionException {
        log.info("执行A方法");
        Thread.sleep(2000);
        log.info("执行A方法结束");
        long startTime = System.currentTimeMillis();
        CompletableFuture<List<String>> stringCompletableFuture = AsyncUtil.callAsync(() -> {
            List<String> listStr = new ArrayList<>();
            try {
//                log.info(String.valueOf(System.currentTimeMillis() - startTime));
                log.info("执行B方法开始");
                Thread.sleep(1000);
                log.info("执行B方法结束");
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(1000);
                    listStr.add("第"+i+"个");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return listStr;
        });
        log.info("     " + stringCompletableFuture.get());
        log.info(String.valueOf(System.currentTimeMillis() - startTime));
        log.info("testAsync方法结束");

    }


    @PostMapping("setAndGet")
    public String test(String k,String v){
        redisUtils.set(k,v);
        log.info((String) redisUtils.get(k));
        return (String) redisUtils.get(k);
    }

    @PostMapping("test")
    public Object test(){
        //step1 先从redis中取
        String strJson=(String) redisUtils.get("testCache");
        if (strJson==null){
            log.info("从db取值");
            // step2如果拿不到则从DB取值
            List<User> listNbaPlayer=userMapper.selectAll(QueryParam.builder().userName("刘凌峰").fixNum(Integer.parseInt(String.valueOf(1))).build());
            // step3 DB非空情况刷新redis值
            if (listNbaPlayer!=null){
                redisUtils.set("testCache", JSON.toJSONString(listNbaPlayer));
                return listNbaPlayer;
            }
            return null;
        }else
        {
            log.info("从redis缓存取值");
            return JSONObject.parseArray(strJson,User.class);
        }
    }

}
