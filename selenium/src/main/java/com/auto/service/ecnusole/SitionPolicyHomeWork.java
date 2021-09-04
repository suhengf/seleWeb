package com.auto.service.ecnusole;

import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SitionPolicyHomeWork {

    //
    public  void handleWork(WebDriver driver, String orangeXpath,String text) throws Exception {
        WebDriverUtils.findElement(driver, orangeXpath, "点击 " + text + "");
        WebDriverUtils.locate(driver, orangeXpath);
        log.info("开始点击 --> {}", text);
        WebDriverUtils.click(driver, orangeXpath);
        Thread.sleep(1000);

        WebDriverUtils.findElement(driver, "/html/body/div[3]/div/div[2]/div[2]/iframe", "进入iframe");
        WebElement webElement0 = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/iframe"));
        driver.switchTo().frame(webElement0);
        Thread.sleep(60);
        WebDriverUtils.findElement(driver, "/html/body/div/div/p/div/iframe", "进入iframe");
        WebElement webElement1 = driver.findElement(By.xpath("/html/body/div/div/p/div/iframe"));
        driver.switchTo().frame(webElement1);
        Thread.sleep(60);
        WebDriverUtils.findElement(driver, "/html/body/iframe", "进入iframe");
        WebElement webElement2 = driver.findElement(By.xpath("/html/body/iframe"));
        driver.switchTo().frame(webElement2);
        Thread.sleep(60);
        for (int i = 1; i <=10; i++) {
            StringBuilder inputXpath = new StringBuilder("/html/body/div/div/div/div/form/div/div/");
            for (int j = 1; j < i; j++) {
                inputXpath.append("div[4]/");
            }
            inputXpath.append("div[2]/div/ul/li["+loadAnswer(i)+"]/label/input");
//            WebDriverUtils.locate(driver, inputXpath.toString());
            driver.findElement(By.xpath(inputXpath+"")).click();
            Thread.sleep(50);
        }


        //点击确定
        String subButton ="/html/body/div/div/div/div/form/div/div/div[4]/div[4]/div[4]/div[4]/div[4]/div[4]/div[4]/div[4]/div[5]/a[2]";
        WebDriverUtils.locate(driver,subButton );
        WebDriverUtils.threeClick(driver, subButton,1);
        Thread.sleep(2000);
        //confirm
        String confirmButton ="/html/body/div/div/div/div/form/div/div/div[4]/div[4]/div[4]/div[4]/div[4]/div[4]/div[6]/div/div/a[1]";
        WebDriverUtils.threeClick(driver,confirmButton ,1);
        WebDriverUtils.click(driver, confirmButton);
        Thread.sleep(4000);
        //return back
        log.info("答案已提交");
        WebDriverUtils.threeClick(driver, "/html/body/div[3]/div/div[1]/a",1);
        Thread.sleep(8000);
    }


    public static int loadAnswer(int i) {
        //专题一
        Map<Integer,Integer> answer1 = new HashMap<Integer,Integer>();
        answer1.put(1,1);
        answer1.put(2,2);
        answer1.put(3,1);
        answer1.put(4,1);
        answer1.put(5,2);
        answer1.put(6,1);
        answer1.put(7,1);
        answer1.put(8,2);
        answer1.put(9,1);
        answer1.put(10,1);
       return answer1.get(i);
    }


}
