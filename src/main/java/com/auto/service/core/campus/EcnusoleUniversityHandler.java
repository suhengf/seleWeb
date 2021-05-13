package com.auto.service.core.campus;

import com.auto.entity.UserInfo;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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



    }



    public void courseHandle(WebDriver driver) throws InterruptedException {
        //进入学习进度页面
        WebDriverUtils.switchToWindowByTitle(driver,"学习进度页面");
        Thread.sleep(2000);
        //判断 是否看过  若看过  跳过
        String isOrange = "/html/body/div[5]/div[1]/div[2]/div[3]/div[1]/div[";

        AtomicInteger structureFirst = new AtomicInteger(1);
        while(true){
             String orangeXpath = isOrange+structureFirst+"]/h3/a/span[2]/em";
            if (WebDriverUtils.check(driver, By.xpath(orangeXpath))){
                needHandler(orangeXpath,driver);
            }else{
                break;
            }
            structureFirst.incrementAndGet();
        }

    }



    public void  needHandler(String orangeXpath,WebDriver driver) throws InterruptedException {
        int viedos = 0;
        String organTitle = driver.findElement(By.xpath(orangeXpath)).getText();
        try {
            viedos = Integer.parseInt(organTitle);
        } catch (NumberFormatException e) {
            return;
        }
        driver.findElement(By.xpath(orangeXpath)).click();
        Thread.sleep(5000);
        chainHandler(viedos,driver);
    }



    public static void chainHandler(int viedos,WebDriver driver)throws InterruptedException{
        //判断viedos 有多少个
        log.info("----------viedos------------"+viedos);
        List<String> listFrames = new ArrayList<>();
        switch (viedos) {
            case 1:
                listFrames.add("/html/body/div/div/p/div/iframe");
                break;
            case 2:
                listFrames.add("/html/body/div/div/p[1]/div/iframe");
                listFrames.add("/html/body/div/div/p[3]/div/iframe");
                break;
            case 3:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                listFrames.add("/html/body/div/div/div[2]/iframe");
                listFrames.add("/html/body/div/div/div[3]/iframe");
                break;
            case 4:
                listFrames.add("/html/body/div/div/p[2]/div/iframe");
                listFrames.add("/html/body/div/div/p[4]/span/span/div/iframe");
                listFrames.add("/html/body/div/div/p[6]/div/iframe");
                listFrames.add("/html/body/div/div/p[8]/span/span/span/span/span/div/iframe");
                break;
            case 5:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                listFrames.add("/html/body/div/div/div[2]/iframe");
                listFrames.add("/html/body/div/div/div[3]/iframe");
                listFrames.add("/html/body/div/div/div[4]/iframe");
                listFrames.add("/html/body/div/div/div[5]/iframe");
                break;
            case 6:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                listFrames.add("/html/body/div/div/div[2]/iframe");
                listFrames.add("/html/body/div/div/div[3]/iframe");
                listFrames.add("/html/body/div/div/div[4]/iframe");
                listFrames.add("/html/body/div/div/div[5]/iframe");
                listFrames.add("/html/body/div/div/div[6]/iframe");
                break;
            case 7:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                listFrames.add("/html/body/div/div/div[2]/iframe");
                listFrames.add("/html/body/div/div/div[3]/iframe");
                listFrames.add("/html/body/div/div/div[4]/iframe");
                listFrames.add("/html/body/div/div/div[5]/iframe");
                listFrames.add("/html/body/div/div/div[6]/iframe");
                listFrames.add("/html/body/div/div/div[7]/iframe");
                break;
            case 8:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                listFrames.add("/html/body/div/div/div[2]/iframe");
                listFrames.add("/html/body/div/div/div[3]/iframe");
                listFrames.add("/html/body/div/div/div[4]/iframe");
                listFrames.add("/html/body/div/div/div[5]/iframe");
                listFrames.add("/html/body/div/div/div[6]/iframe");
                listFrames.add("/html/body/div/div/div[7]/iframe");
                listFrames.add("/html/body/div/div/div[8]/iframe");
                break;
            case 9:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                listFrames.add("/html/body/div/div/div[2]/iframe");
                listFrames.add("/html/body/div/div/div[3]/iframe");
                listFrames.add("/html/body/div/div/div[4]/iframe");
                listFrames.add("/html/body/div/div/div[5]/iframe");
                listFrames.add("/html/body/div/div/div[6]/iframe");
                listFrames.add("/html/body/div/div/div[7]/iframe");
                listFrames.add("/html/body/div/div/div[8]/iframe");
                listFrames.add("/html/body/div/div/div[9]/iframe");
                break;
            case 10:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                listFrames.add("/html/body/div/div/div[2]/iframe");
                listFrames.add("/html/body/div/div/div[3]/iframe");
                listFrames.add("/html/body/div/div/div[4]/iframe");
                listFrames.add("/html/body/div/div/div[5]/iframe");
                listFrames.add("/html/body/div/div/div[6]/iframe");
                listFrames.add("/html/body/div/div/div[7]/iframe");
                listFrames.add("/html/body/div/div/div[8]/iframe");
                listFrames.add("/html/body/div/div/div[9]/iframe");
                listFrames.add("/html/body/div/div/div[10]/iframe");
                break;
        }

        for (String iframe: listFrames) {
            AtomicInteger counts = new AtomicInteger(1);
            while(true){
                if (WebDriverUtils.check(driver, By.xpath("/html/body/div[3]/div/div[2]/div[2]/iframe"))) {
                    break;
                }
                counts.incrementAndGet();

                if(200000==counts.get()){
                    log.info("重试6分钟之后  退出");
                    driver.quit();
                }
            }



            WebElement webElement0 = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/iframe"));
            driver.switchTo().frame(webElement0);

            String descIframe = WebDriverUtils.check(driver, By.xpath(iframe))?
                    iframe:(iframe.substring(0,19)+"p/"+iframe.substring(19));
            WebElement webElement1 = driver.findElement(By.xpath(descIframe));
            driver.switchTo().frame(webElement1);
            Thread.sleep(3000);
            //点击开始播放按钮
            log.info("点击开始播放按钮");
            driver.findElement(By.className("vjs-big-play-button")).click();
            Thread.sleep(8000);
            //计算剩余时间 当总时间 减去 当前播放时间剩余时间等于0  去播放下一个视频
            Thread.sleep(TimeUtils.getDiffTime(driver,8,10));
            driver.switchTo().defaultContent();
            Thread.sleep(5000);
        }
        driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/a")).click();
        log.info("结束返回");
        Thread.sleep(3000);
    }


    @Override
    public EnumUniversityName universityName() {
            return EnumUniversityName.ECNUSOLE_UNIVERSITY;
    }
}
