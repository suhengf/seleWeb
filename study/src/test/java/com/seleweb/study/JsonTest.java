package com.seleweb.study;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonTest {



    @Test
    public void test() throws InterruptedException {
        //对象 json 对象
        String jsonStr = "{\n" +
                "  \"sex\": \"1\",\n" +
                "  \"age\": 1\n" +
                "}";
        User user= (User) JSONObject.parse(jsonStr);


    }


    public static void main(String[] args) {
        //对象 json 对象
        String jsonStr = "{\n" +
                "  \"sex\": \"1\",\n" +
                "  \"age\": 1\n" +
                "}";
        User user=  JSONObject.parseObject(jsonStr,User.class);
        System.out.println(user.getSex());
    }

}
