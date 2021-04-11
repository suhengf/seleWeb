package com.auto.service.business.shanghaimaritimeuniversity.impl;


import com.auto.service.business.shanghaimaritimeuniversity.AskInfoHandler;
import com.auto.service.business.shanghaimaritimeuniversity.IMaritimeUniversity;
import com.auto.entity.UserInfo;
import com.auto.utils.FileParse;
import com.auto.utils.LoginUtils;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 上海海事大学
 */
@Service
public class MaritimeUniversityImpl implements IMaritimeUniversity {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MaritimeUniversityImpl.class);

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(200));

    @Autowired
    private AskInfoHandler askInfoHandler;

    public void excute() throws Exception {

        List<UserInfo> userInfoList = LoginUtils.parseUserList("src\\main\\files\\chromedriver.exe","src\\main\\files\\haishi\\20210124_0.txt");
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
                    askInfoHandler.singleHandler(userInfo,options);
                }
            };
            executor.execute(task);
            Thread.sleep(6000);
        }
    }


}
