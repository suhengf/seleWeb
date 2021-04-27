package com.auto.service.business.ecnusole;

import com.auto.entity.UserInfo;
import com.auto.service.business.core.CampusResolver;
import com.auto.service.business.core.EnumUniversityName;
import com.auto.utils.LoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EcnusoleHandler {

    @Autowired
    private CampusResolver campusResolver;

    //处理单个学生信息
    public void singleHandler(UserInfo userInfo, ChromeOptions options) throws Exception {
        log.info("开始逻辑处理");
        // /html/body/div/div[1]/div[1]/div/div/span[3]
        WebDriver driver = LoginUtils.hsdLogin(userInfo, options,
                "https://wljy.ecnusole.com/mh", "/html/body/div[1]/div/div/div[2]/div/a[1]", "/html/body/div/div/div/div[2]/div[1]/a[2]","/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[1]/input"
                , "/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[2]/input",
                "/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[3]/input", "/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[4]/button");
        log.info("用户{}登录成功,开始逻辑处理 start", userInfo.getUserId());
        campusResolver.getExecutor(EnumUniversityName.ECNUSOLE_UNIVERSITY.getCode()).onlineProcess(userInfo, driver);
        log.info("用户{}登录成功,开始逻辑处理 end", userInfo.getUserId());

    }






}
