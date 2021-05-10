package com.auto.service.core.campus;

import com.auto.entity.UserInfo;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EcnusoleUniversityHandler  implements CampusOnlineHandler {

    private static final String  COMMON_COURSE = "/html/body/div[5]/div[1]/div[2]/div[3]";



    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver) throws Exception {
        log.info("华东师范作业逻辑处理start");
        //点击我的课程
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/a[1]")).click();
        Thread.sleep(3000);


        WebElement webElement = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/iframe"));
        driver.switchTo().frame(webElement);
        Thread.sleep(2000);

        for (int i = 1; i < 10; i++) {
            if(WebDriverUtils.check(driver, By.xpath("/html/body/div[2]/div[2]/div["+i+"]/div[1]/a"))){
                String titleName= driver.findElement(By.xpath("/html/body/div[2]/div[2]/div["+i+"]/div[2]/h3/a")).getText();
                if("形势与政策1".equals(titleName)){
                    continue;
                }

                driver.findElement(By.xpath("/html/body/div[2]/div[2]/div["+i+"]/div[1]/a")).click();

                //开始界面处理
                courseHandle(driver);



            }

        }



        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/h3/a")).click();



    }



    public void courseHandle(WebDriver driver) throws InterruptedException {
        //进入学习进度页面
        WebDriverUtils.switchToWindowByTitle(driver,"学习进度页面");
        Thread.sleep(2000);
        //判断 是否看过  若看过  跳过

    }



    @Override
    public EnumUniversityName universityName() {
            return EnumUniversityName.ECNUSOLE_UNIVERSITY;
    }
}
