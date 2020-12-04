package jiangnan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;

public class TimeUtils {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    public static int getDiffTime(WebDriver driver) throws InterruptedException {
        int diffSec =0;
        Thread.sleep(3000);
        String startTime  = driver.findElement(By.className("vjs-current-time-display")).getText();
        String endTime = driver.findElement(By.className("vjs-duration-display")).getText();
        logger.info("已播放 {}",startTime);
        logger.info("总时长 {} ",endTime);
        logger.info("还差多少毫秒播放结束: {}",diffSec(startTime, endTime));
        return	diffSec= diffSec(startTime, endTime);

    }

    //计算差异时间
    public static int diffSec(String startTime,String endTime) {
        return countSec(endTime) - countSec(startTime);
    }

    public static int countSec(String str) {
        int min = Integer.parseInt(str.substring(0,str.indexOf(":")))*60000;
        int sec = Integer.parseInt(str.substring(str.indexOf(":")+1))*1000;
        return min+sec;
    }
}
