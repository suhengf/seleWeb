package com.auto.utils;

import com.auto.entity.User;
import com.auto.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.util.ResourceUtils;

import java.io.File;
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
        Thread.sleep(5000);
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

    /**
     * 开放大学旅游学院
     * @param userInfo
     * @param options
     * @param url
     * @param studentXpath
     * @param userInput
     * @param userPsdInput
     * @param loginBotton
     * @return
     * @throws Exception
     */
    public static WebDriver  login(UserInfo userInfo, ChromeOptions options,String url,String studentXpath,String userInput,String userPsdInput,String loginBotton) throws Exception {
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);
        driver.findElement(By.xpath(studentXpath)).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath(userInput)).sendKeys(userInfo.getUserId());
        Thread.sleep(7000);
        driver.findElement(By.xpath(userPsdInput)).sendKeys(userInfo.getPassword());
        log.info("current userId：{}"  ,userInfo.getUserId());
        //点击确定
        driver.findElement(By.xpath(loginBotton)).click();
        Thread.sleep(8000);
        return driver;
    }


    /**
     * 华东师范大学登录
     * @param userInfo
     * @param options
     * @param url
     * @param studentXpath
     * @param userInput
     * @param userPsdInput
     * @param verifyCodeInput
     * @param loginBotton
     * @return
     * @throws Exception
     */
    public  static WebDriver hsdLogin(UserInfo userInfo, ChromeOptions options, String url, String studentXpath, String zhanghaodenglu, String userInput, String userPsdInput, String verifyCodeInput, String loginBotton) throws Exception {
        WebDriver driver = new ChromeDriver(options);
        Actions action = new Actions(driver);
        driver.manage().window().maximize();
        driver.get(url);
        WebDriverUtils.findElement(driver, studentXpath, "点击学生地址");
        Thread.sleep(4000);
        driver.findElement(By.xpath(studentXpath)).click();
        Thread.sleep(4000);
        WebDriverUtils.findElement(driver, zhanghaodenglu, "点击账号登录");
        Thread.sleep(4000);
        driver.findElement(By.xpath(zhanghaodenglu)).click();

        //输入用户名
        WebDriverUtils.findElement(driver, userInput, "输入用户名");
        WebElement userInfoElement = driver.findElement(By.xpath(userInput));
        action.moveToElement(userInfoElement).perform();
        userInfoElement.sendKeys(userInfo.getUserId());


        WebDriverUtils.findElement(driver, userPsdInput, "输入密码");
        WebElement userPsdInputElement = driver.findElement(By.xpath(userPsdInput));
        action.moveToElement(userPsdInputElement).perform();
        userPsdInputElement.sendKeys(userInfo.getPassword());


        log.info("current userId：{}", userInfo.getUserId());
        Thread.sleep(8000);

        String verifyCode ="";
        for (int i = 0; i <4 ; i++) {

            try {
                verifyCode =recognize(elementSnapshot(driver, url));
            } catch (Exception e) {
                log.info("e",e);
            }
            if(!"".equals(verifyCode)){
                break;
            }
        }


        log.info("verifyCode：{}", verifyCode);


        WebDriverUtils.findElement(driver, verifyCodeInput, "输入验证码");
         WebElement verifyCodeInputElement = driver.findElement(By.xpath(verifyCodeInput));
        action.moveToElement(verifyCodeInputElement).perform();
        verifyCodeInputElement.sendKeys(verifyCode);
        //点击确定
        WebDriverUtils.findElement(driver, loginBotton, "点击登录");
        WebElement loginBottonElement = driver.findElement(By.xpath(loginBotton));
        action.moveToElement(loginBottonElement).perform();
        loginBottonElement.click();
        Thread.sleep(10000);
        return driver;
    }


    public static WebDriver login(UserInfo userInfo, ChromeOptions options, String url, String userInput, String userPsdInput, String loginBotton) throws Exception {
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);
        driver.findElement(By.xpath(userInput)).sendKeys(userInfo.getUserId());
        Thread.sleep(7000);
        driver.findElement(By.xpath(userPsdInput)).sendKeys(userInfo.getPassword());
        log.info("current userId：{}", userInfo.getUserId());
        Thread.sleep(3000);
        //点击确定
        driver.findElement(By.xpath(loginBotton)).click();
        Thread.sleep(8000);
        return driver;
    }


    public static WebDriver  shCoLogin(UserInfo userInfo, ChromeOptions options,String url,String studentXpath,String userInput,String userPsdInput,String loginBotton) throws Exception {
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.manage().window().maximize();
            driver.get(url);
            Thread.sleep(10000);
            driver.findElement(By.xpath(studentXpath)).click();
            Thread.sleep(10000);
            driver.findElement(By.xpath(userInput)).sendKeys(userInfo.getUserId());
            Thread.sleep(10000);
            driver.findElement(By.xpath(userPsdInput)).sendKeys(userInfo.getPassword());
            log.info("current userId：{}"  ,userInfo.getUserId());
            Thread.sleep(8000);
            driver.findElement(By.xpath(loginBotton)).click();
            Thread.sleep(8000);
        } catch (Exception e) {
            log.error("e",e);
            driver.quit();
        }
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
