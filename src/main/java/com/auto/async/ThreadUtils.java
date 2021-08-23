package com.auto.async;

import java.util.HashMap;

/**
 * 多线程
 * 1.join  ？等待线程执行结束
 * 2.promise模式 get方法需要堵塞等待  futureTask
 */
public class ThreadUtils {

    public static void main(String[] args) {
        HashMap<String, String> name = new HashMap<>();
        name.put("张三","测试数据");
        name.put("李四","测试数据1");
    }

}





