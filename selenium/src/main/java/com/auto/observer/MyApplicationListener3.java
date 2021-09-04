package com.auto.observer;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 创建另一个事件监听器
 * 对于继承了 ApplicationEvent的事件所有入参对应的监听器都会监听到，执行onApplicationEvent方法
 */
@Order(3)
@Service("myApplicationListener3")
public class MyApplicationListener3 implements ApplicationListener<MyTestEvent> {

    @Override
    public void onApplicationEvent(MyTestEvent applicationEvent) {
        try {
            Thread.sleep(4000);
            System.out.println("等待4s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            System.out.println("get to myApplicationListener3...");

    }
}
