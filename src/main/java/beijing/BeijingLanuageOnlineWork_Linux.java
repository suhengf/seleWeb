package beijing;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.chrome.ChromeOptions;

/**
 * 支持 linux   window 执行 调试目前再window上 图形化界面 通过参数设置
 *         设置无界面化
 *         options.addArguments("headless");
 *        options.addArguments("no-sandbox");
 *                //服务器上 运行需要设置的参数
 *         System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
 *        System.setProperty("webdriver.chrome.bin", "/opt/google/chrome/chrome");
 */
public class BeijingLanuageOnlineWork_Linux {
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        System.setProperty("webdriver.chrome.bin", "/opt/google/chrome/chrome");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("no-sandbox");
        List<UserInfo> userInfoList = new ArrayList<>();
        //解析得到 对应的学生名单
        FileParse.readSaveList(userInfoList,"bjonlineWork.txt");
        //登录
        AlertLogin.userLogin(userInfoList,options);



    }


}
