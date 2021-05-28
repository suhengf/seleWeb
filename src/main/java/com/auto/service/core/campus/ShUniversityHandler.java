package com.auto.service.core.campus;

import com.auto.common.exception.BizException;
import com.auto.entity.UserInfo;
import com.auto.service.abstr.ThreadPoolParam;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.service.shUniversity.EnumCourseName;
import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Component
public class ShUniversityHandler  implements CampusOnlineHandler {
    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver, int course) throws Exception {
        log.info("上海大学逻辑处理");
        Thread.sleep(6000);
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
                String desc = EnumCourseName.getEnumCourseName(course).getDesc();
                log.info("处理的科目为: {}",desc);
                if( courseTitle.contains(desc)){
                    Thread.sleep(9000);
                    WebDriverUtils.findElement(driver, onlineStudy, "点击课程");
                    WebDriverUtils.click(driver,onlineStudy);
                    Thread.sleep(8000);
                    //视频逻辑处理
                    courseHandle(driver, courseTitle);
                }

            }

        }

        log.info("上海大学逻辑处理结束");

    }


    public void courseHandle(WebDriver driver, String courseTitle) throws InterruptedException {
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

                if (!courseTitle.contains(EnumCourseName.gonggongguanxi.getDesc())) {
                    String secTitle ="/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div[2]/div[1]/i";
                    if (WebDriverUtils.check(driver, By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div[2]/div[1]/i"))) {
                        judgeCondition(driver);
                        WebDriverUtils.click(driver,secTitle);
                        Thread.sleep(1000);
                    }

                }else{
                    judgeCondition(driver);
                    String topicXpath = "/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div[1]/div[1]/i";
                    WebDriverUtils.click(driver,topicXpath);
                    Thread.sleep(3000);
                }

//
                String topicXpath = "/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div[";
                for (int i = 1; i <15 ; i++) {
                    String fullXPath= topicXpath+i+"]/div[1]/i";
                    if(WebDriverUtils.check(driver, By.xpath(fullXPath))){
                        judgeCondition(driver);
                        String titleName = driver.findElement(By.xpath(fullXPath)).getText();
                        WebDriverUtils.threeClick(driver, fullXPath,2);
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

        for (int i = 1; i <35 ; i++) {
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
                    judgeCondition(driver);
                    WebDriverUtils.threeClick(driver, attributClick,1);
                    Thread.sleep(1000);
                    long leftTime = leftTime(driver, sonTitle, i);
                    judgeCondition(driver,leftTime,sonTitle,i);
                    if(leftTime<=0){
                        continue;
                    }
                }else if("0".equals(attributevalue)){
                    log.info("该小课程刚开始播放 :{}",text);
                    Thread.sleep(2000);
                    judgeCondition(driver);
                    WebDriverUtils.threeClick(driver, attributClick,1);
                    Thread.sleep(1000);
                    long leftTime = leftTime(driver, sonTitle, i);
                    judgeCondition(driver,leftTime,sonTitle,i);
                    continue;
                }


            }


        }
    }


    public long leftTime(WebDriver driver,String sonTitle,int i) throws Exception {

        Thread.sleep(3000);
        judgeCondition(driver,10000, sonTitle, i);
        String endTimeClassName = "vjs-duration-display";
        WebDriverUtils.findClassName(driver, endTimeClassName, "结束时间");
        WebElement element = driver.findElement(By.className(endTimeClassName));
        String allviedoTime = element.getText();
        log.info("视频总时长:---->  {}",allviedoTime);
        String haveShowTimeXpath ="/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[1]/div/div[1]/div/span[4]";
        WebDriverUtils.findElement(driver, haveShowTimeXpath, "学习总时长");
        String haveStudyTime = driver.findElement(By.xpath(haveShowTimeXpath)).getText();
        log.info("学习总时长:---->  {}",haveStudyTime);

        String startTime = haveStudyTime.replace("秒", "");
        startTime = startTime.replace("分", ":").trim();
        log.info("学习总时长:---->  {}",startTime);
        String endTime= StringUtils.isEmpty(allviedoTime)?"30:10":allviedoTime;
        long sleepTime = TimeUtils.diffSec(startTime, endTime);
        log.info("sleepTime :-->{}",sleepTime);
        return sleepTime;
    }



    public void judgeCondition(WebDriver driver) throws Exception {

        AtomicInteger counts = new AtomicInteger(0);
        while (true) {
            //学习下一节
            if (WebDriverUtils.check(driver, By.xpath("/html/body/div[4]/div[3]/a[2]"))) {
                WebDriverUtils.threeClick(driver, "/html/body/div[4]/div[3]/a[2]",1);
                break;
            }

            //知道了 /html/body/div[4]/div[3]/a[1]
            if (WebDriverUtils.check(driver, By.xpath("/html/body/div[5]/div[3]/a[2]"))) {
                log.info("知道了");
                Thread.sleep(3000);
                WebDriverUtils.threeClick(driver, "/html/body/div[5]/div[3]/a[2]",1);

            }
            Thread.sleep(1);
            if (90 - counts.get() == 0) {
                log.info("重试{} 之后  退出");

                break;
            }

            counts.incrementAndGet();
        }
    }



    public long judgeCondition(WebDriver driver,long sleepTime,String sonTitle,int i) throws Exception {
        Actions action = new Actions(driver);
        AtomicInteger counts = new AtomicInteger(0);

        while(true){
            //学习下一节
            if (WebDriverUtils.check(driver, By.xpath("/html/body/div[4]/div[3]/a[2]"))) {
                log.info("学习下一节 取消");
                Thread.sleep(1000);
                WebDriverUtils.threeClick(driver,"/html/body/div[4]/div[3]/a[2]",1);
                WebDriverUtils.threeClick(driver,sonTitle +(i+1)+"]/span[2]",1);
                break;
            }

            //知道了 /html/body/div[4]/div[3]/a[1]
            if (WebDriverUtils.check(driver, By.xpath("/html/body/div[5]/div[3]/a[2]"))) {
                log.info("知道了");
                Thread.sleep(3000);
                WebDriverUtils.threeClick(driver,"/html/body/div[5]/div[3]/a[2]",1);

            }
            Thread.sleep(1);
            if(sleepTime-counts.get()==0){
                log.info("重试{} 之后  退出",sleepTime);

                break;
            }

            isStop(driver,action);
            counts.incrementAndGet();
        }
        return counts.get();
    }

    public void isStop(WebDriver driver,Actions actions) throws Exception {
        //是否暂停
        String isStop = "/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[2]/table/tbody/tr/td/div/div[4]/button[1]";
        if(WebDriverUtils.check(driver,By.xpath(isStop))){
            if ("播放".equals(driver.findElement(By.xpath(isStop)).getAttribute("title"))) {
                log.info("暂停点击播放");
                String center ="/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[2]/div[2]/div/div/div[2]/div/div/div[2]/table/tbody/tr/td/div/video";
                WebDriverUtils.findElement(driver,center,"主屏幕");
                if (WebDriverUtils.check(driver, By.xpath(center))) {
                    log.info("点击播放");
                    WebDriverUtils.threeClick(driver,center,2);
                }
            }
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
