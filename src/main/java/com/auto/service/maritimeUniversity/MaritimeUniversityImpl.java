package com.auto.service.maritimeUniversity;


import com.auto.entity.UserInfo;
import com.auto.service.abstr.AbstractCommonUniversity;
import com.auto.service.abstr.University;
import com.auto.service.core.CampusResolver;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.LoginUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * 上海海事大学
 */
@Slf4j
@Service
public class MaritimeUniversityImpl  extends AbstractCommonUniversity implements University {
    @Autowired
    private CampusResolver campusResolver;

    @Override
    public String getFilePath() {
        return "src\\main\\files\\haishi\\20210124_0.txt";
    }

    @Override
    public EnumUniversityName getUniversityName() {
        return EnumUniversityName.MARITIME_UNIVERSITY;
    }

    @Override
    public void singleHandler(UserInfo userInfo, ChromeOptions options) throws Exception {
        String url = "https://passport.zhihuishu.com/login?service=https://onlineservice.zhihuishu.com/login/gologin#signin";
        WebDriver driver= LoginUtils.login(userInfo,options,url,"/html/body/div[4]/div/form/div[1]/ul[1]/li[1]/input[4]",
                "/html/body/div[4]/div/form/div[1]/ul[1]/li[2]/input","/html/body/div[4]/div/form/div[1]/span");

        closeAlter(driver);
        log.info("用户{}登录成功,开始逻辑处理 start", userInfo.getUserId());
        campusResolver.getExecutor(EnumUniversityName.MARITIME_UNIVERSITY.getCode()).onlineProcess(userInfo,driver);
        log.info("用户{}登录成功,开始逻辑处理 end", userInfo.getUserId());
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
        Thread.sleep(1000);
        closeAlter(driver);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[6]/div/div[3]/span/button")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[7]/div[2]/div[1]/i")).click();
        Thread.sleep(1000);

        String firstTitle = "//*[@id=\"app\"]/div/div[2]/div[2]/div[2]/div[1]/div/ul[";
        for (int j = 1; j <20 ; j++) {
            for (int i = 1; i <20 ; i++) {
                String allXpath;
                String loadFinish; //加载完成xPath
                if(j == 1){
                    allXpath = firstTitle + j + "]/div["+i + "]/li";
                    loadFinish = firstTitle  + j + "]/div[" +i + "]/li/div/div/b[2]";
                }else{
                    allXpath = firstTitle + j + "]/div["+i + "]/ul/li";
                    loadFinish = firstTitle  + j + "]/div[" +i + "]/ul/li/div/div/b[2]";
                }

                //判断是否加载完成
                if(WebDriverUtils.check(driver, By.xpath(loadFinish))){
                    continue;
                }
                //*[@id="vjs_container"]/div[10]
   /*             JavascriptExecutor js = (JavascriptExecutor) driver;//将driver转换为js
                js.executeScript("document.getElementsByClassName('controlsBar')[0].style.display='block'");*/
                //播放视频
                log.info("xpath:{}"+allXpath);
                if (WebDriverUtils.check(driver, By.xpath(allXpath))) {
                    driver.findElement(By.xpath(allXpath + "/div/div/b[1]")).click();
                    while(true){
                        //判断是否播放完成
                        /*String currentTime = driver.findElement(By.xpath("//*[@id=\"vjs_container\"]/div[10]/div[4]/span[1]")).getText();
                        String finishTime = driver.findElement(By.xpath("//*[@id=\"vjs_container\"]/div[10]/div[4]/span[2]")).getText();
                        log.info("当前播放时间：{}，播放结束时间：{}",currentTime,finishTime);
                        if(StringUtils.pathEquals(currentTime,finishTime)){
                            break;
                        }*/


                        if(WebDriverUtils.check(driver, By.xpath("//*[@id=\"app\"]/div/div[7]/div/div[3]"))){
                            driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[7]/div/div[2]/div/div[1]/div/div/div[2]/ul/li[1]")).click();
                            String isTrue = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[7]/div/div[2]/div/div[1]/div/div/div[2]/p/span[1]/span")).getText();
                            if(StringUtils.pathEquals("错误",isTrue.trim())){
                                driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[7]/div/div[2]/div/div[1]/div/div/div[2]/ul/li[2]")).click();
                            }
                            //*[@id="vjs_container"]/div[10]
                            JavascriptExecutor js = (JavascriptExecutor) driver;   //将driver转换为js
                            js.executeScript("document.getElementById('vjs_container').style.display='block'");

                            driver.findElement(By.xpath("//*[@id=\"playButton\"]")).click();
                            log.info("关闭弹框");
                        }//*[@id="app"]/div/div[2]/div[2]/div[2]/div[1]/div/ul[1]/div[1]/li/div/div/b[2]
                        //driver.findElement(By.xpath(allXpath + "/div/div/b[1]")).click();
                        //判断是否加载完成
                        if(WebDriverUtils.check(driver, By.xpath(loadFinish))){
                            break;
                        }
                        Thread.sleep(5000);
                    }
                }
            }

        }

    }




    public void businessDeal(WebDriver driver,String allXpath){
        //逻辑处理 针对问题  自己选择一个
        driver.findElement(By.xpath(allXpath)).click();
        //问题选择完 可以 需要重新点击下






    }

}
