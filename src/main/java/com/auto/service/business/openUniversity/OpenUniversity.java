package com.auto.service.business.openUniversity;

import com.auto.entity.UserInfo;
import com.auto.service.business.core.CampusResolver;
import com.auto.service.business.core.EnumUniversityName;
import com.auto.utils.LoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OpenUniversity {

    @Autowired
    private CampusResolver campusResolver;

    //处理单个学生信息
    public void singleHandler(UserInfo userInfo, ChromeOptions options) throws Exception {
        log.info("开始逻辑处理");
        LoginUtils.login(userInfo, options,
                "https://www.oucbx.com/OUCWEB/LEAP/Web/html/home.html", "/html/body/div/div[1]/div[1]/div/div/span[3]", "/html/body/div/div[1]/div/div[2]/div/div[1]/div[2]/div[1]/div[1]/div[1]/input"
                , "/html/body/div/div[1]/div/div[2]/div/div[1]/div[2]/div[1]/div[2]/div[1]/input",
                "/html/body/div/div[1]/div/div[2]/div/div[1]/div[2]/div[1]/div[3]/div[1]/input", "/html/body/div/div[1]/div/div[2]/div/div[1]/div[2]/div[1]/button");
        log.info("用户{}登录成功,开始逻辑处理 start", userInfo.getUserId());
        campusResolver.getExecutor(EnumUniversityName.OPEN_UNIVERSITY.getCode()).onlineProcess();
        log.info("用户{}登录成功,开始逻辑处理 end", userInfo.getUserId());
    }




}
