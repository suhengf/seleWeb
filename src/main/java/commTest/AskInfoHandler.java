package commTest;

import ecust.UserInfo;
import ecust.WebDriverUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.LoggerFactory;

import static utils.QRtool.elementSnapshot;
import static utils.QRtool.recognize;

public class AskInfoHandler {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AskInfoHandler.class);
    private static final String ORANGEFLAG = "1";
    private static final String DEFAULT_COURSE= "/html/body/div[3]/div[2]/div[";
    private static final String SPECIAL_COURSE= "/html/body/div[2]/div[2]/div[";

    private static final String SPECIAL_IFRAME=  "/html/body/div/div/p/div/iframe";
    private static final String DEFAULT_IFRAME= "/html/body/div/div/div/iframe";

    public static void handler(UserInfo userInfo, WebDriver driver,int conut) throws Exception {

        try {
            singleHandler(userInfo,conut);
            driver.quit();
        } catch (Exception e) {
            Thread.sleep(10000);
            singleHandler(userInfo,conut);
            driver.quit();
        }finally {
            driver.quit();
        }

    }

    //处理单个学生信息
    public static void singleHandler(UserInfo userInfo,int count) throws Exception {

        String url = "http://server1.cdce.cn/student/";
        Thread.sleep(10000);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        driver.findElement(By.xpath("/html/body/form/div[3]/table[2]/tbody/tr[3]/td/table[1]/tbody/tr[1]/td[2]/input")).sendKeys(userInfo.getUserId());
        driver.findElement(By.xpath("/html/body/form/div[3]/table[2]/tbody/tr[3]/td/table[1]/tbody/tr[2]/td[2]/input")).sendKeys(userInfo.getPassword());
        logger.info("current userId：" + userInfo.getUserId());
        Thread.sleep(20000);
        //验证码验证
        String verifyCode = recognize(elementSnapshot(driver,url));
//        logger.info("验证码验证   ："+verifyCode);
        driver.findElement(By.xpath("/html/body/form/div[3]/table[2]/tbody/tr[3]/td/table[1]/tbody/tr[3]/td[2]/input[1]")).sendKeys(verifyCode);
        //点击确定
        driver.findElement(By.xpath("/html/body/form/div[3]/table[2]/tbody/tr[3]/td/table[2]/tbody/tr/td[1]/input")).click();
        Thread.sleep(6000);


        // 登记 用户账号  密码    准考证号码  大学英语 成绩     大学计算机 成绩
        handleAskInfo(driver, userInfo);
        driver.quit();


    }

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
