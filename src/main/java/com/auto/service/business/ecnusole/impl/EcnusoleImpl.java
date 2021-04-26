package com.auto.service.business.ecnusole.impl;

import com.auto.entity.UserInfo;
import com.auto.service.business.ecnusole.Ecnusole;
import com.auto.service.business.ecnusole.EcnusoleHandler;
import com.auto.utils.LoginUtils;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 华东师范大学作业处理
 * http://wljy.sole.ecnu.edu.cn/login
 * 11202837007019  424446123@qq.com
 * 目前已完成自动登录
 */
@Component
public class EcnusoleImpl implements Ecnusole {
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(200));

    @Autowired
    private EcnusoleHandler ecnusoleHandler;


    @Override
    public void excute() throws Exception {
        List<UserInfo> userInfoList = LoginUtils.parseUserList("src\\main\\files\\chromedriver.exe", "src\\main\\files\\huadongshifan\\huadongshifan.txt");
        //批量处理 学生信息
        handUserHouseWork(userInfoList, new ChromeOptions());
    }

    //批量处理 学生信息
    public void handUserHouseWork(List<UserInfo> userInfoList, ChromeOptions options) throws Exception {
        final WebDriver[] driver = {null};
        for (UserInfo userInfo : userInfoList) {
            Runnable task = new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    ecnusoleHandler.singleHandler(userInfo, options);
                }
            };
            executor.execute(task);
            Thread.sleep(10000);
        }
    }


}
