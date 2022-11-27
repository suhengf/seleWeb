package com.auto.service.commercial;

import com.auto.entity.LoginRequest;
import com.auto.entity.UserInfo;
import com.auto.service.abstr.AbstractCommonUniversity;
import com.auto.service.abstr.University;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.LoginUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommercialCollege  extends AbstractCommonUniversity implements University {


    @Override
    public String getFilePath() {
        return "src\\main\\files\\commercial\\commercialCollege.txt";
    }

    @Override
    public EnumUniversityName getUniversityName() {
        return EnumUniversityName.COMMERCIAL_COLLEGE;
    }

    @Override
    public void singleHandler(UserInfo userInfo, ChromeOptions options, int course) throws Exception {
        WebDriver driver = new ChromeDriver(options);
        LoginRequest loginRequest = LoginRequest.builder()
                .url("https://jxjy.sbs.edu.cn/")
                .iframe( "//*[@id=\"iframeLogin\"]")
                .userNameLoc("/html/body/div/div/div/div[2]/div[2]/div/div[2]/span/input")
                .passWordLoc("/html/body/div/div/div/div[2]/div[2]/div/div[3]/span/input")
                .loginButtonLoc("/html/body/div/div/div/div[2]/div[2]/div/div[5]/button")
                .build();
        LoginUtils.commercialLogin(driver,userInfo,loginRequest);
        //解决登录 问题
        Thread.sleep(1000);

        driver.quit();
    }
}
