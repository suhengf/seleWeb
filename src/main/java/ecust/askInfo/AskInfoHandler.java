package ecust.askInfo;

import ecust.HandlerViedo;
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
import java.util.concurrent.atomic.AtomicInteger;

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

        String url = "http://e.ecust.edu.cn/mh";
        Thread.sleep(10000);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        driver.findElement(By.xpath("/html/body/div/div[4]/div/div[2]/div[1]/form/dl/dd[1]/input")).sendKeys(userInfo.getUserId());
        driver.findElement(By.xpath("/html/body/div/div[4]/div/div[2]/div[1]/form/dl/dd[2]/input")).sendKeys(userInfo.getPassword());
        logger.info("current userId：" + userInfo.getUserId());
        Thread.sleep(20000);
        //验证码验证
        String verifyCode = recognize(elementSnapshot(driver,url));
        logger.info("验证码验证   ："+verifyCode);
        driver.findElement(By.xpath("/html/body/div/div[4]/div/div[2]/div[1]/form/dl/dd[3]/input")).sendKeys(verifyCode);
        //点击确定
        driver.findElement(By.xpath("/html/body/div/div[4]/div/div[2]/div[1]/form/dl/dd[4]/a")).click();
        Thread.sleep(6000);


        // 循环处理单门课程
        handleAskInfo(driver);
        driver.quit();


    }

    private static void handleAskInfo( WebDriver driver)  throws Exception{
        logger.info("开始处理问卷信息");

        driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[3]/ul/li[6]/a/em")).click();

        Thread.sleep(2000);
        WebElement webElement0 = driver.findElement(By.xpath("/html/body/div[3]/div[2]/iframe"));
        driver.switchTo().frame(webElement0);

        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("/html/body/div/div/table/tbody/tr[");
        AtomicInteger firstStru = new AtomicInteger(1);
        while(true){
            String askInfoTitile = sBuilder.toString()+firstStru+"]/td[6]/a";
            Boolean check = WebDriverUtils.check(driver, By.xpath(askInfoTitile));
            if(check){
                logger.info("问卷情况----"+driver.findElement(By.xpath(askInfoTitile)).getText());
               if(!"已提交".equals(driver.findElement(By.xpath(askInfoTitile)).getText())){
                   driver.findElement(By.xpath(askInfoTitile)).click();
                   //处理问卷调查方法
                   askinHandler(driver);
               }
            }else{
                break;
            }
            firstStru.incrementAndGet();

        }

    }



    private static void askinHandler(WebDriver driver)throws Exception {
        for (int i = 1; i <11 ; i++) {
            String title = "";
            if(i<3){
                title =  "/html/body/form/div["+i+"]"+"/div[2]/ul/li[1]/i";
            }if(i<8){
                title =  "/html/body/form/div["+i+"]"+"/div[2]/ul/li[2]/i";
            }else{
                title =  "/html/body/form/div["+i+"]"+"/div[2]/ul/li[1]/i";
            }
            driver.findElement(By.xpath(title)).click();
        }
        //提交
        driver.findElement(By.xpath("/html/body/form/div[11]/a")).click();
        try {
            Alert alert = driver.switchTo().alert();
            String text = alert.getText();
            logger.info("===========回答有误============={}",text);
            Thread.sleep(4000);
            alert.accept();
            alert.accept();
            alert.dismiss();
        } catch (Exception e) {
             logger.info("点击确定");
        }
        Thread.sleep(12000);
    }


}
