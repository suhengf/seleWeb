package com.auto.service.business.ecust;

import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 处理视频各种情况
 */
@Slf4j
public class HandlerViedo {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BusinessHandler.class);
    private static final String ORANGEFLAG = "1";
    private static final String DEFAULT_COURSE= "/html/body/div[3]/div[2]/div[";
    private static final String SPECIAL_COURSE= "/html/body/div[2]/div[2]/div[";

    private static final String SPECIAL_IFRAME=  "/html/body/div/div/p/div/iframe";
    private static final String DEFAULT_IFRAME= "/html/body/div/div/div/iframe";



    public static void  handleViedo(WebDriver driver, String secStruct) throws InterruptedException {
        logger.info("---------secStruct-------" + secStruct);
        Thread.sleep(1000);
        //关闭弹框
        WebDriverUtils.closeAlert(driver);

        String isOrange = driver.findElement(By.xpath(secStruct + "]/h3/span[2]/em")).getText();

        int viedos = 0;
        try {
            viedos = Integer.parseInt(isOrange);
        } catch (NumberFormatException e) {
            return;
        }


        String videoTitle = secStruct + "]/h3/span[3]/a";
        //点击进去  切换frame
        driver.findElement(By.xpath(videoTitle)).click();
        Thread.sleep(5000);


        chainHandler(viedos,driver);
    }


    public static void  handleViedoThird(WebDriver driver, String thridStruct) throws InterruptedException {
        logger.info("---------thridStruct-------" + thridStruct);
        Thread.sleep(1000);
        //关闭弹框
        WebDriverUtils.closeAlert(driver);

        String isOrange = driver.findElement(By.xpath(thridStruct + "/span[1]/em")).getText();

        int viedos = 0;
        try {
            viedos = Integer.parseInt(isOrange);
        } catch (NumberFormatException e) {
            return;
        }

        String videoTitle = thridStruct + "/span[2]/a";
        //点击进去  切换frame
        driver.findElement(By.xpath(videoTitle)).click();
        Thread.sleep(5000);
        chainHandler(viedos,driver);
    }



    public static void  handleViedoForth(WebDriver driver, String forthStruct) throws InterruptedException {
        logger.info("---------forthStruct-------" + forthStruct);
        Thread.sleep(1000);

        //关闭弹框
        WebDriverUtils.closeAlert(driver);
        String isOrange = driver.findElement(By.xpath(forthStruct + "/span[1]/em")).getText();

        int viedos = 0;
        try {
            viedos = Integer.parseInt(isOrange);
        } catch (NumberFormatException e) {
            return;
        }

        String videoTitle = forthStruct + "/span[2]/a";
        driver.findElement(By.xpath(videoTitle)).click();
        Thread.sleep(5000);

        chainHandler(viedos,driver);

    }


    public static void chainHandler(int viedos,WebDriver driver)throws InterruptedException{
        //判断viedos 有多少个
        logger.info("----------viedos------------"+viedos);
        List<String> listFrames = new ArrayList<>();
        switch (viedos) {
            case 1:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                break;
            case 2:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                listFrames.add("/html/body/div/div/div[2]/iframe");
                break;
            case 3:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                listFrames.add("/html/body/div/div/div[2]/iframe");
                listFrames.add("/html/body/div/div/div[3]/iframe");
                break;
            case 4:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                listFrames.add("/html/body/div/div/div[2]/iframe");
                listFrames.add("/html/body/div/div/div[3]/iframe");
                listFrames.add("/html/body/div/div/div[4]/iframe");
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
                Thread.sleep(1000);
                counts.incrementAndGet();
            }

            if(200000==counts.get()){
                logger.info("重试6分钟之后  退出");
                driver.quit();
            }

            WebElement webElement0 = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/iframe"));
            driver.switchTo().frame(webElement0);

            String descIframe = WebDriverUtils.check(driver, By.xpath(iframe))?
                    iframe:(iframe.substring(0,19)+"p/"+iframe.substring(19));
            WebElement webElement1 = driver.findElement(By.xpath(descIframe));
            driver.switchTo().frame(webElement1);
            Thread.sleep(3000);
            //点击开始播放按钮
            driver.findElement(By.className("vjs-big-play-button")).click();
            Thread.sleep(8000);
            //计算剩余时间 当总时间 减去 当前播放时间剩余时间等于0  去播放下一个视频
            Thread.sleep(TimeUtils.getDiffTime(driver,8,10));
            driver.switchTo().defaultContent();
            Thread.sleep(5000);

        }
        driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/a")).click();
        Thread.sleep(3000);
    }





}
