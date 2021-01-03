package guokai;

import ecust.UserInfo;
import ecust.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


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
        Thread.sleep(8000);


        // 挂视频
        handleAskInfo1(driver, userInfo);
        driver.quit();


    }

    private static void handleAskInfo1(WebDriver driver, UserInfo userInfo) throws InterruptedException {
        driver.findElement(By.xpath("/html/body/app-root/app-index/div[2]/div/div/app-page-content/div/div[2]/div[2]/div/div/div[4]/div[2]/div/div[3]/button")).click();
        WebDriverUtils.switchToWindowByTitle(driver, "课程： 思想道德修养与法律基础");
        AtomicInteger firstStrct = new AtomicInteger(1);
        StringBuilder sBuilder = new StringBuilder();
        //same structure
        sBuilder.append("/html/body/div[2]/div[4]/div[3]/div/section[1]/div[2]/div/div/ul/li/div/ul[2]/li[");
        String Struct = sBuilder.toString() + firstStrct + "]/div/h3/img";
        if (WebDriverUtils.check(driver, By.xpath(Struct))) {
//                 driver.findElement(By.xpath(Struct)).click();
            Thread.sleep(3000);
            //处理每个标题下面的视频
            driver.findElement(By.xpath("/html/body/div[2]/div[4]/div[3]/div/section[1]/div[2]/div/div/ul/li/div/ul[2]/li[1]/div/ul[2]/li[1]/div/ul/li/div/div/div[2]/div/a")).click();
            handleViedos( driver,  userInfo);
        }


    }


    //国家开放大学挂视频
//    private static void handleAskInfo( WebDriver driver,UserInfo userInfo)  throws Exception{
//        //基本信息查看
//        driver.findElement(By.xpath("/html/body/app-root/app-index/div[2]/div/div/app-page-content/div/div[2]/div[2]/div/div/div[4]/div[2]/div/div[3]/button")).click();
//        WebDriverUtils.switchToWindowByTitle(driver,"课程： 思想道德修养与法律基础");
//         AtomicInteger firstStrct = new AtomicInteger(1);
//        StringBuilder sBuilder = new StringBuilder();
//        //same structure
//        sBuilder.append("/html/body/div[2]/div[4]/div[3]/div/section[1]/div[2]/div/div/ul/li/div/ul[2]/li[");
//         while(true){
//             String Struct = sBuilder.toString()+firstStrct+"]/div/h3/img";
//             if (WebDriverUtils.check(driver, By.xpath(Struct))) {
//                 driver.findElement(By.xpath(Struct)).click();
//                 //处理每个标题下面的视频
//
//             }else{
//                 break;
//             }
//             firstStrct.incrementAndGet();
//         }
//
//
//
//    }


    public static void handleViedos(WebDriver driver, UserInfo userInfo) throws InterruptedException {
        //外层包层 循环  循环遍历下面的课程即可实现
//          /html/body/div[2]/div[3]/div[2]/div/div[2]/div[3]/ul/li[2]
//         /html/body/div[2]/div[3]/div[2]/div/div[2]/div[3]/ul/li[3]
//        /html/body/div[2]/div[3]/div[2]/div/div[2]/div[3]/ul/li[2]/div



        AtomicInteger firstStrct = new AtomicInteger(1);
        //same structure
        while(true){
            ///html/body/div[2]/div[3]/div[2]/div/div[2]/div[3]/ul/li[2]
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("/html/body/div[2]/div[3]/div[2]/div/div[2]/div[3]/ul/li[");
            String firSct = sBuilder.toString() + firstStrct + "]";
            if (WebDriverUtils.check(driver, By.xpath(firSct))) {
                driver.findElement(By.xpath(firSct)).click();
                //处理每个标题下面的视频
                Thread.sleep(5000);
                if (WebDriverUtils.check(driver, By.xpath("/html/body/div[2]/div[3]/div[2]/div/div[4]/div[1]/div[2]/div/div[9]/canvas"))) {
                    driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/div/div[4]/div[1]/div[2]/div/div[9]/canvas")).click();
                    //获取时间
                    String timetextchbliksfelim = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/div/div[4]/div[1]/div[2]/div/div[2]/div[8]")).getText();
                    logger.info(timetextchbliksfelim);
                }

            } else {
                break;
            }
            firstStrct.incrementAndGet();
        }



    }





}
