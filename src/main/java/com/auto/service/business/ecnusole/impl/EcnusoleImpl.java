package com.auto.service.business.ecnusole.impl;

import com.auto.entity.UserInfo;
import com.auto.service.business.ecnusole.Ecnusole;
import com.auto.service.business.ecnusole.EcnusoleHandler;
import com.auto.service.business.ecust.ECUSTOnlineWork;
import com.auto.utils.FileParse;
import lombok.SneakyThrows;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.util.ArrayList;
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
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1,4,60L, TimeUnit.SECONDS,new LinkedBlockingDeque<>(200));

    @Autowired
    private EcnusoleHandler ecnusoleHandler;

    @Override
    public void excute() throws Exception {
        //目前引用的是本地配置
        File file = ResourceUtils.getFile("src\\main\\files\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getPath());
        ChromeOptions options = new ChromeOptions();
        List<UserInfo> userInfoList = new ArrayList<>();
        //解析得到 对应的学生名单  src/main/files/huadongshifan/huadongshifan.txt
        FileParse.readSaveList(userInfoList,"src\\main\\files\\huadongshifan\\huadongshifan.txt");
        //批量处理 学生信息
        int count = 4;
        ECUSTOnlineWork ecustOnlineWork = new ECUSTOnlineWork();
        handUserHouseWork(userInfoList,count);
    }





    //批量处理 学生信息
    public  void handUserHouseWork(List<UserInfo> userInfoList  ,int count) throws Exception {
        for (UserInfo userInfo:userInfoList) {
            Runnable task = new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    ecnusoleHandler.singleHandler( userInfo);
                }
            };
            executor.execute(task);
            Thread.sleep(6000);
        }
    }

}
