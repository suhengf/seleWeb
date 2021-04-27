package com.auto.service.beijing;

import com.auto.service.commTest.AlertLogin;
import com.auto.entity.UserInfo;
import com.auto.utils.FileParse;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class BeijingLanuageOnlineWork {
    public static void main(String[] args) throws Exception {
        //目前引用的是本地配置
        File file = ResourceUtils.getFile("D:\\file\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getPath());
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("headless");
//        options.addArguments("no-sandbox");
        List<UserInfo> userInfoList = new ArrayList<>();
        //解析得到 对应的学生名单
        FileParse.readSaveList(userInfoList,"D:\\file\\bjonlineWork.txt");
        //登录
        AlertLogin.userLogin(userInfoList,options);



    }


}
