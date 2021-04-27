package com.auto.service.sbsedu.impl;

import com.auto.entity.UserInfo;
import com.auto.service.sbsedu.Sbsedu;
import com.auto.service.sbsedu.SbseduHandler;
import com.auto.utils.LoginUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 上海商学院作业处理
 *  https://jxjy.sbs.edu.cn/
 *  B21522806029   Cyk123456
 */
@Slf4j
@Component
public class SbseduImpl implements Sbsedu {


    private ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(200));

    @Autowired
    private SbseduHandler sbseduHandler;

    public void excute() throws Exception {

        List<UserInfo> userInfoList = LoginUtils.parseUserList("src\\main\\files\\chromedriver.exe","src\\main\\files\\shangxueyuan\\sbs.txt");
        //批量处理 学生信息
        handUserHouseWork(userInfoList,new ChromeOptions());

    }



    //批量处理 学生信息
    public void handUserHouseWork(List<UserInfo> userInfoList,ChromeOptions options) throws Exception {
        final WebDriver[] driver = {null};
        for (UserInfo userInfo : userInfoList) {
            Runnable task = new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    sbseduHandler.singleHandler(userInfo,options);
                }
            };
            executor.execute(task);
            Thread.sleep(6000);
        }
    }
}
