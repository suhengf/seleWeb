package com.auto.observer;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 创建另一个事件监听器
 * 对于继承了 ApplicationEvent的事件所有入参对应的监听器都会监听到，执行onApplicationEvent方法
 */
@Order(1)
@Service("myTestApplictionListener")
public class MyTestApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        //System.out.println(applicationEvent.getClass());
        //没有指定具体的自定义事件类型,spring初始化的一些事件也会进入,判断事件类型或者指定事件类型
        if(applicationEvent instanceof MyTestEvent){
            System.out.println("get to MyTestApplicationListener...");
        }
    }
}
