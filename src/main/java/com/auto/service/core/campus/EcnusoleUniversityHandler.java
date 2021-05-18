package com.auto.service.core.campus;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.auto.common.exception.BizException;
import com.auto.entity.UserInfo;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.helper.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class EcnusoleUniversityHandler  implements CampusOnlineHandler {

    private static final String  COMMON_COURSE = "/html/body/div[5]/div[1]/div[2]/div[3]";



    //登录
    //处理逻辑
    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver,int course) throws Exception {
        log.info("华东师范作业逻辑处理start");

        AtomicInteger counts = new AtomicInteger(1);
        while(true) {
            if (WebDriverUtils.check(driver, By.xpath("/html/body/div[1]/div/div/div[2]/div/a[1]"))) {
                break;
            }
            counts.incrementAndGet();
            Thread.sleep(1);
            if (200000 == counts.get()) {
                log.info("重试6分钟之后  退出");
                 throw new BizException("重试6分钟之后  退出");
            }
        }
        //点击我的课程  /html/body/div[1]/div/div/div[2]/div/a[1]
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/a[1]")).click();
        Thread.sleep(30000);

        //先进ifram
        WebElement webElement = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/iframe"));
        driver.switchTo().frame(webElement);
        Thread.sleep(2000);

        for (int i = course; i < 10; i++) {
            //  /html/body/div[2]/div[2]/div[2]/div[1]/a  判断这个标签存在不存在
            if(WebDriverUtils.check(driver, By.xpath("/html/body/div[2]/div[2]/div["+i+"]/div[1]/a"))){
                String titleName= driver.findElement(By.xpath("/html/body/div[2]/div[2]/div["+i+"]/div[2]/h3/a")).getText();
                if("形势与政策1".equals(titleName)){
                    continue;
                }

                driver.findElement(By.xpath("/html/body/div[2]/div[2]/div["+i+"]/div[1]/a")).click();

                //开始界面处理
                courseHandle(driver);



            }

        }



    }



    public void courseHandle(WebDriver driver) throws InterruptedException {
        //进入学习进度页面  重新开了个页面  跳到另外一个页面
        WebDriverUtils.switchToWindowByTitle(driver,"学习进度页面");
        Thread.sleep(20000);

        //目前支持两层
        for (int i = 1; i <10 ; i++) {
            //判断 是否看过  若看过  跳过
            String isOrange = "/html/body/div[5]/div[1]/div[2]/div[3]/div["+i+"]/div[";

            AtomicInteger structureFirst = new AtomicInteger(1);
            while(true){
                String orangeXpath = isOrange+structureFirst+"]/h3/a/span[2]/em";
                if (WebDriverUtils.check(driver, By.xpath(orangeXpath))){
                    needHandler(orangeXpath,driver);
                }else{
                    break;
                }
                structureFirst.incrementAndGet();
            }
        }


    }



    public void  needHandler(String orangeXpath,WebDriver driver) throws InterruptedException {
        int viedos = 0;
        String organTitle = driver.findElement(By.xpath(orangeXpath)).getText();
        try {
            viedos = Integer.parseInt(organTitle);
        } catch (Exception e) {
            return;
        }
        String text = driver.findElement(By.xpath(orangeXpath.replace("2]/em","3]"))).getText();
        if ("巩固练习".equals(text)) {
//            driver.findElement(By.xpath(orangeXpath)).click();
//
//            //获取当前巩固练习坐标
//            char[] arr = orangeXpath.replace("/","").toCharArray();
//            int arr_36 = Integer.valueOf(String.valueOf(arr[36]));
//
//            //当思想品德执行到  专题六时
//            if(arr_36 == 6 && "思想品德".equals("")){
//                arr_36 = Integer.valueOf(String.valueOf(arr[36]) + String.valueOf(arr[42]));
//            }
//            excuteHoomeWork(driver,arr_36);
            return ;
        }
        driver.findElement(By.xpath(orangeXpath)).click();
        Thread.sleep(5000);
        chainHandler(viedos,driver);
    }

    public void excuteHoomeWork(WebDriver driver,int arr_36) {
        Map<Integer, List<String>> loadAnswer = new HashMap<>();
        loadAnswer(loadAnswer);
        List<String> anList = loadAnswer.get(arr_36);

        WebElement webElement0 = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/iframe"));
        driver.switchTo().frame(webElement0);
        WebElement webElement1 = driver.findElement(By.xpath("/html/body/div/div/p/div/iframe"));
        driver.switchTo().frame(webElement1);
        WebElement webElement2 = driver.findElement(By.xpath("/html/body/iframe"));
        driver.switchTo().frame(webElement2);

        for (int i = 0; i < anList.size(); i++) {
            char[] arr = anList.get(i).toCharArray();
            for (int k = 0; k < arr.length; k++) {
                StringBuilder sAnswer = new StringBuilder();
                sAnswer.append("/html/body/div/div/div/div/form/div/div");
                if(i != 0){
                    for (int j = 0; j < i; j++) {
                        sAnswer.append("/div[4]");
                    }
                }
                String title = exTile(String.valueOf(arr[k]));
                sAnswer.append("/div[2]/ul/li[").append(title).append("]").append("/label/input");



                if(WebDriverUtils.check(driver, By.xpath(String.valueOf(sAnswer)))){
                    driver.findElement(By.xpath(String.valueOf(sAnswer))).click();
                }
            }
        }
        int a = anList.size() - 2;
        StringBuilder sBottom = new StringBuilder();
        sBottom.append("//*[@id=\"ZyBottom\"]/div[1]");
        for (int j = 0; j < a; j++) {
            sBottom.append("/div[4]");
        }
        sBottom.append("/div[5]/a[2]");
        driver.findElement(By.xpath(String.valueOf(sBottom))).click();

        //确认提交
        driver.findElement(By.xpath("/html/body/div/div/div/div/form/div/div/div[4]/div[4]/div[4]/div[4]/div[6]/div/div/a[1]")).click();

        //回到课程
        driver.findElement(By.xpath(String.valueOf("/html/body/div[3]/div/div[1]/a"))).click();
    }

    public void excuteHoomeWork(WebDriver driver){
        Map<String,String> memoryAnswer = new HashMap<>();
        int i = 1;
        while (true){
            StringBuilder sTitle = new StringBuilder();
            sTitle.append("//*[@id=\"ZyBottom\"]/div[1]");
            if(i != 1){
                for (int j = 0; j < i; j++) {
                    sTitle.append("/div[4]");
                }
            }
            sTitle.append("/div[1]/div");

            String titlePath = String.valueOf(sTitle);

            if(WebDriverUtils.check(driver, By.xpath(titlePath))){

                String titleName = driver.findElement(By.xpath(titlePath)).getText();

                //处理标题
                String newTitle = exTile(titleName);

                if(memoryAnswer.containsKey(newTitle)){

                    StringBuilder sAnswer = new StringBuilder();
                    sAnswer.append("//*[@id=\"ZyBottom\"]/div[1]");

                    if(i != 1){
                        for (int j = 0; j < i; j++) {
                            sAnswer.append("/div[4]");
                        }
                    }
                    sAnswer.append("//div[2]/ul/li[").append(memoryAnswer.get(newTitle)).append("]");
                    driver.findElement(By.xpath(String.valueOf(sAnswer))).click();
                }
                i++;
            }else {
                break;
            }
        }
        int a = i - 2;
        StringBuilder sBottom = new StringBuilder();
        sBottom.append("//*[@id=\"ZyBottom\"]/div[1]");
        for (int j = 0; j < a; j++) {
            sBottom.append("/div[4]");
        }
        sBottom.append("/div[5]/a[2]");
        driver.findElement(By.xpath(String.valueOf(sBottom))).click();

        //回到课程
        driver.findElement(By.xpath(String.valueOf("/html/body/div[3]/div/div[1]/a"))).click();
    }
    private void loadAnswer(Map<Integer,List<String>> titleAnswer){
        //专题一
        List<String> answer1 = new ArrayList<>();
        answer1.add("D");
        answer1.add("A");
        answer1.add("B");
        answer1.add("ABC");
        answer1.add("ABC");
        answer1.add("ABCD");
        titleAnswer.put(1,answer1);

        //专题二
        List<String> answer2 = new ArrayList<>();
        answer2.add("B");
        answer2.add("A");
        answer2.add("B");
        answer2.add("B");
        answer2.add("A");
        answer2.add("B");
        answer2.add("ABC");
        answer2.add("ABC");
        titleAnswer.put(2,answer2);

        //专题三
        List<String> answer3 = new ArrayList<>();
        answer3.add("B");
        answer3.add("A");
        answer3.add("D");
        answer3.add("ABC");
        titleAnswer.put(3,answer3);

        //专题四
        List<String> answer4 = new ArrayList<>();
        answer4.add("B");
        answer4.add("D");
        answer4.add("D");
        answer4.add("ABCD");
        answer4.add("ABCD");
        titleAnswer.put(4,answer4);

        //专题五
        List<String> answer5 = new ArrayList<>();
        answer5.add("B");
        answer5.add("A");
        answer5.add("A");
        answer5.add("B");
        answer5.add("√");
        answer5.add("×");
        answer5.add("√");
        answer5.add("×");
        answer5.add("×");
        titleAnswer.put(5,answer5);

        //专题六
        List<String> answer61 = new ArrayList<>();
        answer61.add("C");
        answer61.add("A");
        answer61.add("B");
        answer61.add("D");
        answer61.add("C");
        answer61.add("B");
        answer61.add("D");
        answer61.add("B");
        answer61.add("D");
        titleAnswer.put(61,answer61);

        List<String> answer62 = new ArrayList<>();
        answer62.add("A");
        answer62.add("C");
        answer62.add("D");
        answer62.add("B");
        answer62.add("D");
        answer62.add("C");
        answer62.add("B");
        answer62.add("A");
        answer62.add("D");
        answer62.add("D");
        titleAnswer.put(62,answer62);

        List<String> answer63 = new ArrayList<>();
        answer63.add("A");
        answer63.add("B");
        answer63.add("B");
        answer63.add("B");
        answer63.add("A");
        answer63.add("A");
        answer63.add("B");
        answer63.add("B");
        answer63.add("D");
        answer63.add("B");
        titleAnswer.put(63,answer63);


        List<String> answer64 = new ArrayList<>();
        answer64.add("B");
        answer64.add("B");
        answer64.add("D");
        answer64.add("B");
        answer64.add("C");
        answer64.add("C");
        answer64.add("D");
        answer64.add("A");
        answer64.add("C");
        answer64.add("D");
        titleAnswer.put(64,answer64);

    }

    private static String exTile(String title){
        title = title.replaceAll("A","1");
        title = title.replaceAll("B","2");
        title = title.replaceAll("C","3");
        title = title.replaceAll("D","4");
        title = title.replaceAll("√","1");
        title = title.replaceAll("×","2");
        return title;
    }

/*    public static void main(String[] args) {
        String str = "B";
        str = str.replaceAll("B","2");
        //exTile("B")
        System.out.println(str);
    }*/

    public static void chainHandler(int viedos,WebDriver driver)throws InterruptedException{
        //判断viedos 有多少个
        log.info("----------viedos------------"+viedos);
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
                counts.incrementAndGet();
                Thread.sleep(1);
                if(200000==counts.get()){
                    log.info("重试6分钟之后  退出");
                    driver.quit();
                }
            }



            WebElement webElement0 = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/iframe"));
            driver.switchTo().frame(webElement0);

            String descIframe = WebDriverUtils.check(driver, By.xpath(iframe))?
                    iframe:(iframe.substring(0,19)+"p/"+iframe.substring(19));
            WebElement webElement1 = driver.findElement(By.xpath(descIframe));
            driver.switchTo().frame(webElement1);
            Thread.sleep(3000);


            //点击开始播放按钮
            log.info("点击开始播放按钮");
            driver.findElement(By.className("vjs-big-play-button")).click();
            Thread.sleep(8000);


            if (WebDriverUtils.check(driver, By.className("vjs-control-text"))) {
                Thread.sleep(8000);
                for (int i = 0; i <3 ; i++) {
                    //快进  倍速
                    driver.findElement(By.xpath("/html/body/div[4]/div/div[5]/div[1]/button")).click();
                    Thread.sleep(1000);
                }


                //计算剩余时间 当总时间 减去 当前播放时间剩余时间等于0  去播放下一个视频
                Thread.sleep(TimeUtils.getDiffTime(driver,1,2));
            }else{
                //计算剩余时间 当总时间 减去 当前播放时间剩余时间等于0  去播放下一个视频
                Thread.sleep(TimeUtils.getDiffTime(driver,8,10));
            }


            driver.switchTo().defaultContent();
            Thread.sleep(5000);
        }
        driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/a")).click();
        log.info("结束返回");
        Thread.sleep(3000);
    }


    @Override
    public EnumUniversityName universityName() {
            return EnumUniversityName.ECNUSOLE_UNIVERSITY;
    }
}
