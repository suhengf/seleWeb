package com.auto.redis.expire;

import com.auto.common.exception.BizException;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.*;

/**
 * 分布式锁的实现
 */
public class ExpireDemo {

    private static Jedis jedis = new Jedis("127.0.0.1");


    public boolean lock(String key,String value,int timeOut){
        long result = jedis.setnx(key, value);
        jedis.expire(key,timeOut);
        if(result<=0){
            throw  new BizException("加锁失败");
        }
        return result>0;
    }

    /**
     * 支持身份验证功能的分布式锁释放机制
     * @param key
     */
    public boolean unlock(String key,String value ){
        String curValue = jedis.get(key);

        Pipeline pipeline= jedis.pipelined();
        try {
            pipeline.watch(key);
            if (StringUtils.isEmpty(curValue)||"null".equals(curValue)) {
                return true;
            }
            if(curValue.equals(value)){
                pipeline.multi();
                pipeline.del(key);
                pipeline.exec();
                return true;
            }else{
                return false;
            }

        } catch (Exception e) {
            return false;
        }finally {
            pipeline.close();
        }

    }


    public static void main(String[] args) throws InterruptedException {

        jedis.set("test_key", "test_value");
        jedis.expire("test_key",10);

        jedis.setex("test_key1", 20,"suheng");

//        Thread.sleep(12000);
         String test_key = jedis.get("test_key");
        System.out.println(StringUtils.isEmpty(test_key)?"空":"否");
        System.out.println(StringUtils.isEmpty(jedis.get("test_key1"))?"空":"否");
        /**
         * 支持超时自动分布式锁的释放
         */
        ExpireDemo expireDemo = new ExpireDemo();
        expireDemo.lock("su_heng","段晨曦",12);
        if (!expireDemo.unlock("su_heng","段晨曦1")) {
            System.out.println("不是本人不能释放锁");
        }

    }

}
