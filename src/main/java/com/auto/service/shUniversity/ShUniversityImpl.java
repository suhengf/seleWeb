package com.auto.service.shUniversity;

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
 * 上海大学处理
 *          options.addArguments("headless");
 *  *        options.addArguments("no-sandbox");
 */
@Slf4j
@Component
public class ShUniversityImpl extends AbstractCommonUniversity implements University {
    @Autowired
    private CampusResolver campusResolver;

    @Override
    public String getFilePath() {
        return "D:\\houseWork\\shanghaidaxue.txt";
    }

    private static final String WIN_WEBDRIVER = "D:\\houseWork\\chromedriver.exe";

    @Override
    public EnumUniversityName getUniversityName() {
        return     EnumUniversityName.SH_UNIVERSITY;
    }

    @Override
    public void singleHandler(UserInfo userInfo, ChromeOptions options, int course) throws Exception {
        log.info("上海大学开始逻辑处理");
        WebDriver driver = null;
        try {
            options.addArguments("headless");
            options.addArguments("no-sandbox");
            driver = LoginUtils.shCoLogin(userInfo, options,
                    "https://cce.shu.edu.cn/", "/html/body/div[1]/div[3]/div/div[2]/ul/li[2]/div/div[2]/a",
                    "/html/body/div/div[3]/div/div/form/div[1]/input"
                    , "/html/body/div/div[3]/div/div/form/div[2]/input[2]",
                    "/html/body/div/div[3]/div/div/form/button");
            log.info("用户{}登录成功,开始逻辑处理 start", userInfo.getUserId());
            campusResolver.getExecutor(EnumUniversityName.SH_UNIVERSITY.getCode()).onlineProcess(userInfo, driver,course);
        } catch (Exception e) {
            log.error("异常",e);
        }finally {
            if (driver != null) {
                driver.quit();
            }
        }
        log.info("用户{}登录成功,开始逻辑处理 end", userInfo.getUserId());
    }

    public ThreadPoolParam getPoolParam(){
        return ThreadPoolParam.builder().corePoolSize(4).maximumPoolSize(4).build();
    }

    public String getWinWebdriverPath() {
        return WIN_WEBDRIVER;
    }

}
