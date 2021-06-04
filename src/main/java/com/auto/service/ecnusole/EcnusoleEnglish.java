package com.auto.service.ecnusole;

import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EcnusoleEnglish {
    private static final String COMMON_COURSE = "/html/body/div[5]/div[1]/div[2]/div[3]";

    private static final String SPRCIAL_IFREAEM = "/html/body/div/div/p/span[2]/div/iframe";

    private static final String IFRAME5 = "/html/body/div/div/p[5]/span/div/iframe";

    private static final String IFRAME2 = "/html/body/div/div/p[2]/span/div/iframe";

    private static final String IFRAME = "/html/body/div/div/p/div/iframe";

    private static final String IFRAME1 = "/html/body/div/div/p[1]/span/div/iframe";

    private static final String IFRAME3 = "/html/body/div/div/p[3]/span/div/iframe";

    private static final String IFRAME4 = "/html/body/div/div/p[4]/span/div/iframe";

    private static final String IFRAME6 = "/html/body/div/div/p[6]/span/div/iframe";

    private static final String IFRAME7 = "/html/body/div/div/p[2]/span/span/div/iframe";

    private static final String IFRAME_STRING = "/html/body/div/div/p[1]/strong/div/iframe";
    private static final String IFRAME_STRING1 = "/html/body/div/div/p[1]/div/iframe";

    private static final String IFRAME8 = "/html/body/div/div/p[9]/div/iframe";

    private static final String IFRAME9 = "/html/body/div/div/p[9]/div/iframe";

    private static final String IFRAME15 = "/html/body/div/div/p[15]/div/iframe";

    private static final String IFRAME12 = "/html/body/div/div/p[12]/div/iframe";

    private static final String IFRAME10 = "/html/body/div/div/p[10]/div/iframe";

    private static final String IFRAME11 = "/html/body/div/div/p[11]/div/iframe";

    private static final String IFRAME13 = "/html/body/div/div/p[13]/div/iframe";

    private static final String IFRAME14 = "/html/body/div/div/p[14]/div/iframe";

    private static final String IFRAME20 = "/html/body/div/div/p[20]/div/iframe";

    private static final String IFRAME16 = "/html/body/div/div/p[16]/div/iframe";

    private static final String IFRAME17 = "/html/body/div/div/p[17]/div/iframe";

    private static final String IFRAME18 = "/html/body/div/div/p[18]/div/iframe";

    private static final String IFRAME19 = "/html/body/div/div/p[19]/div/iframe";


    public void handChain(WebDriver driver, int viedos, List<String> iframes) throws Exception {

        //每个点之前需要判断 任务点颜色 是否执行完成  再iframe 下面 决定要不要进去下一个iframe

        //先处理一
        List<String>hasDoneList= new ArrayList<>();
        hasDoneList.add("1");
        for (int i = 0; i < iframes.size(); i++) {
            log.info("iframe 值----> {}",iframes.get(i));
            hasDoneList.stream().forEach(s -> {
                log.info("hasDoneList ----->{}",s);
            });
            if (hasDoneList.contains(iframes.get(i))) {
                continue;
            }
            String firstIframe = "/html/body/div[3]/div/div[2]/div[2]/iframe";
            WebDriverUtils.findElement(driver,firstIframe,"第一个iframe");
            WebElement webElement = driver.findElement(By.xpath(firstIframe));
            driver.switchTo().frame(webElement);

            String iframe1 = getIframe(driver, iframes.get(i),hasDoneList);
            hasDoneList.add(iframe1);
            WebElement webElement0 = driver.findElement(By.xpath(iframe1));
            driver.switchTo().frame(webElement0);
            String startButton =WebDriverUtils.check(driver, By.xpath("/html/body/div[4]/div/button"))?"/html/body/div[4]/div/button":"/html/body/div[3]/div/div[5]/button";

            WebDriverUtils.findElement(driver, startButton, "点击开始播放按钮");
            WebDriverUtils.locate(driver,startButton);
            driver.findElement(By.xpath(startButton)).click();
            Thread.sleep(TimeUtils.getDiffTime(driver, 95, 100));
            driver.switchTo().defaultContent();
            Thread.sleep(5000);
        }

       WebDriverUtils.click(driver,"/html/body/div[3]/div/div[1]/a");
        Thread.sleep(8000);
    }


    public String getIframe(WebDriver driver, String iframe,List<String>hasDoneList) {
        String descIframe = "";
        if (WebDriverUtils.check(driver, By.xpath(iframe))&&!hasDoneList.contains(iframe)) {
            descIframe = iframe;
        } else if (WebDriverUtils.check(driver, By.xpath(SPRCIAL_IFREAEM))&&!hasDoneList.contains(SPRCIAL_IFREAEM)) {
            descIframe = SPRCIAL_IFREAEM;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME))&&!hasDoneList.contains(IFRAME)) {
            descIframe = IFRAME;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME1))&&!hasDoneList.contains(IFRAME1)) {
            descIframe = IFRAME1;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME2))&&!hasDoneList.contains(IFRAME2)) {
            descIframe = IFRAME2;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME3))&&!hasDoneList.contains(IFRAME3)) {
            descIframe = IFRAME3;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME4))&&!hasDoneList.contains(IFRAME4)) {
            descIframe = IFRAME4;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME5))&&!hasDoneList.contains(IFRAME5)) {
            descIframe = IFRAME5;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME6))&&!hasDoneList.contains(IFRAME6)) {
            descIframe = IFRAME6;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME7))&&!hasDoneList.contains(IFRAME7)) {
            descIframe = IFRAME7;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME_STRING))&&!hasDoneList.contains(IFRAME_STRING)) {
            descIframe = IFRAME_STRING;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME_STRING1))&&!hasDoneList.contains(IFRAME_STRING1)) {
            descIframe = IFRAME_STRING1;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME8))&&!hasDoneList.contains(IFRAME8)) {
            descIframe = IFRAME8;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME9))&&!hasDoneList.contains(IFRAME9)) {
            descIframe = IFRAME9;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME10))&&!hasDoneList.contains(IFRAME10)) {
            descIframe = IFRAME10;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME12))&&!hasDoneList.contains(IFRAME12)) {
            descIframe = IFRAME12;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME13))&&!hasDoneList.contains(IFRAME13)) {
            descIframe = IFRAME13;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME14))&&!hasDoneList.contains(IFRAME14)) {
            descIframe = IFRAME14;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME15))&&!hasDoneList.contains(IFRAME15)) {
            descIframe = IFRAME15;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME16))&&!hasDoneList.contains(IFRAME16)) {
            descIframe = IFRAME16;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME11))&&!hasDoneList.contains(IFRAME11)) {
            descIframe = IFRAME11;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME17))&&!hasDoneList.contains(IFRAME17)) {
            descIframe = IFRAME17;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME18))&&!hasDoneList.contains(IFRAME18)) {
            descIframe = IFRAME18;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME19))&&!hasDoneList.contains(IFRAME19)) {
            descIframe = IFRAME19;
        } else if (WebDriverUtils.check(driver, By.xpath(IFRAME20))&&!hasDoneList.contains(IFRAME20)) {
            descIframe = IFRAME20;
        }

        log.info("descIframe--->{}", descIframe);
        return descIframe;
    }


}
