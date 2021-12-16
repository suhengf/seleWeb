package com.auto.service.core.campus;

import com.auto.entity.UserInfo;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MaritimeUniversityJourneyHandler implements CampusOnlineHandler {





    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver,int course) throws Exception {
        log.info("开放大学旅游学院start");
        String task = "/html/body/app-root/app-index/div[2]/div/div/app-page-content/div/div[1]/div[2]/div/div[2]/div[";
        for (int i = 1; i < 15; i++) {
            String  courseNameXpath = task+i+"]/div[2]/h3";
            task = task+i+"]/div[2]/div/div[3]/button";
            String courseName = driver.findElement(By.xpath(courseNameXpath)).getText();
           log.info("task:{}"+task);
            driver.findElement(By.xpath(task)).click();
            Thread.sleep(3000);


            //页面跳转
            WebDriverUtils.switchToWindowByTitle(driver, courseName);
            log.info("切换 {} 界面之后 开始执行逻辑处理",courseName);
            // 点击形考任务
            driver.findElement(By.xpath("/html/body/div[2]/header/ul[1]/li[2]/a")).click();


            executeHouseWork(driver,courseName);
        }





    }

    /**
     * 处理形考任务
     * @param driver
     */
    private void executeHouseWork(WebDriver driver,String courseName) throws Exception {
        //1.形考任务 /html/body/div[2]/div[3]/div/div/div[1]/div/div[3]/a
        //2.形考任务 /html/body/div[2]/div[3]/div/div/div[2]/div/div[3]/a

        String houseWor ="/html/body/div[2]/div[3]/div/div/div[";
        for (int i = 2; i < 10; i++) {
            //进去形考
            houseWor = houseWor+i+"]/div/div[3]/a";
            Thread.sleep(4000);
            WebDriverUtils.threeClick(driver,houseWor,10);

            Thread.sleep(3000);
            //处理作业任务                /html/body/div[3]/div[2]/div/div/section/div/div/div[2]/div/form/button
            //点击上次试答    开始答题            /html/body/div[3]/div[2]/div/div/section/div/div/div[2]/div/form/button
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/section/div/div/div[2]/div/form/button")).click();
            doHomeWork(driver);

        }






    }


    private void doHomeWork(WebDriver driver) throws InterruptedException {

        for (int i = 2; i < 60; i++) {
            String problem = "/html/body/div[3]/div[2]/div/div/section[1]/div/div/form/div/div["+ i+"]/div[2]/div/div[1]/p";
            if (WebDriverUtils.check(driver, By.xpath(problem))) {
                String proChinese = driver.findElement(By.xpath(problem)).getText();
                log.info("proChinese:{}",proChinese);

            }
        }



        String nextPage = "/html/body/div[3]/div[2]/div/div/section[1]/div/div/form/div/div[4]/input";
        repeatDo(driver,nextPage);

        String finishPage  ="/html/body/div[3]/div[2]/div/div/section[1]/div/div/form/div/div[4]/input[2]";
        if (WebDriverUtils.check(driver, By.xpath(finishPage))) {
            driver.findElement(By.xpath(finishPage)).click();
        }


    }





    public void repeatDo(WebDriver driver,String nextPage)throws InterruptedException{
        //下一页
        nextPage =  WebDriverUtils.check(driver, By.xpath(nextPage))?nextPage:"/html/body/div[3]/div[2]/div/div/section[1]/div/div/form/div/div[11]/input";
        if (WebDriverUtils.check(driver, By.xpath(nextPage))) {
            String nextPageChinese = driver.findElement(By.xpath(nextPage)).getText();
            log.info("nextPageChinese:{}",nextPageChinese);
            Thread.sleep(5000);
            for (int i = 2; i < 60; i++) {

                final String text = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/section[1]/div/div/form/div/div[1]/div[2]/div/div/p")).getText();
                String problem = "/html/body/div[3]/div[2]/div/div/section[1]/div/div/form/div/div["+ i+"]/div[2]/div/div[1]/p";
                if (WebDriverUtils.check(driver, By.xpath(problem))) {

                    //逻辑抽离  整理答案
                    String proChinese = driver.findElement(By.xpath(problem)).getText();
                    log.info("proChinese:{}",proChinese);


                }
            }
        }else{
            return;
        }

    }



    //匹配答题  根据题目类型 分为 判断题 多选题  单选题
    //包含我的题目 然后 我去找他的答案
    public void  answerQue(){

    }



    @Override
    public EnumUniversityName universityName() {
            return EnumUniversityName.OPEN_UNIVERSITY_JOURNEY;
    }
}
