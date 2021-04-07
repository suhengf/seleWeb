package com.auto.service.business.ecnusole;

import com.auto.entity.UserInfo;
import com.auto.utils.AlterUtils;
import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

            driver.findElement(By.xpath("/html/body/div[7]/div/div[2]/p[2]/a")).click();

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
        Thread.sleep(3000);
        WebDriverUtils.switchToWindowByTitle(driver,"学习进度页面");
        String xpath = "/html/body/div[5]/div[1]/div[2]/div[3]/div[1]/div[";
        AtomicInteger structureFst = new AtomicInteger(1);
        while(true){
            String isfinished = xpath+structureFst;
            log.info("isfinished{}",isfinished);
            if (WebDriverUtils.check(driver, By.xpath(isfinished+"]/h3/a"))){
                //需要判断内部  /h3/a/span[2]/em
                handleViedo(driver,isfinished);
            }else{
                break;
            }

            structureFst.incrementAndGet();
        }

    }


    /**
     * 核心业务逻辑处理
     */
    public  void  handleViedo(WebDriver driver, String secStruct) throws InterruptedException {
        Thread.sleep(1000);
        //关闭弹框
        WebDriverUtils.closeAlert(driver);
        String isOrange = driver.findElement(By.xpath(secStruct + "]/h3/a/span[2]/em")).getText();

        int viedos = 0;
        try {
            viedos = Integer.parseInt(isOrange);
        } catch (NumberFormatException e) {
            return;
        }


        String videoTitle = secStruct + "]/h3/a";
        //点击进去  切换frame
        driver.findElement(By.xpath(videoTitle)).click();
        Thread.sleep(5000);


        chainHandler(viedos,driver);
    }



    public  void switchFrame(String iframe, WebDriver driver){
        WebElement webElement0 = driver.findElement(By.xpath(iframe));
        driver.switchTo().frame(webElement0);

    }


    public  void chainHandler(int viedos,WebDriver driver)throws InterruptedException{
        log.info("----------viedos------------"+viedos);
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
                log.info("重试6分钟之后  退出");
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
