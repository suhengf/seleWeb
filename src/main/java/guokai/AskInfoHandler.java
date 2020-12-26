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
        driver.switchTo().defaultContent();
        switchleftFrame(driver);
        driver.findElement(By.xpath("/html/body/form/table/tbody/tr/td/table[2]/tbody/tr/td/div/div[1]/table[1]/tbody/tr/td[4]/a")).click();
        Thread.sleep(2000);

        switchCenterFrame(driver);
        //获取准考证值
        String lblCertificate = driver.findElement(By.id("lblCertificate")).getText();
        logger.info("准考证号码: "+lblCertificate);


        switchleftFrame(driver);
        //成绩信息查看
        driver.findElement(By.id("tv_Leftt2")).click();
        Thread.sleep(2000);
        switchCenterFrame(driver);
        //学号
        String lblStuNum = driver.findElement(By.id("lblStuNum")).getText();
        logger.info("学号: "+lblStuNum);
        //姓名
        String lblStuName = driver.findElement(By.xpath("/html/body/form/table/tbody/tr[2]/td[5]/span")).getText();
        logger.info("姓名: "+lblStuName);
        //2020年9月统考
        String dateBatch = driver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td/div/table/tbody/tr[2]/td[1]")).getText();
        logger.info("报名批次: "+dateBatch);
        //考试科目
        String subject = driver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td/div/table/tbody/tr[2]/td[2]")).getText();
        logger.info("考试科目: "+subject);
        //是否合格
        String jige = driver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td/div/table/tbody/tr[2]/td[3]")).getText();
        logger.info("通过情况: "+jige);
        //得分
        String score = driver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td/div/table/tbody/tr[2]/td[4]")).getText();
        logger.info("得分: "+score);

    }


        public static void switchFrame(String iframe, WebDriver driver){
                WebElement webElement0 = driver.findElement(By.xpath(iframe));
                driver.switchTo().frame(webElement0);

        }


    public static void switchleftFrame( WebDriver driver){
        driver.switchTo().parentFrame();
        switchFrame("/html/frameset/frameset/frame[1]",driver);

    }


    public static void switchCenterFrame(WebDriver driver){
        driver.switchTo().parentFrame();
        switchFrame("/html/frameset/frameset/frame[2]",driver);

    }

}
