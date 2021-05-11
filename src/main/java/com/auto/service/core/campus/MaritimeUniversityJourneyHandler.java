package com.auto.service.core.campus;

import com.auto.entity.UserInfo;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class MaritimeUniversityJourneyHandler implements CampusOnlineHandler {





    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver) throws Exception {
        log.info("开放大学旅游学院start");
        //点击我的课程 //*[@id="zaixuekecheng"]/div/div/div[1]/div[2]/h3
        for (int i = 1; i < 13; i++) {
//            String title =  "/html/body/app-root/app-index/div[2]/div/div/app-page-content/div/div[2]/div[2]/div/div/div["+i+"]/div[2]/h3";
            String title ="//*[@id=\"zaixuekecheng\"]/div/div/div["+i+"]/div[2]/h3";
            if (WebDriverUtils.check(driver, By.xpath(title))) {
                String text = driver.findElement(By.xpath(title)).getText();
                if("习近平新时代中国特色社会主义思想".equals(text)){
                    driver.findElement(By.xpath(title.replace("/h3","")+"/div/div[3]/button")).click();
                    WebDriverUtils.switchToWindowByTitle(driver, "课程： "+text);
                    AtomicInteger firstStrct = new AtomicInteger(1);
                    StringBuilder sBuilder = new StringBuilder();
                    //same structure
                    sBuilder.append("/html/body/div[2]/div[4]/div[3]/div/section[1]/div[2]/div/div/ul/li/div/ul[2]/li[");
                    String Struct = sBuilder.toString() + firstStrct + "]/div/h3/img";
                    if (WebDriverUtils.check(driver, By.xpath(Struct))) {
                        Thread.sleep(3000);
                        //处理每个标题下面的视频
                        driver.findElement(By.xpath("/html/body/div[2]/div[4]/div[3]/div/section[1]/div[2]/div/div/ul/li/div/ul[2]/li[1]/div/ul[2]/li[1]/div/ul/li/div/div/div[2]/div/a")).click();
                        openList(driver);
                    }

                }

            }

        }

    }




    public static void openList(WebDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        for (int i = 0; i < 13; i++) {
            Thread.sleep(3000);
            if (WebDriverUtils.check(driver, By.xpath("/html/body/div[2]/div[3]/div[1]/div[2]/span"))) {
                driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[1]/div[2]/span")).click();
                String baseStr = " //*[@id=\"list\"]/div/a[";
                String baseStrct = baseStr + i + "]";
                if (WebDriverUtils.check(driver, By.xpath(baseStrct))) {
                    try {
                        driver.findElement(By.xpath(baseStrct)).click();
//                        //挂视频  和点击
                        handleViedos(driver);
                    } catch (Exception e) {
                        log.info("把异常吃了, 让她继续浪");
                        log.info("e" + e);
                    }
                }
            }

        }
    }


    public static void handleViedos(WebDriver driver) throws InterruptedException {
        //外层包层 循环  循环遍历下面的课程即可实现

        AtomicInteger firstStrct = new AtomicInteger(1);
        //same structure
        while(true){
            driver.findElement(By.xpath("//*[@id=\"sec_right\"]")).click();
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("/html/body/div[2]/div[3]/div[2]/div/div[2]/div[3]/ul/li[");
            String firSct = sBuilder.toString() + firstStrct + "]";
            if (WebDriverUtils.check(driver, By.xpath(firSct))) {
                //下一个视频
                driver.findElement(By.xpath(firSct)).click();
                //如果出现弹框需要 点击播放   thread 一些时间
                sleep(driver, firSct);
            } else {
                break;
            }
            firstStrct.incrementAndGet();
        }

    }


    public static void sleep(WebDriver driver, String firSct) {
        //处理每个标题下面的视频
        try {
            //如果不弹框
            if (!alertExists(driver)) {
                Thread.sleep(3000);
            } else {
                closeAlter(driver);
                if (WebDriverUtils.check(driver, By.xpath("/html/body/div[2]/div[3]/div[2]/div/div[4]/div[1]/div[2]/div/div[9]/canvas"))) {
                    driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/div/div[4]/div[1]/div[2]/div/div[9]/canvas")).click();
                    //获取时间
                    String allTime = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/div/div[4]/div[1]/div[2]/div/div[2]/div[8]")).getText();
                    //休眠
                    timeHandle(allTime, driver);
                }
                driver.findElement(By.xpath(firSct)).click();
            }

        } catch (InterruptedException e) {
            log.info(e.getMessage());
        }
    }


    public static boolean alertExists(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException ne) {
            log.info("没有检测到弹出框");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return false;
    }

    public static void timeHandle(String allTime, WebDriver driver) throws InterruptedException {
        val timeStr = allTime.replace("/", "").split("  ");
        final String s = timeStr[0];
        final String s1 = timeStr[1];
        Thread.sleep(TimeUtils.getDiffTimeKai(s, s1));

    }


    public static void closeAlter(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            alert.dismiss();
        } catch (Exception e) {
            log.info("关闭窗口");
        }

    }




    @Override
    public EnumUniversityName universityName() {
            return EnumUniversityName.ECNUSOLE_UNIVERSITY;
    }
}
