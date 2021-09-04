package com.auto.event;

import com.alibaba.fastjson.JSONObject;
import com.auto.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserListener {

    @EventListener
    public void printLog(UserEvent<User> userEvent){
         User data = userEvent.getData();
         log.info("data:", JSONObject.toJSONString(data));
    }

}
