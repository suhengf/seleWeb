package com.auto.service.business.core.campus;

import com.auto.entity.UserInfo;
import com.auto.service.business.core.CampusOnlineHandler;
import com.auto.service.business.core.EnumUniversityName;
import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

/**
 * 开放大学处理逻辑
 */
@Slf4j
@Service
public class OpenUniversityHandler implements CampusOnlineHandler {
    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver ) {
        log.info("学校:{}",EnumUniversityName.OPEN_UNIVERSITY.getDesc());
        driver.findElement(By.xpath("/html/body/div/div[3]/a[1]/p[1]")).click();

        String courseId= "/html/body/div/div[3]/div/div[2]/ul/li[";
        for (int i= 1; i<=8;i++){
            String inOnlineWork = courseId+i+"]/div/div/div[2]/p/a";
            if (WebDriverUtils.check(driver, By.xpath(inOnlineWork))){
                String finishFlag = driver.findElement(By.xpath(courseId + i + "]/div/div/div[2]/div[3]/span")).getText();
                String courseName =  driver.findElement(By.xpath(inOnlineWork)).getText();
                //学习进度：0.58学时 / 60.00学时
                String[] studySpeed = finishFlag.replace("学习进度：", "").replace("学时", "").split("/");
                String startSpeed = studySpeed[0].trim();
                String endSpeed = studySpeed[1].trim();
                if(startSpeed.equals(endSpeed)){
                    log.info("课程:{}已完成",courseName);
                    continue;
                }
                driver.findElement(By.xpath(inOnlineWork)).click();
                //切换界面
                WebDriverUtils.switchToWindowByTitle(driver,"课程详情");
                log.info("切换界面之后 开始执行逻辑处理");


                try {
                    differBus( driver);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }


    }


    /**
     * 不同业务的逻辑处理
     */
    public void differBus(WebDriver driver) throws Exception {
        driver.findElement(By.xpath("/html/body/div[2]/div[4]/ul[1]/li[2]/a")).click();
        //需要判断 是pdf 还是视频 或者是作业

        if(WebDriverUtils.check(driver, By.xpath("/html/body/div/div[3]/div/div/button/span[2]"))){
            log.info("处理视频");
            driver.findElement(By.className("vjs-big-play-button")).click();
            Thread.sleep(8000);
            Thread.sleep(TimeUtils.getDiffTime(driver));
        }else{
            log.info("其他处理 向下滑动 关闭弹框");

        }

        driver.findElement(By.xpath("/html/body/div/div[2]/ul/li[1]/a")).click();
        log.info("返回课程详情页");

    }


    @Override
    public EnumUniversityName universityName() {
        return EnumUniversityName.OPEN_UNIVERSITY;
    }


}
