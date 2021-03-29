package com.auto.service.business.ecnusole;

import com.auto.entity.UserInfo;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

            closeAlter(driver);
            // 登记 用户账号  密码    准考证号码  大学英语 成绩     大学计算机 成绩
            handleAskInfo(driver, userInfo);

        } catch (Exception e) {
            log.error("用户:"+userInfo.getUserId()+"密码:"+userInfo.getPassword());

        }
        driver.quit();


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

    private static void handleAskInfo( WebDriver driver,UserInfo userInfo)  throws Exception{
        //基本信息查看
        driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[1]/div[3]/div/div[1]/div[2]/ul[1]/div/dl/dt/div[1]/div[1]")).click();
        closeAlter(driver);
        Thread.sleep(5000);
//                    /html/body/div[1]/div/div[2]/div[2]/div[2]/div[1]/div/ul[2]/div[1]/ul/li[2]/div/b
        String firstTitle = "/html/body/div[1]/div/div[2]/div[2]/div[2]/div[1]/div/ul[";
        for (int j = 1; j <20 ; j++) {
            for (int i = 1; i <20 ; i++) {
                String allXpath = firstTitle + j + "]/div["+i + "]/li/div/div/b";
                log.info("xpath:{}"+allXpath);
                if (WebDriverUtils.check(driver, By.xpath(allXpath))) {


                    //判断是否播放完成
                    //  /html/body/div[1]/div/div[2]/div[2]/div[2]/div[1]/div/ul[1]/div[1]/li/div/div/b[2]

                }
            }

        }

    }




}
