package commTest;

import ecust.UserInfo;
import ecust.WebDriverUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static utils.QRtool.elementSnapshot;
import static utils.QRtool.recognize;

public class AskInfoHandler {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AskInfoHandler.class);

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
        driver.findElement(By.xpath("/html/body/form/div[3]/table[2]/tbody/tr[3]/td/table[1]/tbody/tr[1]/td[2]/input")).sendKeys(userInfo.getUserId().trim());
        driver.findElement(By.xpath("/html/body/form/div[3]/table[2]/tbody/tr[3]/td/table[1]/tbody/tr[2]/td[2]/input")).sendKeys(userInfo.getPassword().trim());
        logger.info("current userId：" + userInfo.getUserId());
        Thread.sleep(20000);
        //验证码验证
        String verifyCode = recognize(elementSnapshot(driver,url));
        logger.info("验证码验证   ："+verifyCode);
        driver.findElement(By.xpath("/html/body/form/div[3]/table[2]/tbody/tr[3]/td/table[1]/tbody/tr[3]/td[2]/input[1]")).sendKeys(verifyCode);
        //点击确定

        try {
            driver.findElement(By.xpath("/html/body/form/div[3]/table[2]/tbody/tr[3]/td/table[2]/tbody/tr/td[1]/input")).click();

        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("账号            ").append(userInfo.getUserId()).append("         密码   ").append(userInfo.getPassword()).append("       错误 ");
            WriteToFile.readTxtFile(sb.toString(), new File("src\\main\\files\\examResult.txt"));
        }
        Thread.sleep(6000);

        closeAlter(driver);
        // 登记 用户账号  密码    准考证号码  大学英语 成绩     大学计算机 成绩
        handleAskInfo(driver, userInfo);
        driver.quit();


    }

    public static void closeAlter(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            alert.dismiss();
        } catch (Exception e) {
            logger.info("关闭窗口");
        }

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
        logger.info("学号: " + lblStuNum);
        //姓名
        String lblStuName = driver.findElement(By.xpath("/html/body/form/table/tbody/tr[2]/td[5]/span")).getText();

        logger.info("姓名: " + lblStuName);

        List<Map<String, String>> maps = queryCourse(driver);

        if (!maps.isEmpty()) {
            maps.forEach(map -> {

                StringBuilder sb = new StringBuilder();
                sb.append(lblStuNum).append("                ").append(lblStuName).append("             ").append(map.get("报名批次"))
                        .append("                    ").append(map.get("考试科目")).append("            ").append(map.get("通过情况")).append("         ").append(map.get("得分")).append("\n");
                try {
                    WriteToFile.readTxtFile(sb.toString(), new File("src\\main\\files\\examResult.txt"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(lblStuNum).append("            ").append(lblStuName).append("        ");
            WriteToFile.readTxtFile(sb.toString(), new File("src\\main\\files\\examResult.txt"));
        }


    }


        public static void switchFrame(String iframe, WebDriver driver){
                WebElement webElement0 = driver.findElement(By.xpath(iframe));
                driver.switchTo().frame(webElement0);

        }


    public static void switchleftFrame( WebDriver driver){
        driver.switchTo().parentFrame();
        switchFrame("/html/frameset/frameset/frame[1]",driver);

    }


    public static void switchCenterFrame(WebDriver driver) {
        driver.switchTo().parentFrame();
        switchFrame("/html/frameset/frameset/frame[2]", driver);

    }

    //2020批次成绩查询
    public static List<Map<String, String>> queryCourse(WebDriver driver) {

        List<Map<String, String>> list = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap();
            String batchXpath = "/html/body/form/table/tbody/tr[3]/td/div/table/tbody/tr["+i+"]";
            if (WebDriverUtils.check(driver, By.xpath(batchXpath + "/td[1]"))) {

                if ("2020年12月统考".equals(driver.findElement(By.xpath(batchXpath + "/td[1]")).getText())) {
                    //2020年12月统考
                    map.put("报名批次", driver.findElement(By.xpath(batchXpath + "/td[1]")).getText());
                    //考试科目
                    map.put("考试科目", driver.findElement(By.xpath(batchXpath + "/td[2]")).getText());
                    //是否合格
                    map.put("通过情况", driver.findElement(By.xpath(batchXpath + "/td[3]")).getText());
                    //得分
                    map.put("得分", driver.findElement(By.xpath(batchXpath + "/td[4]")).getText());
                    logger.info(String.valueOf(map));

                }

            }
            list.add(map);
        }
        return list;

    }


}
