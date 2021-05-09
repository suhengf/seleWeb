package com.auto.observer;

import org.springframework.context.ApplicationEvent;

/**
 * 定义一个事件（主题 subject）
 * define an event
 */
public class MyTestEvent extends ApplicationEvent {

    public MyTestEvent(Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

}
