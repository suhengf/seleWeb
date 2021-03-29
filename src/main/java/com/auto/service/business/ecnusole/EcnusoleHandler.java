package com.auto.service.business.ecnusole;

import com.auto.entity.UserInfo;
import com.auto.utils.AlterUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import static com.auto.utils.QRtool.elementSnapshot;
import static com.auto.utils.QRtool.recognize;

@Slf4j
@Service
public class EcnusoleHandler {



    //处理单个学生信息
    public  void singleHandler(UserInfo userInfo) throws Exception {
        WebDriver driver = new ChromeDriver();
        try {
            String url = "http://wljy.sole.ecnu.edu.cn/login";
            driver.manage().window().maximize();
            driver.get(url);
            Thread.sleep(6000);
            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div[1]/a[2]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[1]/input")).sendKeys(userInfo.getUserId().trim());
            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[2]/input")).sendKeys(userInfo.getPassword().trim());
            log.info("current userId：" + userInfo.getUserId());
            Thread.sleep(6000);
            String verifyCode = recognize(elementSnapshot(driver,url));
            log.info("验证码验证   ："+verifyCode);
            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[3]/input")).sendKeys(verifyCode);

            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div[2]/div[2]/form/div[4]/button")).click();
            Thread.sleep(2000);

            AlterUtils.closeAlter(driver);
            // 逻辑处理
            businessHandler(driver, userInfo);

        } catch (Exception e) {
           log.error("异常",e);

        }
        driver.quit();


    }



    private  void businessHandler( WebDriver driver,UserInfo userInfo)  throws Exception{
        //点击我的课程
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/a[1]")).click();
        Thread.sleep(3000);
        switchFrame("/html/body/div[1]/div[2]/div[2]/div/iframe",driver);
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/div[1]/a")).click();
        log.info("jhahhah");


    }


    public  void switchFrame(String iframe, WebDriver driver){
        WebElement webElement0 = driver.findElement(By.xpath(iframe));
        driver.switchTo().frame(webElement0);

    }




}
