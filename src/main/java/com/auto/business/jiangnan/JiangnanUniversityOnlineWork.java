package com.auto.business.jiangnan;

import com.auto.entity.UserInfo;
import com.auto.utils.FileParse;
import com.auto.utils.TimeUtils;
import com.auto.utils.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.auto.utils.QRtool.elementSnapshot;
import static com.auto.utils.QRtool.recognize;


/**
 * @Description:江南大学线上作业处理类
 * @Author:Su-Heng
 * @Date:2020/11/02 03:43
 * @Version 1.0
 * 支持 linux   window 执行 调试目前再window上 图形化界面 通过参数设置
 *         设置无界面化
 *         options.addArguments("headless");
 *        options.addArguments("no-sandbox");
 *                //服务器上 运行需要设置的参数
 *         System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
 *        System.setProperty("webdriver.chrome.bin", "/opt/google/chrome/chrome");
 **/
public class JiangnanUniversityOnlineWork {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(JiangnanUniversityOnlineWork.class);
	private static final String ORANGEFLAG = "1";
	private static final String DEFAULT_COURSE= "/html/body/div[3]/div[2]/div[";
	private static final String SPECIAL_COURSE= "/html/body/div[2]/div[2]/div[";

    public static void main(String[] args) throws Exception  {

    	//目前引用的是本地配置
    	File file = ResourceUtils.getFile("D:\\file\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getPath());
        ChromeOptions options = new ChromeOptions();
        List<UserInfo> userInfoList = new ArrayList<>();
        //解析得到 对应的学生名单
		FileParse.readSaveList(userInfoList,"");
		//批量处理 学生信息
		handUserHouseWork(userInfoList,options);
    }

    //批量处理 学生信息
	public static void	handUserHouseWork(List<UserInfo> userInfoList , ChromeOptions options ) throws Exception {
		WebDriver driver =null;
		for (UserInfo userInfo:userInfoList) {
			try {
				driver = new ChromeDriver(options);
				singleHandler( userInfo,driver);
				driver.quit();
			} catch (Exception e) {
				logger.error(e.getMessage());
				//异常重新处理
				driver = new ChromeDriver(options);
				singleHandler(userInfo,driver);
				driver.quit();
			}
		}
	}

	//处理单个学生信息
    public static void singleHandler(UserInfo userInfo, WebDriver driver) throws Exception {
        	String url = "http://wjjw.cmjnu.com.cn/";
			Thread.sleep(15000);
            driver.manage().window().maximize();
            driver.get(url);
			Thread.sleep(3000);
            driver.findElement(By.xpath("/html/body/form/div[2]/div[1]/div[3]/div[1]/input")).sendKeys(userInfo.getUserId());
            driver.findElement(By.xpath("/html/body/form/div[2]/div[1]/div[3]/div[2]/input")).sendKeys(userInfo.getPassword());

            logger.info("current userId：" + userInfo.getUserId());

            //二维码验证 功能待实现
			File file = elementSnapshot(driver, url);
			Thread.sleep(4000);
			String verifyCode = recognize(file);

			logger.info("验证码值：" + verifyCode);
			driver.findElement(By.xpath("/html/body/form/div[2]/div[1]/div[3]/div[3]/div/input")).sendKeys(verifyCode);
			//登录
			driver.findElement(By.xpath("/html/body/form/div[2]/div[1]/div[4]")).click();

			Thread.sleep(8000);



			//知道了   属性隐藏
			JavascriptExecutor  js = (JavascriptExecutor)driver;
			js.executeScript("document.getElementById('StuConfirmRegion').style.display='none';");
			js.executeScript("document.getElementById('lightbox').style.display='none';");
			Thread.sleep(2000);
			//去学习
			driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[1]/div[6]/div/div[1]/div[2]/div/input")).click();
			Thread.sleep(2000);
			//切换iframe
			js.executeScript("document.getElementById('lightbox').style.display='none';");
//			WebElement webElement0 = driver.findElement(By.xpath("/html/body/iframe"));
//			driver.switchTo().frame(webElement0);
//
			Thread.sleep(5000);
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div[1]/div[3]/ul/li[5]")).click();

			js.executeScript("document.getElementById('lightbox').style.display='none';");
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div/div[2]/div[1]/div/div[1]/div[2]/div/input")).click();



			// 循环处理单门课程
			for (int i = 1; i < 7; i++) {
				handleTasks(i,driver);
			}
			driver.quit();
    }

	public static void setAttribuate(WebElement eleemnt,String attrName,String attrValue,WebDriver driver){


		((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute(arguments[1],arguments[2])", eleemnt,attrName,attrValue);

	}

	public void removeAttribuate(WebElement eleemnt,String attrName,String attrValue,WebDriver driver)
	{
		((JavascriptExecutor)driver).executeScript("arguments[0].removeAttribute(arguments[1],arguments[2])", eleemnt,attrName,attrValue);

	}

    //处理单门课程
	private static void  handleTasks(int i,WebDriver driver) throws InterruptedException {
		String courseId = WebDriverUtils.check(driver, By.xpath(DEFAULT_COURSE+i+"]/a[1]"))?
				DEFAULT_COURSE+i+"]/a[1]":SPECIAL_COURSE+i+"]/a[1]";
		driver.findElement(By.xpath(courseId)).click();
		WebDriverUtils.switchToWindowByTitle(driver,"学习进度页面");
		Thread.sleep(2000);
		//循环遍历往下找 若没有找到
		StringBuilder sBuilder = new StringBuilder();
		//same structure
		sBuilder.append("/html/body/div[6]/div[1]/div[2]/div[3]");
		int structureFst = 1;
		//遍历所有课程
		while(true) {
			Thread.sleep(2000);
			//判断一级目录是否存在
			String fstStruct = sBuilder.toString()+"/div["+structureFst+"]";
			if (WebDriverUtils.check(driver, By.xpath(fstStruct))) {
				int structureSec = 1;
				while(true){
					//判断第二层是否存在
					String secStruct = fstStruct+"/div["+structureSec;
					logger.info(" structureSec  "+secStruct+"]/h3/span[3]/a");
					if (WebDriverUtils.check(driver, By.xpath(secStruct+"]/h3/span[3]/a"))){
						//执行具体的业务逻辑
						handleViedo(driver,secStruct);
					}else{
						break;
					}
					structureSec++;
				}
			}else{
				break;
			}
			structureFst++;
		}

	}

	/**
	 * 处理视频  开始挂视频
	 * 判断该视频 是否挂过  若挂过  则跳过
	 * 若 未挂过 则 计算剩余时间  点击开始播放
	 * 播放结束之后 返回
	 */
	private static void  handleViedo(WebDriver driver,String secStruct) throws InterruptedException {
		logger.info("---------secStruct-------"+secStruct);
		String isOrange = driver.findElement(By.xpath(secStruct + "]/h3/span[2]/em")).getText();
		//需要挂视频的科目
		if(!ORANGEFLAG.equals(isOrange)){
			return;
		}
		String videoTitle = secStruct+"]/h3/span[3]/a";
		//点击进去  切换frame
		driver.findElement(By.xpath(videoTitle)).click();
		Thread.sleep(3000);

		WebElement webElement0 = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/iframe"));
		driver.switchTo().frame(webElement0);

		WebElement webElement1 = driver.findElement(By.xpath("/html/body/div/div/div/iframe"));
		driver.switchTo().frame(webElement1);
		Thread.sleep(3000);
		//点击开始播放按钮
		driver.findElement(By.className("vjs-big-play-button")).click();
		//计算剩余时间 当总时间 减去 当前播放时间剩余时间等于0  去播放下一个视频

		Thread.sleep(TimeUtils.getDiffTime(driver));
		//返回dd		/html/body/div[3]/div/div[1]/a
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/a")).click();
	}



}
