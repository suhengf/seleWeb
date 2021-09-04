package com.auto.observer;

import com.auto.entity.User;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 创建另一个事件监听器
 * 对于继承了 ApplicationEvent的事件所有入参对应的监听器都会监听到，执行onApplicationEvent方法
 */
@Service("myApplicationListener4")
public class MyApplicationListener4 implements ApplicationListener<MyTestEvent> {

    @Async
    @Override
    public void onApplicationEvent(MyTestEvent applicationEvent) {
        User source = (User) applicationEvent.getSource();
         String userName = source.getUserName();
        System.out.println("姓名是:"+userName);
        System.out.println("get to myApplicationListener4...");

    }
}
