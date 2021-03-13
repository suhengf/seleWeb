package com.auto.service.business.shanghaimaritimeuniversity;

import com.auto.entity.UserInfo;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AskInfoHandler {

    public static void handler(UserInfo userInfo, WebDriver driver, int conut) throws Exception {

        try {
            singleHandler(userInfo,conut);
            driver.quit();
        } catch (Exception e) {
            log.error("账号:{}  异常{}",userInfo.getUserId(),e);
            Thread.sleep(10000);
            singleHandler(userInfo,conut);
            driver.quit();
        }finally {
            driver.quit();
        }

    }

    //处理单个学生信息
    public static void singleHandler(UserInfo userInfo,int count) throws Exception {
        WebDriver driver = new ChromeDriver();
        try {
            String url = "https://passport.zhihuishu.com/login?service=https://onlineservice.zhihuishu.com/login/gologin#signin";
            Thread.sleep(10000);

            driver.manage().window().maximize();
            driver.get(url);
            driver.findElement(By.xpath("/html/body/div[4]/div/form/div[1]/ul[1]/li[1]/input[4]")).sendKeys(userInfo.getUserId().trim());
            driver.findElement(By.xpath("/html/body/div[4]/div/form/div[1]/ul[1]/li[2]/input")).sendKeys(userInfo.getPassword().trim());
            log.info("current userId：" + userInfo.getUserId());
            Thread.sleep(6000);

            driver.findElement(By.xpath("/html/body/div[4]/div/form/div[1]/span")).click();
            Thread.sleep(6000);

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



    public void businessDeal(WebDriver driver,String allXpath){
        //逻辑处理 针对问题  自己选择一个
        driver.findElement(By.xpath(allXpath)).click();
        //问题选择完 可以 需要重新点击下






    }



    // xpath  /html/body/div[1]/div/div[7]/div/div[2]/div/div[1]/div/div/div[2]/p/span[2]
    //  class  title-tit



}
