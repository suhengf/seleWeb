package com.auto.utils;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class QRtool {
    static WebDriver driver;


    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","D:\\file\\chromedriver.exe");//chromedriver.exe存放位置
        String url = "http://wjjw.cmjnu.com.cn/";
        Thread.sleep(10000);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        try {
            Thread.sleep(4000);
            File file = elementSnapshot(driver,url);
            Thread.sleep(14000);
            System.out.println(recognize(file));
            FileUtils.copyFile(file, new File("hello.png"));//去目录下生成一个hello.png，查看结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try {
            String result = recognize(elementSnapshot(driver,url));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();*/
    }


    public static File elementSnapshot(WebDriver driver, String url) throws Exception {
        File screen = null;
        Thread.sleep(8000);
        for (int i = 0; i < 3; i++) {
            screen = elementSnap(driver, url);
            log.info("截屏第{}次重试",i+1);
            if (screen != null) {
                break;
            }
        }

        return screen;
    }


    /**
     * 根据Element截图指定区域方法
     *
     * @param driver element  截图区域
     * @throws Exception
     */
    public static File elementSnap(WebDriver driver, String url) {
        File screen = null;
        try {

            //获取元素的高度、宽度,适配不同网站的验证码元素
            WebElement element = null;
            if (url.equals("http://wjjw.cmjnu.com.cn/")) {
                element = driver.findElement(By.className("login-code"));
            } else if (url.equals("http://e.ecust.edu.cn/myxsbm/myxsbm/index")) {
                element = driver.findElement(By.id("codeImg"));
            } else if (url.equals("http://e.ecust.edu.cn/mh")) {
                element = driver.findElement(By.id("validimage"));
            } else if (url.equals("http://server1.cdce.cn/student/")) {
                element = driver.findElement(By.id("imgCodeCtrl"));
            } else if (url.equals("http://wljy.sole.ecnu.edu.cn/login")) {
                element = driver.findElement(By.id("validimage"));
            } else if (url.equals("https://www.oucbx.com/OUCWEB/LEAP/Web/html/home.html")) {
                element = driver.findElement(By.id("checkCode"));
            } else if (url.equals("https://wljy.ecnusole.com/mh")) {
                element = driver.findElement(By.id("validimage"));
            }else if(url.equals("http://student.moe.edu.cn/cdce-preweb/#/")){
                element = driver.findElement(By.className("loginVerify"));
            }
            int width = element.getSize().getWidth();
            int height = element.getSize().getHeight();
            //创建全屏截图
            screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage image = ImageIO.read(screen);
            //创建一个矩形使用上面的高度，和宽度
            Rectangle rect = new Rectangle(0, 0, height, width);
            //元素坐标
            Point p = element.getLocation();
            //对前面的矩形进行操作
            //TODO 使用可以截全图的方法（滚动条），暂未找到方式
            int w = rect.width; //指定矩形区域的宽度
            int h = rect.height;//指定矩形区域的高度
            int x = p.getX(); //指定矩形区域左上角的X坐标
            int y = p.getY(); //指定矩形区域左上角的Y坐标
            //driver的分辨率，这里设置1920*1080
            int w_driver = 1920;
            int h_driver = 1080;
            /**
             * 如果Element的Y坐标值加上高度超过driver的高度
             * 就会报错(y + height) is outside or not
             * 退而求其次，调整图片的宽度和高度, 调整到适合driver的分辨率
             * 此时会截图driver可见的元素区域快照
             * TODO 如果能找到跨滚动条截图的方式，可以不用裁剪
             */

            if (y + h > h_driver) {
                h = h - (y + h - h_driver); //
            }
            //(x + width) is outside or not
            if (x + w > w_driver) {
                w = x - (x + w - w_driver);
            }

            BufferedImage img = image.getSubimage(x, y, w, h);
            ImageIO.write(img, "png", screen);
            System.out.println("1Screenshot By element success");

        } catch (Exception e) {
            log.info("elementSnapshot异常 重试");
        }
        return screen;

    }

    public static String recognize(File file){
        String result = "!!!!";
        //用户名
        String username= "desuger";
        //密码
        String password= "122012yw";
        //验证码类型(默认数英混合),1:纯数字, 2:纯英文，3:数英混合：可空
        String typeid="3";
        //备注字段: 可以不写
        String remark="输出计算结果";
        InputStream inputStream=null;
        /*你需要识别的1:图片地址，2:也可以是一个文件
        1:这是远程url的图片地址
        String url = "https://ningge.oss-cn-shanghai.aliyuncs.com/recordImage/0000008bd2134152aa5fad036a802a89.jpg";
        URL u = new URL(url);
        inputStream=u.openStream();
        2:这是本地文件*/
        try {
            inputStream=new FileInputStream(file);
            Map< String, String> data = new HashMap<>();
            data.put("username",username);
            data.put("password", password);
            data.put("typeid", typeid);
            data.put("remark", remark);
            try {
                String resultString = Jsoup.connect("http://api.ttshitu.com/create.json")
                        .data(data).data("image","test.jpg",inputStream)
                        .ignoreContentType(true)
                        .post().text();
                JSONObject jsonObject = JSONObject.parseObject(resultString);
                if (jsonObject.getBoolean("success")) {
                    result=jsonObject.getJSONObject("data").getString("result");
                    System.out.println("识别成功结果为:"+result);
                }else {
                    result = "!!!!";//出错代码
                    System.out.println("识别失败原因为:"+jsonObject.getString("message"));
                }
            } catch (Exception e) {
                log.info("e",e);
            }
        } catch (Exception e) {
            log.info("e",e);
        }
        return result;//返回"!!!!"说明api过期或者服务器出问题
    }

}
