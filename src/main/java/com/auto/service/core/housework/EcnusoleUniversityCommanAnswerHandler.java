package com.auto.service.core.housework;

import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共答案处理类
 */
@Slf4j
public abstract class EcnusoleUniversityCommanAnswerHandler {


    public void answerHandler(String orangeXpath, WebDriver driver) throws Exception {
        WebDriverUtils.findElement(driver, orangeXpath, "");
        WebElement element = driver.findElement(By.xpath(orangeXpath));
        element.click();

        //获取当前巩固练习坐标
        char[] arr = orangeXpath.replace("/", "").toCharArray();
        int arr_36 = Integer.valueOf(String.valueOf(arr[36]));

        //当思想品德执行到  专题六时
        if (arr_36 == 6 && "思想品德".equals("")) {
            arr_36 = Integer.valueOf(String.valueOf(arr[36]) + String.valueOf(arr[42]));
        }



            excuteHoomeWork(driver, arr_36);


    }


    public void excuteHoomeWork(WebDriver driver, int arr_36) throws Exception {
        Actions action = new Actions(driver);
        Map<Integer, List<String>> loadAnswer = new HashMap<>();
        loadAnswer(loadAnswer);
        List<String> anList = loadAnswer.get(arr_36);

        Thread.sleep(2000);


        Thread.sleep(2000);
            WebDriverUtils.findElement(driver, "/html/body/div[3]/div/div[2]/div[2]/iframe", "进入iframe");
            WebElement webElement0 = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/iframe"));
            driver.switchTo().frame(webElement0);
            WebDriverUtils.findElement(driver, "/html/body/div/div/p/div/iframe", "进入iframe");
            WebElement webElement1 = driver.findElement(By.xpath("/html/body/div/div/p/div/iframe"));
            driver.switchTo().frame(webElement1);
            WebDriverUtils.findElement(driver, "/html/body/iframe", "进入iframe");
            WebElement webElement2 = driver.findElement(By.xpath("/html/body/iframe"));
            driver.switchTo().frame(webElement2);


        for (int i = 0; i < anList.size(); i++) {
            char[] arr = anList.get(i).toCharArray();
            for (int k = 0; k < arr.length; k++) {
                StringBuilder sAnswer = new StringBuilder();
                sAnswer.append("/html/body/div/div/div/div/form/div/div");
                if (i != 0) {
                    for (int j = 0; j < i; j++) {
                        sAnswer.append("/div[4]");
                    }
                }
                String title = exTile(String.valueOf(arr[k]));
                sAnswer.append("/div[2]/ul/li[").append(title).append("]").append("/label/input");


                if (WebDriverUtils.check(driver, By.xpath(String.valueOf(sAnswer)))) {
                    driver.findElement(By.xpath(String.valueOf(sAnswer))).click();
                }
            }
        }

        log.info("答题完成");
        int a = anList.size() - 2;
        StringBuilder sBottom = new StringBuilder();
        sBottom.append("//*[@id=\"ZyBottom\"]/div");
        for (int j = 0; j < a; j++) {
            sBottom.append("/div[4]");
        }
        sBottom.append("/div[5]/a[2]");

        Thread.sleep(5000);

        try {
            //确认提交
            WebDriverUtils.findElement(driver, sBottom.toString(), "确认提交");
            WebElement element = driver.findElement(By.xpath(String.valueOf(sBottom)));
            action.moveToElement(element).perform();
            element.click();

            Thread.sleep(6000);
        } catch (Exception e) {
            log.info("e", e);
        }

        WebDriverUtils.findClassName(driver, "bluebtn", "确认提交");
        //确认提交
        Thread.sleep(5000);
        WebElement bluebtn = driver.findElement(By.className("bluebtn"));
        action.moveToElement(bluebtn).perform();
        bluebtn.click();
        Thread.sleep(5000);

        log.info("答题结束 返回");
        driver.switchTo().defaultContent();

        //回到课程 /html/body/div[3]/div/div[1]/a  /html/body/div[3]/div/div[1]/a
        try {
            WebDriverUtils.findElement(driver, "/html/body/div[3]/div/div[1]/a", "回到课程");
            WebElement element = driver.findElement(By.xpath(("/html/body/div[3]/div/div[1]/a")));
            action.moveToElement(element).perform();
            element.click();
        } catch (Exception e) {
            log.info("e", e);
        }


        Thread.sleep(5000);


    }


    public abstract void loadAnswer(Map<Integer, List<String>> titleAnswer);


    private static String exTile(String title) {
        title = title.replaceAll("A", "1");
        title = title.replaceAll("B", "2");
        title = title.replaceAll("C", "3");
        title = title.replaceAll("D", "4");
        title = title.replaceAll("true", "1");
        title = title.replaceAll("false", "2");
        return title;
    }

    public abstract CourseNameEnum courseName();

}



