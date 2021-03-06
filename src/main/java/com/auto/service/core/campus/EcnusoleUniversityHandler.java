package com.auto.service.core.campus;

import com.auto.common.exception.BizException;
import com.auto.entity.UserInfo;
import com.auto.service.abstr.UniversityResolver;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.service.ecnusole.EcnusoleEnglish;
import com.auto.service.ecnusole.SituationPolicy;
import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class EcnusoleUniversityHandler  implements CampusOnlineHandler {

    private static final String  COMMON_COURSE = "/html/body/div[5]/div[1]/div[2]/div[3]";

    private static  final String SPRCIAL_IFREAEM ="/html/body/div/div/p/span[2]/div/iframe";

    private static final String IFRAME5 ="/html/body/div/div/p[5]/span/div/iframe";

    private static final String IFRAME2 ="/html/body/div/div/p[2]/span/div/iframe";

    private static final String IFRAME ="/html/body/div/div/p/div/iframe";

    private static final String IFRAME1 ="/html/body/div/div/p[1]/span/div/iframe";

    private static final String IFRAME3 ="/html/body/div/div/p[3]/span/div/iframe";

    private static final String IFRAME4 ="/html/body/div/div/p[4]/span/div/iframe";

    private static final String IFRAME6 ="/html/body/div/div/p[6]/span/div/iframe";

    private static final String IFRAME7 ="/html/body/div/div/p[2]/span/span/div/iframe";

    private static final String IFRAME_STRING ="/html/body/div/div/p[1]/strong/div/iframe";
    private static final String IFRAME_STRING1 ="/html/body/div/div/p[1]/div/iframe";

    private static final String IFRAME8 ="/html/body/div/div/p[9]/div/iframe";

    private static final String IFRAME9 ="/html/body/div/div/p[9]/div/iframe";

    private static final String IFRAME15 ="/html/body/div/div/p[15]/div/iframe";

    private static final String IFRAME12 ="/html/body/div/div/p[12]/div/iframe";

    private static final String IFRAME10 ="/html/body/div/div/p[10]/div/iframe";

    private static final String IFRAME11 ="/html/body/div/div/p[11]/div/iframe";

    private static final String IFRAME13 ="/html/body/div/div/p[13]/div/iframe";

    private static final String IFRAME14 ="/html/body/div/div/p[14]/div/iframe";

    private static final String IFRAME20 ="/html/body/div/div/p[20]/div/iframe";

    private static final String IFRAME16 ="/html/body/div/div/p[16]/div/iframe";

    private static final String IFRAME17 ="/html/body/div/div/p[17]/div/iframe";

    private static final String IFRAME18 ="/html/body/div/div/p[18]/div/iframe";

    private static final String IFRAME19 ="/html/body/div/div/p[19]/div/iframe";

    @Autowired
    private UniversityResolver universityResolver;

    //登录
    //处理逻辑
    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver,int course) throws Exception {
        log.info("华东师范作业逻辑处理start");

        //点击我的课程
        String myCourse = "/html/body/div[1]/div/div/div[2]/div/a[1]";
        WebDriverUtils.findElement(driver,myCourse,"点击我的课程");
        driver.findElement(By.xpath(myCourse)).click();
        Thread.sleep(5000);

        //先进ifram
        String myIframe = "/html/body/div[1]/div[2]/div[2]/div/iframe";
        WebDriverUtils.findElement(driver,myIframe,"进入iframe");
        WebElement webElement = driver.findElement(By.xpath(myIframe));
        driver.switchTo().frame(webElement);
        Thread.sleep(2000);

        for (int i = course; i < 10; i++) {
            //进入学习
            String courseName = "/html/body/div[3]/div[2]/div["+i+"]/div[1]/a";
            //课程名称
            String courseTitleName ="/html/body/div[3]/div[2]/div["+i+"]/div[2]/h3/a";
            if(WebDriverUtils.check(driver, By.xpath(courseName))){
                String titleName= driver.findElement(By.xpath(courseTitleName)).getText();
                if("形势与政策1".equals(titleName)){
                                        log.info("titleName--->{}",titleName);
                    driver.findElement(By.xpath(courseName)).click();
                   new SituationPolicy().courseHandle(driver,titleName);
                }

//                //开始界面处理
//                if(titleName.contains("公共英语")){
//                    log.info("titleName--->{}",titleName);
//                    driver.findElement(By.xpath(courseName)).click();
//                    courseHandle(driver,titleName);
//                }
            }

        }


    }


    public void courseHandle(WebDriver driver,String titleName) throws Exception {
        //进入学习进度页面  重新开了个页面  跳到另外一个页面
        WebDriverUtils.switchToWindowByTitle(driver,"学习进度页面");
        Thread.sleep(2000);


        //目前支持两层
        for (int i = 1; i <15 ; i++) {
            //判断 是否看过  若看过  跳过
            String isOrange = "/html/body/div[5]/div[1]/div[2]/div[3]/div["+i+"]/div[";
            AtomicInteger structureFirst = new AtomicInteger(1);
            while(true) {
                String orangeXpath = "";

//                if(i == 6 && "思想道德与法治".equals(titleName)){
//                    //orangeXpath = isOrange+structureFirst+"]/h3/a/span[1]/em";
//
//                    for (int j = 1; j <= 4; j++) {
//                        AtomicInteger mks = new AtomicInteger(1);
//                        while(true){
//                            orangeXpath = isOrange+j+"]/div/h3["+mks+"]/a/span[1]/em";
//                            if (WebDriverUtils.check(driver, By.xpath(orangeXpath))){
//                                //判断是否已经播放
//                                String isPlay = driver.findElement(By.xpath(orangeXpath)).getText();
//                                Thread.sleep(50);
//                                if(!"openlock".equals(isPlay) && !"".equals(isPlay)){
//                                    if(needHandler(orangeXpath,driver,titleName)){
//                                        break;
//                                    }
//                                }
//                            }else{
//                                break;
//                            }
//                            mks.incrementAndGet();
//                        }
//                    }
//                    break;
//                }

                orangeXpath = isOrange + structureFirst + "]/h3/a/span[2]/em";
                if (WebDriverUtils.check(driver, By.xpath(orangeXpath))) {
                    for (int j = 0; j < 10; j++) {
                        String  sonXpath= isOrange + structureFirst + "]/div/h3["+j+"]/a/span[1]/em";
                        if(WebDriverUtils.check(driver, By.xpath(sonXpath))){
                            log.info("sonXpath 存在");
                            isPlayed(driver,sonXpath,titleName,i);
                        }
                    }

                    //判断是否已经播放
                    Thread.sleep(50);
                    if ( isPlayed(driver,orangeXpath,titleName,i)) {
                        log.info("orangeXpath 存在");
                        break;
                    }
                    
                } else {
                    break;
                }

                structureFirst.incrementAndGet();
            }
        }


    }


    public boolean  needHandler(String orangeXpath,WebDriver driver,String titleName,String houseWork) throws Exception {
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
        if ("巩固练习".equals(text) || text.contains("巩固练习")) {

            try {
                universityResolver.getEcnusoleUniversityAnswerHandler(titleName).answerHandler(orangeXpath, driver,houseWork);
            } catch (Exception e) {
                log.error("作业处理异常", e);
                throw new BizException("作业处理异常");
            }

            return true;
        }else{

//            try {
//                viedos = Integer.parseInt(organTitle);
//            } catch (Exception e) {
//                return false;
//            }
//
//            if(viedos==1&&(text.contains("对话")||text.contains("练习"))){
//                log.info("跳过第一个语音");
//                return false;
//            }
//
//            log.info("courseXpath : {}", orangeXpath);
//            WebDriverUtils.findElement(driver, orangeXpath, "点击 " + text + "");
//            WebDriverUtils.locate(driver, orangeXpath);
//            log.info("开始点击 --> {}", text);
//            WebDriverUtils.click(driver, orangeXpath);
////            chainHandler(viedos,driver);
//            englishChainHandler(viedos, driver);

            return false;
        }


    }




    public static void englishChainHandler(int viedos, WebDriver driver) throws Exception {
        //判断viedos 有多少个
        log.info("----------viedos个数{}-----------", viedos);
        List<String> listFrames = new ArrayList<>();
        switch (viedos) {
            case 1:
                listFrames.add("/html/body/div/div/p/div/iframe");
                break;
            case 2:
                listFrames.add("/html/body/div/div/p[2]/div/iframe");
                listFrames.add("/html/body/div/div/p[10]/div/iframe");
                break;
            case 3:
                listFrames.add("/html/body/div/div/p[2]/div/iframe");
                listFrames.add("/html/body/div/div/p[10]/div/iframe");
                listFrames.add("/html/body/div/div/p[13]/div/iframe");
                break;
        }
        new EcnusoleEnglish().handChain(driver,viedos,listFrames);

    }


    public static void chainHandler(int viedos, WebDriver driver) throws Exception {
        //判断viedos 有多少个
        log.info("----------viedos------------" + viedos);
        List<String> listFrames = new ArrayList<>();
        switch (viedos) {
            case 1:
                listFrames.add("/html/body/div/div/p/div/iframe");
                break;
            case 2:
                listFrames.add("/html/body/div/div/p[1]/div/iframe");
                listFrames.add("/html/body/div/div/p[3]/div/iframe");
                break;
            case 3:
                listFrames.add("/html/body/div/div/div[1]/iframe");
                listFrames.add("/html/body/div/div/div[2]/iframe");
                listFrames.add("/html/body/div/div/div[3]/iframe");
                break;
            case 4:
                listFrames.add("/html/body/div/div/p[2]/div/iframe");
                listFrames.add("/html/body/div/div/p[4]/span/span/div/iframe");
                listFrames.add("/html/body/div/div/p[6]/div/iframe");
                listFrames.add("/html/body/div/div/p[8]/span/span/span/span/span/div/iframe");
                break;
        }

        log.info("-----遍历iframe------------");
        for (String iframe: listFrames) {
            AtomicInteger counts = new AtomicInteger(1);
            while(true){
                if (WebDriverUtils.check(driver, By.xpath("/html/body/div[3]/div/div[2]/div[2]/iframe"))) {
                    break;
                }
                counts.incrementAndGet();
                Thread.sleep(1);
                if(60000==counts.get()){
                    log.info("重试60秒之后  退出");
                    driver.quit();
                }
            }


            log.info("-----进入iframe------------");
            WebElement webElement0 = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/iframe"));
            driver.switchTo().frame(webElement0);

            String descIframe = "";
            if(WebDriverUtils.check(driver, By.xpath(iframe))){
                descIframe= iframe;
            }else if(WebDriverUtils.check(driver, By.xpath(SPRCIAL_IFREAEM))){
                descIframe= SPRCIAL_IFREAEM;
            }else if(WebDriverUtils.check(driver, By.xpath(IFRAME))){
                descIframe= IFRAME;
            }else if(WebDriverUtils.check(driver, By.xpath(IFRAME1))){
                descIframe= IFRAME1;
            }else if(WebDriverUtils.check(driver, By.xpath(IFRAME2))){
                descIframe= IFRAME2;
            }else if(WebDriverUtils.check(driver, By.xpath(IFRAME3))){
                descIframe= IFRAME3;
            }else if (WebDriverUtils.check(driver, By.xpath(IFRAME4))) {
                descIframe = IFRAME4;
            } else if (WebDriverUtils.check(driver, By.xpath(IFRAME5))) {
                descIframe = IFRAME5;
            } else if (WebDriverUtils.check(driver, By.xpath(IFRAME6))) {
                descIframe = IFRAME6;
            } else if (WebDriverUtils.check(driver, By.xpath(IFRAME7))) {
                descIframe = IFRAME7;
            }
            log.info("descIframe路径:{}",descIframe);

            WebElement webElement1 = driver.findElement(By.xpath(descIframe));
            driver.switchTo().frame(webElement1);
            Thread.sleep(8000);

            log.info("-----进入iframe------------结束");
            //点击开始播放按钮
            log.info("点击开始播放按钮");
            WebDriverUtils.findClassName(driver,"vjs-big-play-button","点击开始播放按钮");
            driver.findElement(By.className("vjs-big-play-button")).click();
            Thread.sleep(8000);


            if (WebDriverUtils.check(driver, By.className("vjs-control-text"))) {
                Thread.sleep(18000);
                for (int i = 0; i <3 ; i++) {
                    try {
                        //快进  倍速
                        String speedFast ="/html/body/div[4]/div/div[5]/div[1]/button";
                        WebDriverUtils.findElement(driver,speedFast,"点击快进按钮");
                        driver.findElement(By.xpath(speedFast)).click();
                    } catch (Exception e) {
                        driver.findElement(By.className("vjs-big-play-button")).click();
                        log.info("点击快进异常");
                    }
                    Thread.sleep(1000);
                }
                //计算剩余时间 当总时间 减去 当前播放时间剩余时间等于0  去播放下一个视频
                Thread.sleep(TimeUtils.getDiffTime(driver,1,2));
            }else{
                //计算剩余时间 当总时间 减去 当前播放时间剩余时间等于0  去播放下一个视频
                Thread.sleep(TimeUtils.getDiffTime(driver, 8, 10));
            }
            driver.switchTo().defaultContent();
            Thread.sleep(5000);
        }
        driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/a")).click();
        log.info("结束返回");
        Thread.sleep(10000);
    }



    public boolean isPlayed(WebDriver driver, String xPath,String titleName,int i) throws Exception {
        Thread.sleep(100);
        String houseWork = i>=10?"0":"";
        if (needHandler(xPath, driver, titleName,houseWork)) {
            return true;
        }
        return false;
    }

//    public boolean isPlayed(WebDriver driver, String xPath,String titleName) throws Exception {
//        Thread.sleep(100);
//        String isPlay = driver.findElement(By.xpath(xPath)).getText();
//        if (!"openlock".equals(isPlay) && !"".equals(isPlay)&& needHandler(xPath, driver, titleName)) {
//            return true;
//        }
//        return false;
//    }


    @Override
    public EnumUniversityName universityName() {
        return EnumUniversityName.ECNUSOLE_UNIVERSITY;
    }
}
