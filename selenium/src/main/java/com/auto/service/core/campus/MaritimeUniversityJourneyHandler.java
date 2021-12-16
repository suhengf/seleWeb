package com.auto.service.core.campus;

import com.auto.entity.UserInfo;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MaritimeUniversityJourneyHandler implements CampusOnlineHandler {





    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver,int course) throws Exception {
        log.info("开放大学旅游学院start");
        String task = "/html/body/app-root/app-index/div[2]/div/div/app-page-content/div/div[1]/div[2]/div/div[2]/div[";
        for (int i = 1; i < 15; i++) {
            String  courseNameXpath = task+i+"]/div[2]/h3";
            task = task+i+"]/div[2]/div/div[3]/button";
            String courseName = driver.findElement(By.xpath(courseNameXpath)).getText();
           log.info("task:{}"+task);
            driver.findElement(By.xpath(task)).click();
            Thread.sleep(3000);

            // 点击形考任务
            driver.findElement(By.xpath("/html/body/div[2]/header/ul[1]/li[2]/a")).click();

            //页面跳转
            WebDriverUtils.switchToWindowByTitle(driver, courseName);
            log.info("切换 {} 界面之后 开始执行逻辑处理",courseName);
            executeHouseWork(driver);
        }





    }

    /**
     * 处理形考任务
     * @param driver
     */
    private void executeHouseWork(WebDriver driver) throws InterruptedException {
        //1.形考任务 /html/body/div[2]/div[3]/div/div/div[1]/div/div[3]/a
        //2.形考任务 /html/body/div[2]/div[3]/div/div/div[2]/div/div[3]/a
        String houseWor ="/html/body/div[2]/div[3]/div/div/div[";
        for (int i = 1; i < 10; i++) {
            houseWor=  houseWor+"]/div/div[3]/a";
            driver.findElement(By.xpath(houseWor)).click();
            Thread.sleep(3000);
            //处理作业任务
            //点击上次试答
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[3]/div/section/div/div/div[3]/div/form/button")).click();


        }


    }











    @Override
    public EnumUniversityName universityName() {
            return EnumUniversityName.OPEN_UNIVERSITY_JOURNEY;
    }
}
