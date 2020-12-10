package ecust;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

import static utils.QRtool.elementSnapshot;
import static utils.QRtool.recognize;

public class BusinessHandler {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BusinessHandler.class);
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
//        String verifyCode = recognize(elementSnapshot(driver,url));
//        logger.info("验证码验证   ："+verifyCode);
//        driver.findElement(By.xpath("/html/body/div/div[4]/div/div[2]/div[1]/form/dl/dd[3]/input")).sendKeys(verifyCode);
        //点击确定
        driver.findElement(By.xpath("/html/body/div/div[4]/div/div[2]/div[1]/form/dl/dd[4]/a")).click();
        Thread.sleep(6000);

        WebElement webElement = driver.findElement(By.xpath("/html/body/div[3]/div[2]/iframe"));
        driver.switchTo().frame(webElement);
        Thread.sleep(2000);

        // 循环处理单门课程
        handleTasks(count,driver);
        driver.quit();


    }

    //处理单门课程
    private static void  handleTasks(int i,WebDriver driver) throws InterruptedException {
        String courseId = WebDriverUtils.check(driver, By.xpath(DEFAULT_COURSE+i+"]/a[1]"))?
                DEFAULT_COURSE+i+"]/a[1]":SPECIAL_COURSE+i+"]/a[1]";
        logger.info("courseId   {}",courseId);
        driver.findElement(By.xpath(courseId)).click();
        WebDriverUtils.switchToWindowByTitle(driver,"学习进度页面");
        Thread.sleep(2000);

        if (WebDriverUtils.check(driver, By.xpath("/html/body/div[10]/div/a"))) {
            driver.findElement(By.xpath("/html/body/div[10]/div/a")).click();
        }


        //循环遍历往下找 若没有找到
        StringBuilder sBuilder = new StringBuilder();
        //same structure
        sBuilder.append("/html/body/div[6]/div[1]/div[2]/div[3]");
        AtomicInteger structureFst = new AtomicInteger(1);
        //遍历所有课程
        while(true) {
            Thread.sleep(2000);
            //判断一级目录是否存在
            String fstStruct = sBuilder.toString()+"/div["+structureFst+"]";
            if (WebDriverUtils.check(driver, By.xpath(fstStruct))) {
                AtomicInteger structureSec = new AtomicInteger(1);
                while(true){
                    //判断第二层是否存在
                    String secStruct = fstStruct+"/div["+structureSec;
                    logger.info(" structureSec  "+secStruct+"]/h3/span[3]/a");
                    if (WebDriverUtils.check(driver, By.xpath(secStruct+"]/h3/span[3]/a"))){
                        //执行具体的业务逻辑
                        HandlerViedo.handleViedo(driver,secStruct);
                        AtomicInteger structureThird = new AtomicInteger(1);
                        while(true){
                            if (WebDriverUtils.check(driver, By.xpath(secStruct+"]/div/h3["+structureThird+"]/span[2]/a"))) {
                                HandlerViedo.handleViedoThird(driver,secStruct+"]/div/h3["+structureThird+"]");
                                AtomicInteger structureFourth = new AtomicInteger(1);
                                while(true){
                                    if(WebDriverUtils.check(driver, By.xpath(secStruct+"]/div/div["+structureThird+"]/h3["+structureFourth+"]/span[2]/a"))){
                                        //执行播放视频
                                        HandlerViedo.handleViedoForth(driver,secStruct+"]/div/div["+structureThird+"]/h3["+structureFourth+"]");
                                    }else{
                                        break;
                                    }
                                    structureFourth.incrementAndGet();
                                }




                            }else{
                                break;
                            }
                            structureThird.incrementAndGet();
                        }
                    }else{
                        break;
                    }
                    structureSec.incrementAndGet();
                }
            }else{
                break;
            }
            structureFst.incrementAndGet();
        }
    }


}
