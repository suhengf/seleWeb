package guokai;

import ecust.UserInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.LoggerFactory;


public class AskInfoHandler {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AskInfoHandler.class);

    public static void handler(UserInfo userInfo, WebDriver driver,int conut) throws Exception {

        try {
            singleHandler(userInfo);
            driver.quit();
        } catch (Exception e) {
            Thread.sleep(10000);
            singleHandler(userInfo);
            driver.quit();
        }finally {
            driver.quit();
        }

    }

    //处理单个学生信息
    public static void singleHandler(UserInfo userInfo) throws Exception {

        String url = "http://www.ouchn.cn/";
        Thread.sleep(10000);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div/header/div[2]/ul[2]/li[1]/button[1]")).click();
        Thread.sleep(5000);

        driver.findElement(By.xpath("/html/body/div/div/div/form/div/div/div[2]/div/input")).sendKeys(userInfo.getUserId());
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html/body/div/div/div/form/div/div/div[3]/div/input")).sendKeys(userInfo.getPassword());
        logger.info("current userId：" + userInfo.getUserId());
        Thread.sleep(8000);

        //点击确定
        driver.findElement(By.xpath("/html/body/div/div/div/form/div/div/div[4]/button")).click();
        Thread.sleep(800000);


        // 挂视频
//        handleAskInfo(driver, userInfo);
        driver.quit();


    }


    //国家开放大学挂视频
    private static void handleAskInfo( WebDriver driver,UserInfo userInfo)  throws Exception{
        //基本信息查看


    }




}
