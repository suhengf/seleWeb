package com.auto.service.core.campus;

import com.auto.common.exception.BizException;
import com.auto.entity.UserInfo;
import com.auto.service.abstr.ThreadPoolParam;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import jdk.nashorn.internal.ir.CallNode;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Component
public class ShUniversityHandler  implements CampusOnlineHandler {
    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver, int course) throws Exception {
        log.info("上海大学逻辑处理");
        Thread.sleep(4000);
        //课程信息 /html/body/div[2]/div[3]/table/tbody/tr/td[1]/div[2]/div/div[2]/div[2]/ul/li[4]/a
        String courseInfo ="/html/body/div[2]/div[3]/table/tbody/tr/td[1]/div[2]/div/div[2]/div[2]/ul/li[4]/a";

        WebDriverUtils.findElement(driver,courseInfo,"课程信息");
        driver.findElement(By.xpath(courseInfo)).click();

        Thread.sleep(8000);
        //网上学习
        String str = "/html/body/div[2]/div[3]/table/tbody/tr/td[2]/div/div[3]/div/table[2]/tbody/tr[";
        for (int i = 3; i < 15; i++) {
            String onlineStudy = str+i+"]/td[10]/a";
            if (WebDriverUtils.check(driver, By.xpath(onlineStudy))) {
                String text = str + i + "]/td[2]";
                String courseTitle = driver.findElement(By.xpath(text)).getText();
                log.info("courseTitle ->:{}", courseTitle);
                if (courseTitle.contains("形势与政策实践")
                        || courseTitle.contains("传统养生")
                        || courseTitle.contains("公关社交礼仪")) {
                    Thread.sleep(1000);
                    driver.findElement(By.xpath(onlineStudy)).click();
                    Thread.sleep(8000);
                    //视频逻辑处理
                    courseHandle(driver, courseTitle);
                }

            }

        }

        log.info("上海大学逻辑处理结束");

    }


    public void courseHandle(WebDriver driver,String courseTitle) throws InterruptedException {
        //进入学习进度页面  重新开了个页面 现代物流管理* - 首页
        WebDriverUtils.switchToWindowByTitle(driver, courseTitle + " - 首页");
        log.info("切换页面-->{}", courseTitle + " - 首页");
        Thread.sleep(15000);
        //开始学习

        try {
            String startStudy = "/html/body/div[1]/div/div[1]/div/div[2]/div[1]/div/div/div/div/table/tbody/tr/td[1]/div[1]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[3]";
            WebDriverUtils.findElement(driver, startStudy, "开始学习");
            if (WebDriverUtils.check(driver, By.xpath(startStudy))) {
                driver.findElement(By.xpath(startStudy)).click();
                log.info("点击开始学习");
                Thread.sleep(8000);
                String secTitle ="/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div[2]/div[1]/i";
                if (WebDriverUtils.check(driver, By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div[2]/div[1]/i"))) {
                    driver.findElement(By.xpath(secTitle)).click();
                    Thread.sleep(1000);
                }

//
                String topicXpath = "/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div[";
                for (int i = 1; i <10 ; i++) {
                    String fullXPath= topicXpath+i+"]/div[1]/i";
                    if(WebDriverUtils.check(driver, By.xpath(fullXPath))){
                        String titleName = driver.findElement(By.xpath(fullXPath)).getText();
                        driver.findElement(By.xpath(fullXPath)).click();
                        log.info("打开第 {} 主题 :{}", i,titleName);
                        String sonTitle ="/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div["+i+"]/div[2]/div[";
                        isFinishFlag(driver,sonTitle);

                    }

                }

            }
        } catch (Exception e) {
            log.info("异常",e);
            throw new BizException("异常");
        }

    }


    public void isFinishFlag(WebDriver driver,String sonTitle) throws Exception {

        for (int i = 1; i <25 ; i++) {
            String finishTitleFullPath= sonTitle+i+"]/span[1]/i";
            if(WebDriverUtils.check(driver, By.xpath(finishTitleFullPath))){
                String attributevalue = driver.findElement(By.xpath(finishTitleFullPath)).getAttribute("ng-switch-when");
                log.info("attributevalue----> {}",attributevalue);
                String clickTitle = finishTitleFullPath.replace("]/span[1]/i", "]/span[2]");
                String attributClick = sonTitle+i+"]/span[2]";
                String text = driver.findElement(By.xpath(clickTitle)).getText();
                if("2".equals(attributevalue)){

                    log.info("该小课程已经播放完成 :{}",text);
                    continue;
                }else if("1".equals(attributevalue)){
                    log.info("该小课程已经播放一部分 :{}",text);
                    Thread.sleep(2000);
                    driver.findElement(By.xpath(attributClick)).click();
                    Thread.sleep(1000);
                     long leftTime = leftTime(driver);
                     if(leftTime<=0){
                         continue;
                     }
                    judgeCondition(driver,leftTime);
                }else if("0".equals(attributevalue)){
                    log.info("该小课程刚开始播放 :{}",text);
                    Thread.sleep(2000);
                    driver.findElement(By.xpath(attributClick)).click();
                    Thread.sleep(1000);
                    long leftTime = leftTime(driver);
                    judgeCondition(driver,leftTime);
                }


            }


        }
    }


    public long leftTime(WebDriver driver) throws Exception {
        Actions action = new Actions(driver);
        Thread.sleep(3000);
        judgeCondition(driver,10000);
        String endTimeClassName = "vjs-duration-display";
        WebDriverUtils.findClassName(driver, endTimeClassName, "结束时间");
        WebElement element = driver.findElement(By.className(endTimeClassName));
        action.moveToElement(element).perform();
        String allviedoTime = element.getText();
        log.info("视频总时长:---->  {}",allviedoTime);
        String haveShowTimeXpath ="/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[1]/div/div[1]/div/span[4]";
        WebDriverUtils.findElement(driver, haveShowTimeXpath, "学习总时长");
        String haveStudyTime = driver.findElement(By.xpath(haveShowTimeXpath)).getText();
        log.info("学习总时长:---->  {}",haveStudyTime);

        String startTime = haveStudyTime.replace("秒", "");
        startTime = startTime.replace("分", ":").trim();
        log.info("学习总时长:---->  {}",startTime);
        long sleepTime = TimeUtils.diffSec(startTime, allviedoTime);
        log.info("sleepTime :-->{}",sleepTime);
        return sleepTime;
    }



    public void judgeCondition(WebDriver driver,long sleepTime) throws Exception {
        Actions action = new Actions(driver);
        AtomicInteger counts = new AtomicInteger(0);
        while(true){
            //判断结束标志
            if (WebDriverUtils.check(driver, By.xpath("/html/body/div[5]/div[3]/a[1]"))) {
                log.info("播放下一个");
                WebElement element = driver.findElement(By.xpath("/html/body/div[5]/div[3]/a[1]"));
                action.moveToElement(element).perform();
                WebDriverUtils.threeClick(element);
            }
            //继续播放
            if (WebDriverUtils.check(driver, By.xpath("/html/body/div[4]/div[3]/a[1]"))) {
                log.info("继续播放");
                Thread.sleep(3000);
                 WebElement element = driver.findElement(By.xpath("/html/body/div[4]/div[3]/a[1]"));
                action.moveToElement(element).perform();
                WebDriverUtils.threeClick(element);
            }
            Thread.sleep(1);
            if(sleepTime==counts.get()){
                log.info("重试6分钟之后  退出");
                break;
            }
            //作业课程
            if(WebDriverUtils.check(driver,By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[2]/div[2]/div/div/div[3]/div/div/div/button"))){
                log.info("遇见作业");
                break;
            }
            counts.incrementAndGet();
        }

    }



    public ThreadPoolParam getPoolParam(){
        return ThreadPoolParam.builder().corePoolSize(3).maximumPoolSize(4).build();
    }

    @Override
    public EnumUniversityName universityName() {
           return EnumUniversityName.SH_UNIVERSITY;
    }
}
