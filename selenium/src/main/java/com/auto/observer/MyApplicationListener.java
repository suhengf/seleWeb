package com.auto.observer;

import com.auto.common.exception.BizException;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 创建一个事件监听器（观察者 observer）
 * listener defination
 */
@Order(2)
@Service("myApplicationListener")
public class MyApplicationListener implements ApplicationListener<MyTestEvent> {


    @Override
    public void onApplicationEvent(MyTestEvent applicationEvent) {
        //System.out.println(applicationEvent.getClass());
        //if(applicationEvent instanceof  MyTestEvent){
        try {
            Thread.sleep(10000);
            System.out.println("等待10s");
        } catch (Exception e) {
           throw new BizException("ssasajakjdkjakdkak");
        }
        //}
    }

}
