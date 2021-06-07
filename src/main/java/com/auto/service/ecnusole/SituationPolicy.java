package com.auto.service.ecnusole;

import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SituationPolicy {

    public void courseHandle(WebDriver driver, String titleName) throws Exception {
        //进入学习进度页面  重新开了个页面  跳到另外一个页面
        WebDriverUtils.switchToWindowByTitle(driver, "学习进度页面");
        Thread.sleep(2000);


        //目前支持两层  /html/body/div[5]/div[1]/div[2]/div[3]/div/div[1]/h3/a/span[3]
        //             /html/body/div[5]/div[1]/div[2]/div[3]/div/div[2]/h3/a/span[3]

            //判断 是否看过  若看过  跳过
            String isOrange = "/html/body/div[5]/div[1]/div[2]/div[3]/div/div[";
            AtomicInteger structureFirst = new AtomicInteger(1);
            while (true) {
                String orangeXpath = "";

                orangeXpath = isOrange + structureFirst + "]/h3/a/span[2]/em";
                if (WebDriverUtils.check(driver, By.xpath(orangeXpath))) {
                    Thread.sleep(50);
                    needHandler(orangeXpath, driver, titleName);
                } else {
                    break;
                }
                structureFirst.incrementAndGet();
            }
        }





    public boolean needHandler(String orangeXpath, WebDriver driver, String titleName) throws Exception {
        int viedos = 0;
        Thread.sleep(5000);

        String organTitle = "";
        try {
            organTitle = driver.findElement(By.xpath(orangeXpath)).getText();
        } catch (Exception e) {
            Thread.sleep(3000);
        }
        orangeXpath = orangeXpath.replace("2]/em", "3]");
        orangeXpath = orangeXpath.replace("1]/em", "2]");
        String text = driver.findElement(By.xpath(orangeXpath)).getText();
        log.info("任务点名称：{}", text);
        log.info("xpath路径：{}", orangeXpath);
        log.info("科目: {}", titleName);
        log.info("organTitle -->{}", organTitle);
        if ("本讲巩固".equals(text)) {
            try {
                viedos = Integer.parseInt(organTitle);
            } catch (Exception e) {
                return false;
            }
            new SitionPolicyHomeWork().handleWork(driver,orangeXpath,text);
            return true;
        } else {

            try {
                viedos = Integer.parseInt(organTitle);
            } catch (Exception e) {
                return false;
            }

            log.info("courseXpath : {}", orangeXpath);
            WebDriverUtils.findElement(driver, orangeXpath, "点击 " + text + "");
            WebDriverUtils.locate(driver, orangeXpath);
            log.info("开始点击 --> {}", text);
            WebDriverUtils.click(driver, orangeXpath);
            //视频处理
            handChain(driver,viedos);
            return false;
        }


    }


    public void handChain(WebDriver driver, int viedos) throws Exception {

        //每个点之前需要判断 任务点颜色 是否执行完成  再iframe 下面 决定要不要进去下一个iframe
        //先处理一
        String firstIframe = "/html/body/div[3]/div/div[2]/div[2]/iframe";
        WebDriverUtils.findElement(driver, firstIframe, "第一个iframe");
        WebElement webElement = driver.findElement(By.xpath(firstIframe));
        driver.switchTo().frame(webElement);
        Thread.sleep(100);
        String iframe1 = "/html/body/div/div/p[2]/div/iframe";
        WebDriverUtils.findElement(driver, iframe1, "iframe1");
        WebElement webElement0 = driver.findElement(By.xpath(iframe1));
        driver.switchTo().frame(webElement0);
        Thread.sleep(100);

        String iframe3 = "/html/body/iframe";
        WebDriverUtils.findElement(driver, iframe3, "iframe3");
        WebElement webElement2 = driver.findElement(By.xpath(iframe3));
        driver.switchTo().frame(webElement2);
        Thread.sleep(100);

        String iframe2 = "/html/body/div/div/div/div/iframe";
        WebDriverUtils.findElement(driver, iframe2, "iframe2");
        WebElement webElement1 = driver.findElement(By.xpath(iframe2));
        driver.switchTo().frame(webElement1);
        Thread.sleep(100);

        WebDriverUtils.findClassName(driver, "vjs-big-play-button", "点击开始播放按钮");
        WebDriverUtils.locateClassName(driver, "vjs-big-play-button");
        driver.findElement(By.className("vjs-big-play-button")).click();
        Thread.sleep(6000);

        Thread.sleep(TimeUtils.getSitionPolicy(driver, 95, 100));
        //返回
        WebDriverUtils.click(driver, "/html/body/div[3]/div/div[1]/a");
        Thread.sleep(8000);
    }


}
