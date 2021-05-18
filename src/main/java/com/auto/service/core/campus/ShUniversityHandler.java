package com.auto.service.core.campus;

import com.auto.entity.UserInfo;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ShUniversityHandler  implements CampusOnlineHandler {
    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver, int course) throws Exception {
        log.info("上海大学逻辑处理");
        Thread.sleep(30000);
        //课程信息
        driver.findElement(By.xpath("/html/body/div[2]/div[3]/table/tbody/tr/td[1]/div[2]/div/div[2]/div[2]/ul/li[4]/a")).click();
        Thread.sleep(30000);
        //网上学习
        String str = "/html/body/div[2]/div[3]/table/tbody/tr/td[2]/div/div[3]/div/table[2]/tbody/tr[";
        for (int i = 3; i < 15; i++) {
            String onlineStudy = str+i+"]/td[10]/a";
            if (WebDriverUtils.check(driver, By.xpath(onlineStudy))) {
                String text = str+i+"]/td[2]";
                String courseTitle = driver.findElement(By.xpath(onlineStudy)).getText();
                if(text.contains("现代物流管理")){
                    driver.findElement(By.xpath(onlineStudy)).click();
                    //视频逻辑处理
                    courseHandle(driver,courseTitle);
                }

            }

        }

        log.info("上海大学逻辑处理结束");

    }


    public void courseHandle(WebDriver driver,String courseTitle) throws InterruptedException {
        //进入学习进度页面  重新开了个页面
        WebDriverUtils.switchToWindowByTitle(driver,courseTitle+" - 首页");
        Thread.sleep(20000);
        //开始学习
        String startStudy = "/html/body/div[1]/div/div[1]/div/div[2]/div[1]/div/div/div/div/table/tbody/tr/td[1]/div[1]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[3]/button";
        if (WebDriverUtils.check(driver, By.xpath(startStudy))) {
            driver.findElement(By.xpath(startStudy)).click();
            //视频处理逻辑
        }


    }


    @Override
    public EnumUniversityName universityName() {
           return EnumUniversityName.SH_UNIVERSITY;
    }
}
