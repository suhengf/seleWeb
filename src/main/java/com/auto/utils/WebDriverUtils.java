package com.auto.utils;

import com.auto.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.DailyRollingFileAppender;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
@Slf4j
public class WebDriverUtils {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(WebDriverUtils.class);

    public static Boolean check(WebDriver driver, By seletor) {
        try {
            driver.findElement(seletor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean switchToWindowByTitle(WebDriver driver,String windowTitle){
        boolean flag = false;
        try {
            String currentHandle = driver.getWindowHandle();
            Set<String> handles = driver.getWindowHandles();
            for (String s : handles) {
                if (s.equals(currentHandle)) {
                    continue;
                } else {
                    driver.switchTo().window(s);
                    if (driver.getTitle().contains(windowTitle)) {
                        flag = true;
                        System.out.println("Switch to window: "
                                + windowTitle + " successfully!");
                        break;
                    } else {
                        continue;
                    }
                }
            }
        } catch (NoSuchWindowException e) {
            logger.error("Window: " + windowTitle  + " cound not found!", e.fillInStackTrace());
            flag = false;
        }
        return flag;
    }


    public static void closeAlert(WebDriver driver){
        Boolean check = WebDriverUtils.check(driver, By.xpath("/html/body/div[11]/div/a"));
        if (check) {
            driver.findElement(By.xpath("/html/body/div[11]/div/a")).click();
        }
    }

    public static void findElement(WebDriver driver,String xpath,String xPathName) throws Exception {
        AtomicInteger counts = new AtomicInteger(1);
        while(true) {
            if (WebDriverUtils.check(driver, By.xpath(xpath))) {
                log.info("找到标签 : {}",xPathName);
                break;
            }
            counts.incrementAndGet();
            Thread.sleep(1);
            if (10000 == counts.get()) {
                log.info("重试9秒之后  退出");
                throw new BizException("重试90秒之后  退出");
            }
        }
    }

    public static void findClassName(WebDriver driver,String xpath,String xPathName) throws Exception {
        AtomicInteger counts = new AtomicInteger(1);
        while(true) {
            if (WebDriverUtils.check(driver, By.className(xpath))) {
                log.info("找到标签 : {}",xPathName);
                break;
            }
            counts.incrementAndGet();
            Thread.sleep(1);
            if (90000 == counts.get()) {
                log.info("重试90秒之后  退出");
                throw new BizException("重试90秒之后  退出");
            }
        }
    }

    /**
     * 点击重试
     * @param driver
     * @param xpath
     * @param count
     * @throws Exception
     */
    public static void threeClick(WebDriver driver,String xpath,int count) throws Exception {
        for (int i = 0; i < count; i++) {
            try {
                driver.findElement(By.xpath(xpath)).click();
                 return;
            } catch (Exception e) {
                Thread.sleep(1000);
                if(i==1){
                    log.error("错误",e);
                }
            }
        }
    }


    /**
     * 点击之前判断 元素是否存在
     * @param driver
     * @param xpath
     */
    public static void click(WebDriver driver,String xpath) throws InterruptedException {
        Thread.sleep(3000);
        try {
            for (int i = 0; i < 10; i++) {
                log.info("点击进入");
                driver.findElement(By.xpath(xpath)).click();
                driver.navigate().refresh();
                Thread.sleep(6000);
            }
        } catch (Exception e) {
            return;
        }

    }


    /**
     * 鼠标定位事件
     * @param driver
     * @param attributClick
     * @throws InterruptedException
     */
    public static void locate(WebDriver driver, String attributClick) throws InterruptedException {
            Thread.sleep(3000);
            Actions act = new Actions(driver);
            act.moveToElement(driver.findElement(By.xpath(attributClick)));
            act.perform();
    }


    public static void locateClassName(WebDriver driver, String attributClick) throws InterruptedException {
        Thread.sleep(3000);
        Actions act = new Actions(driver);
        act.moveToElement(driver.findElement(By.className(attributClick)));
        act.perform();
    }



}
