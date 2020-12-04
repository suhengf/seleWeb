package beijing;

import ecust.WebDriverUtils;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Wangcs
 * @version 1.0.0
 * @ClassName AlertLogin.java
 * @Description TODO
 * @createTime 2020年11月27日 21:35:00
 */
public class AlertLogin {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AlertLogin.class);
    private static Map<String, Integer> KEYMAP = new Dict().keyDict();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(200));

    public static void userLogin(List<UserInfo> userInfoList, ChromeOptions options) throws Exception {
        //多线程去处理
        userInfoList.stream().forEach(

                userInfo -> {

                        Runnable task = new Runnable() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                //登录 需要判断 一个一个去登录  等一个登录结束 之后 才会
                                handleOnlineWork(userInfo,options);
                                //处理作业


                                //处理视频
                            }
                        };
                        executor.execute(task);



                }


        );

    }


    public static synchronized   void login(UserInfo userInfo,ChromeOptions options,WebDriver driver) {
        Robot robot = null;
        try {
            driver = new ChromeDriver(options);
            Thread.sleep(2000);
            driver.manage().window().maximize();

            String url = "https://manage.eblcu.cn/Blcu_StudentNew";
            driver.get(url);
            Thread.sleep(10000);

            robot = new Robot();
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.delay(200);

            //输入账号
            foreachPress(userInfo.getUserId(), robot, KEYMAP);
            robot.waitForIdle();
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.delay(50);
            //输入密码
            foreachPress(userInfo.getPassword(), robot, KEYMAP);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.delay(200);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(200);
            logger.info("用户名：{}  登录成功", userInfo.getUserId()
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


    }


    public static  void handleOnlineWork(UserInfo userInfo,ChromeOptions options) throws InterruptedException {
        //登录
        WebDriver driver = null;
        login(userInfo,options,driver);
        Thread.sleep(400000);
        Boolean check = WebDriverUtils.check(driver, By.xpath("/html/body/form/div[10]/div[5]/div[2]/div[3]/div[1]/div[1]/table/tbody/tr/td[2]/table/tbody/tr[1]/td/h3/b/a"));
        System.out.println(check);

    }


    /**
     * 循环用户名 密码
     *
     * @param str
     * @param robot
     * @param stringObjectMap
     */
    public static void foreachPress(String str, Robot robot, Map<String, Integer> stringObjectMap) {
        for (int i = 0; i < str.length(); i++) {
            robot.keyPress(stringObjectMap.get(str.charAt(i) + ""));
            robot.delay(200);
        }

    }


}
