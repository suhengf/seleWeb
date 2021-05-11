package com.auto.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.helper.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;

@Slf4j
public class TimeUtils {


    public static long getDiffTime(WebDriver driver,int divSon,int divMonther) throws InterruptedException {
        long diffSec = 0;
        Thread.sleep(3000);
        String startTime = "";
        String endTime = "";
        while (true) {
            startTime = driver.findElement(By.className("vjs-current-time-display")).getText();
            endTime= driver.findElement(By.className("vjs-duration-display")).getText();
            if (!StringUtil.isBlank(startTime)&&!StringUtil.isBlank(endTime)) {
                break;
            }
            Thread.sleep(1000);
        }
        log.info("已播放 {}", startTime);
        log.info("总时长 {} ", endTime);
        log.info("还差多少毫秒播放结束: {}", diffSec(startTime, endTime));
        return diffSec = "0:00".equals(endTime)?0:((diffSec(startTime, endTime))* divSon )/divMonther;

    }

    public static int countSec(String str) {
        String[] my = str.split(":");
        int time = 0;
        for (int i = 0; i < my.length - 1; i++) {
            time = (Integer.parseInt(my[i]) + time) * 60;
        }
        time = time + Integer.parseInt(my[my.length - 1]);
        return time * 1000;
    }


    //计算差异时间
    public static long diffSec(String startTime, String endTime) {
        return countSec(endTime) - countSec(startTime);
    }



    public static long getDiffTimeKai(String startTime,String endTime ) {
        long diffSec = 0;
        log.info("已播放 {}", startTime);
        log.info("总时长 {} ", endTime);
        log.info("还差多少毫秒播放结束: {}", diffSec(startTime, endTime));
        return diffSec = "0:00".equals(endTime)?0:((diffSec(startTime, endTime)) );

    }


}
