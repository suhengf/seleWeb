package com.auto.event;

import org.springframework.context.ApplicationEvent;

public class UserEvent<T> extends ApplicationEvent {
    private  T data;


    public UserEvent(T source) {
        super(source);
        this.data= source;
    }

    public T getData(){
        return this.data;
    }

    public void setData(final T data){
        this.data=data;
    }

}
