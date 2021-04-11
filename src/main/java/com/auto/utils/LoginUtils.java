package com.auto.utils;

import com.auto.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.auto.utils.QRtool.elementSnapshot;
import static com.auto.utils.QRtool.recognize;

@Slf4j
public class LoginUtils {


    public static List<UserInfo> parseUserList(String chromeDriverPath,String filePath) throws Exception {
        File file = ResourceUtils.getFile(chromeDriverPath);
        System.setProperty("webdriver.chrome.driver", file.getPath());
        List<UserInfo> userInfoList = new ArrayList<>();
        FileParse.readSaveList(userInfoList,filePath);
        return userInfoList;
    }

    public static WebDriver  login(UserInfo userInfo, ChromeOptions options,String url,String studentXpath,String userInput,String userPsdInput,String verifyCodeInput,String loginBotton) throws Exception {
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);
        driver.findElement(By.xpath(studentXpath)).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath(userInput)).sendKeys(userInfo.getUserId());
        Thread.sleep(7000);
        driver.findElement(By.xpath(userPsdInput)).sendKeys(userInfo.getPassword());
        log.info("current userId：{}"  ,userInfo.getUserId());
        String verifyCode = recognize(elementSnapshot(driver,url));
        log.info("verifyCode：{}" , verifyCode);
        Thread.sleep(3000);
        driver.findElement(By.xpath(verifyCodeInput)).sendKeys(verifyCode);
        //点击确定
        driver.findElement(By.xpath(loginBotton)).click();
        Thread.sleep(8000);
        return driver;
    }



    public static WebDriver  login(UserInfo userInfo, ChromeOptions options,String url,String userInput,String userPsdInput,String loginBotton) throws Exception {
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);
        driver.findElement(By.xpath(userInput)).sendKeys(userInfo.getUserId());
        Thread.sleep(7000);
        driver.findElement(By.xpath(userPsdInput)).sendKeys(userInfo.getPassword());
        log.info("current userId：{}"  ,userInfo.getUserId());
        Thread.sleep(3000);
        //点击确定
        driver.findElement(By.xpath(loginBotton)).click();
        Thread.sleep(8000);
        return driver;
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

}
