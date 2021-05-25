package com.auto.service.core.campus;

import com.auto.entity.UserInfo;
import com.auto.service.abstr.ThreadPoolParam;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Component
public class ShUniversityHandler  implements CampusOnlineHandler {
    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver, int course) throws Exception {
        log.info("上海大学逻辑处理");
        Thread.sleep(10000);
        //课程信息 /html/body/div[2]/div[3]/table/tbody/tr/td[1]/div[2]/div/div[2]/div[2]/ul/li[4]/a
        String courseInfo ="/html/body/div[2]/div[3]/table/tbody/tr/td[1]/div[2]/div/div[2]/div[2]/ul/li[4]/a";

        WebDriverUtils.findElement(driver,courseInfo,"课程信息");
        driver.findElement(By.xpath(courseInfo)).click();

        Thread.sleep(10000);
        //网上学习
        String str = "/html/body/div[2]/div[3]/table/tbody/tr/td[2]/div/div[3]/div/table[2]/tbody/tr[";
        for (int i = 3; i < 15; i++) {
            String onlineStudy = str+i+"]/td[10]/a";
            if (WebDriverUtils.check(driver, By.xpath(onlineStudy))) {
                String text = str+i+"]/td[2]";
                String courseTitle = driver.findElement(By.xpath(text)).getText();
                if(courseTitle.contains("形势与政策实践(2)*")){
                    driver.findElement(By.xpath(onlineStudy)).click();
                    Thread.sleep(10000);
                    //视频逻辑处理
                    courseHandle(driver,courseTitle);
                }

            }

        }

        log.info("上海大学逻辑处理结束");

    }


    public void courseHandle(WebDriver driver,String courseTitle) throws InterruptedException {
        //进入学习进度页面  重新开了个页面 现代物流管理* - 首页
        WebDriverUtils.switchToWindowByTitle(driver, courseTitle + " - 首页");
        Thread.sleep(20000);
        //开始学习

        try {
            String startStudy = "/html/body/div[1]/div/div[1]/div/div[2]/div[1]/div/div/div/div/table/tbody/tr/td[1]/div[1]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[3]";
            if (WebDriverUtils.check(driver, By.xpath(startStudy))) {
                driver.findElement(By.xpath(startStudy)).click();
                //视频处理逻辑
                 long time = TimeUtils.getDiffTime(driver, 100, 97);
                 log.info("该视频时长:{}",time);
                 boolean isNext = false;
                AtomicInteger counts = new AtomicInteger(0);
                while(true){
                    //判断结束标志
                    if (WebDriverUtils.check(driver, By.xpath("/html/body/div[5]/div[3]/a[1]"))) {
                        log.info("播放下一个");
                        driver.findElement(By.xpath("/html/body/div[5]/div[3]/a[1]")).click();
                    }
                    //继续播放
                    if (WebDriverUtils.check(driver, By.xpath("/html/body/div[4]/div[3]/a[1]"))) {
                        log.info("继续播放");
                        driver.findElement(By.xpath("/html/body/div[4]/div[3]/a[1]")).click();
                    }
                    Thread.sleep(1);
                    if(time==counts.get()){
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
        } catch (Exception e) {
            log.info("异常",e);
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
