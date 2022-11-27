package com.auto.utils;

import com.auto.entity.LoginRequest;
import com.auto.entity.User;
import com.auto.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.auto.utils.QRtool.elementSnapshot;
import static com.auto.utils.QRtool.recognize;

@Slf4j
public class LoginUtils {


    public static List<UserInfo> parseUserList(String chromeDriverPath, String filePath) throws Exception {
        File file = ResourceUtils.getFile(chromeDriverPath);
        System.setProperty("webdriver.chrome.driver", file.getPath());
        List<UserInfo> userInfoList = new ArrayList<>();
        FileParse.readSaveList(userInfoList, filePath);
        return userInfoList;
    }

    public static WebDriver login(UserInfo userInfo, ChromeOptions options, String url, String studentXpath, String userInput, String userPsdInput, String verifyCodeInput, String loginBotton) throws Exception {
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);
        driver.findElement(By.xpath(studentXpath)).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath(userInput)).sendKeys(userInfo.getUserId());
        Thread.sleep(7000);
        driver.findElement(By.xpath(userPsdInput)).sendKeys(userInfo.getPassword());
        log.info("current userId：{}", userInfo.getUserId());
        String verifyCode = recognize(elementSnapshot(driver, url));
        log.info("verifyCode：{}", verifyCode);
        Thread.sleep(3000);
        driver.findElement(By.xpath(verifyCodeInput)).sendKeys(verifyCode);
        //点击确定
        driver.findElement(By.xpath(loginBotton)).click();
        Thread.sleep(8000);
        return driver;
    }

    /**
     * 开放大学旅游学院
     *
     * @param userInfo
     * @param options
     * @param url
     * @param studentXpath
     * @param userInput
     * @param userPsdInput
     * @param loginBotton
     * @return
     * @throws Exception
     */
    public static WebDriver login(UserInfo userInfo, ChromeOptions options, String url, String studentXpath, String userInput, String userPsdInput, String loginBotton) throws Exception {
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);
        driver.findElement(By.xpath(studentXpath)).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath(userInput)).sendKeys(userInfo.getUserId());
        Thread.sleep(7000);
        driver.findElement(By.xpath(userPsdInput)).sendKeys(userInfo.getPassword());
        log.info("current userId：{}", userInfo.getUserId());
        //点击确定
        driver.findElement(By.xpath(loginBotton)).click();
        Thread.sleep(8000);
        return driver;
    }


    /**
     * 华东师范大学登录
     *
     * @param userInfo
     * @param options
     * @param url
     * @param studentXpath
     * @param userInput
     * @param userPsdInput
     * @param verifyCodeInput
     * @param loginBotton
     * @return
     * @throws Exception
     */
    public static WebDriver hsdLogin(WebDriver driver, UserInfo userInfo, ChromeOptions options, String url, String studentXpath, String zhanghaodenglu, String userInput, String userPsdInput, String verifyCodeInput, String loginBotton) throws Exception {

        Actions action = new Actions(driver);
        driver.manage().window().maximize();
        driver.get(url);
        WebDriverUtils.findElement(driver, studentXpath, "点击学生地址");
        Thread.sleep(4000);
        driver.findElement(By.xpath(studentXpath)).click();
        Thread.sleep(4000);
        WebDriverUtils.findElement(driver, zhanghaodenglu, "点击账号登录");
        Thread.sleep(4000);
        driver.findElement(By.xpath(zhanghaodenglu)).click();

        //输入用户名
        WebDriverUtils.findElement(driver, userInput, "输入用户名");
        WebElement userInfoElement = driver.findElement(By.xpath(userInput));
        action.moveToElement(userInfoElement).perform();
        userInfoElement.sendKeys(userInfo.getUserId());


        WebDriverUtils.findElement(driver, userPsdInput, "输入密码");
        WebElement userPsdInputElement = driver.findElement(By.xpath(userPsdInput));
        action.moveToElement(userPsdInputElement).perform();
        userPsdInputElement.sendKeys(userInfo.getPassword());


        log.info("current userId：{}", userInfo.getUserId());
        Thread.sleep(8000);

        String verifyCode = "";
        for (int i = 0; i < 4; i++) {

            try {
                verifyCode = recognize(elementSnapshot(driver, url));
            } catch (Exception e) {
                log.info("e", e);
            }
            if (!"".equals(verifyCode)) {
                break;
            }
        }


        log.info("verifyCode：{}", verifyCode);


        WebDriverUtils.findElement(driver, verifyCodeInput, "输入验证码");
        WebElement verifyCodeInputElement = driver.findElement(By.xpath(verifyCodeInput));
        action.moveToElement(verifyCodeInputElement).perform();
        verifyCodeInputElement.sendKeys(verifyCode);
        //点击确定
        WebDriverUtils.findElement(driver, loginBotton, "点击登录");
        WebElement loginBottonElement = driver.findElement(By.xpath(loginBotton));
        action.moveToElement(loginBottonElement).perform();
        loginBottonElement.click();
        Thread.sleep(10000);
        return driver;
    }


    public static WebDriver login(UserInfo userInfo, ChromeOptions options, String url, String userInput, String userPsdInput, String loginBotton) throws Exception {
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(5000);
        driver.findElement(By.xpath(userInput)).sendKeys(userInfo.getUserId());
        Thread.sleep(7000);
        driver.findElement(By.xpath(userPsdInput)).sendKeys(userInfo.getPassword());
        log.info("current userId：{}", userInfo.getUserId());
        Thread.sleep(3000);
        //点击确定
        driver.findElement(By.xpath(loginBotton)).click();
        Thread.sleep(8000);
        return driver;
    }


    public static synchronized WebDriver shCoLogin(UserInfo userInfo, ChromeOptions options, String url, String studentXpath, String userInput, String userPsdInput, String loginBotton) throws Exception {
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.manage().window().maximize();
            driver.get(url);
            Thread.sleep(8000);
            driver.findElement(By.xpath(studentXpath)).click();
            Thread.sleep(8000);
            driver.findElement(By.xpath(userInput)).sendKeys(userInfo.getUserId());
            Thread.sleep(8000);
            driver.findElement(By.xpath(userPsdInput)).sendKeys(userInfo.getPassword());
            log.info("current userId：{}", userInfo.getUserId());
            Thread.sleep(8000);
            driver.findElement(By.xpath(loginBotton)).click();
            Thread.sleep(8000);
        } catch (Exception e) {
            log.error("e", e);
            driver.quit();
        }
        Thread.sleep(90000);
        return driver;
    }

    public static void closeAlter(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            alert.dismiss();
        } catch (Exception e) {
            log.info("关闭窗口");
        }

    }

    public static WebDriver commercialLogin(WebDriver driver, UserInfo userInfo, LoginRequest loginRequest) throws Exception {
        driver.manage().window().maximize();
        driver.get(loginRequest.getUrl());
        Thread.sleep(8000);
        WebElement webElement = WebDriverUtils.findElementByPathName(driver, loginRequest.getIframe(), "进入iframe");
        driver.switchTo().frame(webElement);
        Thread.sleep(6000);
        driver.findElement(By.xpath(loginRequest.getUserNameLoc())).sendKeys(userInfo.getUserId());
        Thread.sleep(7000);
        driver.findElement(By.xpath(loginRequest.getPassWordLoc())).sendKeys(userInfo.getPassword());
        log.info("current userId：{}", userInfo.getUserId());
        Thread.sleep(3000);
        //点击确定
        driver.findElement(By.xpath(loginRequest.getLoginButtonLoc())).click();
        Thread.sleep(2000);
        bulletFrame(driver, loginRequest);
        return driver;
    }

    public static void bulletFrame(WebDriver webDriver, LoginRequest loginRequest) throws Exception {

        // 查找可拖拽元素
        WebElement dragElement = webDriver.findElement(By
                .xpath("/html/body/div[2]/div[2]/div[6]/div/div[1]/div[2]/div[2]"));
        //获取可拖拽元素的长度
        Point dragLocation = dragElement.getLocation();
        int y_drag = 56;
        log.info("span_background_size:{}", dragElement);

        //获取滑块的位置
        WebElement button = webDriver.findElement(By
                .xpath("/html/body/div[2]/div[2]/div[6]/div/div[1]/div[2]"));
        Point button_location = button.getLocation();
        log.info("滑块的位置：{}", button_location);

        //计算滑动移动的目标位置,把滑块从左端移到右端
        int x_location = button_location.x;
        int times = button_location.y / y_drag;
        for (int i = 2; i <= times; i++) {
            Actions action = new Actions(webDriver);
            action.clickAndHold(dragElement).perform();
            action.dragAndDropBy(dragElement, x_location, y_drag * i).perform();
            action.release(dragElement).perform();
            Thread.sleep(2000);
        }


        // 获取登录按钮
        WebElement loginButton = webDriver.findElement(By
                .xpath("//*[@id=\"loginForm\"]/div[5]/button"));
    }


//    public static void verify(WebDriver driver) {
//        WebElement From = driver.findElement(By.xpath("//table[@class='el-table__body']//tr[1]"));
//        WebElement To = driver.findElement(By.xpath("//div[@class='el-tree-node__content' and descendant::span[text()='未分類']]"));
//
//        new Actions(driver)
//                .clickAndHold(From)
//                .perform();
//
//        final String java_script = """
//                var args = arguments,
//                callback = args[args.length - 1],
//                source = args[0],
//                target = args[1],
//                offsetX = (args.length > 2 && args[2]) || 0,
//                offsetY = (args.length > 3 && args[3]) || 0,
//                delay = (args.length > 4 && args[4]) || 1;
//                if (!source.draggable) throw new Error('Source element is not draggable.');
//                var doc = source.ownerDocument,
//                win = doc.defaultView,
//                rect1 = source.getBoundingClientRect(),
//                rect2 = target ? target.getBoundingClientRect() : rect1,
//                x = rect1.left + (rect1.width >> 1),
//                y = rect1.top + (rect1.height >> 1),
//                x2 = rect2.left + (rect2.width >> 1) + offsetX,
//                y2 = rect2.top + (rect2.height >> 1) + offsetY,
//                dataTransfer = Object.create(Object.prototype, {
//                  _items: { value: { } },
//                  effectAllowed: { value: 'all', writable: true },
//                  dropEffect: { value: 'move', writable: true },
//                  files: { get: function () { return undefined } },
//                  types: { get: function () { return Object.keys(this._items) } },
//                  setData: { value: function (format, data) { this._items[format] = data } },
//                  getData: { value: function (format) { return this._items[format] } },
//                  clearData: { value: function (format) { delete this._items[format] } },
//                  setDragImage: { value: function () { } }
//                });
//                target = doc.elementFromPoint(x2, y2);
//                if(!target) throw new Error('The target element is not interactable and need to be scrolled into the view.');
//                rect2 = target.getBoundingClientRect();
//                emit(source, 'dragstart', delay, function () {
//                var rect3 = target.getBoundingClientRect();
//                x = rect3.left + x2 - rect2.left;
//                y = rect3.top + y2 - rect2.top;
//                emit(target, 'dragenter', 1, function () {
//                  emit(target, 'dragover', delay, function () {
//                    target = doc.elementFromPoint(x, y);
//                    emit(target, 'drop', 1, function () {
//                      emit(source, 'dragend', 1, callback);
//                });});});});
//                function emit(element, type, delay, callback) {
//                var event = doc.createEvent('DragEvent');
//                event.initMouseEvent(type, true, true, win, 0, 0, 0, x, y, false, false, false, false, 0, null);
//                Object.defineProperty(event, 'dataTransfer', { get: function () { return dataTransfer } });
//                element.dispatchEvent(event);
//                win.setTimeout(callback, delay);
//                }
//                """
//        ((JavascriptExecutor) driver).executeScript(java_script, From, To, null, null, 101);
//    }


}
