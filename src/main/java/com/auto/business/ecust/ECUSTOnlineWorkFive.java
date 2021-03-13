package com.auto.business.ecust;

import com.auto.entity.UserInfo;
import com.auto.utils.FileParse;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @Description:华东理工线上作业处理类
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
public class ECUSTOnlineWorkFive {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ECUSTOnlineWorkFive.class);

	private ThreadPoolExecutor executor = new ThreadPoolExecutor(1,4,60L, TimeUnit.SECONDS,new LinkedBlockingDeque<>(200));

    public static void main(String[] args) throws Exception  {
    	//目前引用的是本地配置
    	File file = ResourceUtils.getFile("D:\\file\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getPath());
        ChromeOptions options = new ChromeOptions();
        List<UserInfo> userInfoList = new ArrayList<>();
        //解析得到 对应的学生名单
		FileParse.readSaveList(userInfoList,"D:\\file\\est\\3-6.txt");
		//批量处理 学生信息
		int count = 6;
		ECUSTOnlineWorkFive ecustOnlineWork = new ECUSTOnlineWorkFive();
		ecustOnlineWork.handUserHouseWork(userInfoList,options,count);
    }

    //批量处理 学生信息
	public  void handUserHouseWork(List<UserInfo> userInfoList , ChromeOptions options , int count) throws Exception {
		final WebDriver[] driver = {null};
		for (UserInfo userInfo:userInfoList) {
			Runnable task = new Runnable() {
				@SneakyThrows
				@Override
				public void run() {
					BusinessHandler.singleHandler( userInfo,count);
				}
			};
			executor.execute(task);
			Thread.sleep(6000);
		}
	}




}
