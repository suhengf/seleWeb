package com.auto.service.ecnusole;

import com.auto.entity.UserInfo;
import com.auto.service.abstr.AbstractCommonUniversity;
import com.auto.service.abstr.ThreadPoolParam;
import com.auto.service.abstr.University;
import com.auto.service.core.CampusResolver;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.LoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 华东师范大学作业处理
 * https://wljy.ecnusole.com/mh
 * 11202837007019  424446123@qq.com
 * 目前已完成自动登录
 */
@Slf4j
@Component
public class EcnusoleImpl extends AbstractCommonUniversity implements University {
    @Autowired
    private CampusResolver campusResolver;

    @Override
    public String getFilePath() {
        return "src\\main\\files\\huadongshifan\\huadongshifan.txt";
    }

    @Override
    public EnumUniversityName getUniversityName() {
        return EnumUniversityName.ECNUSOLE_UNIVERSITY;
    }


    @Override
    public void singleHandler(UserInfo userInfo, ChromeOptions options,int course) throws Exception {
        log.info("开始逻辑处理");
        WebDriver driver = null;
        try {
        // /html/body/div/div[1]/div[1]/div/div/span[3]
        driver = LoginUtils.hsdLogin(userInfo, options,
                "https://wljy.ecnusole.com/mh", "/html/body/div[1]/div/div/div[2]/div/a[1]", "/html/body/div/div/div/div[2]/div[1]/a[2]","/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[1]/input"
                , "/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[2]/input",
                "/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[3]/input", "/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[4]/button");
        log.info("用户{}登录成功,开始逻辑处理 start", userInfo.getUserId());
            campusResolver.getExecutor(EnumUniversityName.ECNUSOLE_UNIVERSITY.getCode()).onlineProcess(userInfo, driver,course);
        } catch (Exception e) {
           log.error("异常",e);
            if (driver != null) {
                driver.quit();
            }
        }finally {
            if (driver != null) {
                driver.quit();
            }
        }
        log.info("用户{}登录成功,开始逻辑处理 end", userInfo.getUserId());
    }

    public ThreadPoolParam getPoolParam(){
        return ThreadPoolParam.builder().corePoolSize(1).maximumPoolSize(4).build();
    }


}
