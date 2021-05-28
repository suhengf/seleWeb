package com.auto.service.core.campus;

import com.auto.common.exception.BizException;
import com.auto.entity.UserInfo;
import com.auto.service.abstr.ThreadPoolParam;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.service.core.campus.component.CurrentLogistics;
import com.auto.service.shUniversity.EnumCourseName;
import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import jdk.nashorn.internal.ir.CallNode;
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

                if (!courseTitle.contains(EnumCourseName.yansgheng.getDesc())) {
                    String secTitle ="/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div[2]/div[1]/i";
                    if (WebDriverUtils.check(driver, By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div[2]/div[1]/i"))) {
                        new CurrentLogistics().judgeCondition(driver);
                        WebDriverUtils.click(driver,secTitle);
                        Thread.sleep(1000);
                    }

                }else{
                    new CurrentLogistics().judgeCondition(driver);
                    String topicXpath = "/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div[1]/div[1]/i";
                    WebDriverUtils.click(driver,topicXpath);
                    Thread.sleep(3000);
                }

//
                String topicXpath = "/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div[";
                for (int i = 1; i <15 ; i++) {
                    String fullXPath= topicXpath+i+"]/div[1]/i";
                    if(WebDriverUtils.check(driver, By.xpath(fullXPath))){
                        new CurrentLogistics().judgeCondition(driver);
                        String titleName = driver.findElement(By.xpath(fullXPath)).getText();
                        WebDriverUtils.threeClick(driver, fullXPath,2);
                        log.info("打开第 {} 主题 :{}", i,titleName);
                        String sonTitle ="/html/body/div[1]/div/div[1]/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[1]/div["+i+"]/div[2]/div[";
                        new CurrentLogistics().isFinishFlag(driver,sonTitle);

                    }

                }

            }
        } catch (Exception e) {
            log.info("异常",e);
            throw new BizException("异常");
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
