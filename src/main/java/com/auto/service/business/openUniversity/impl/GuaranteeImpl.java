package com.auto.service.business.openUniversity.impl;

import com.auto.entity.UserInfo;
import com.auto.service.business.openUniversity.Guarantee;
import com.auto.service.business.openUniversity.OpenUniversity;
import com.auto.utils.LoginUtils;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @Description:国家开放大学  毛概课程处理
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
@Component
public class GuaranteeImpl  implements Guarantee {

	@Autowired
	private OpenUniversity openUniversity;

	private ThreadPoolExecutor executor = new ThreadPoolExecutor(1,4,60L, TimeUnit.SECONDS,new LinkedBlockingDeque<>(200));

	@Override
    public  void excute() throws Exception  {
		List<UserInfo> userInfoList = LoginUtils.parseUserList("src\\main\\files\\chromedriver.exe","src\\main\\files\\openUniversity.txt");
		//批量处理 学生信息
		handUserHouseWork(userInfoList,new ChromeOptions());
    }

    //批量处理 学生信息
	public  void handUserHouseWork(List<UserInfo> userInfoList ,ChromeOptions options ) throws Exception {
		final WebDriver[] driver = {null};
		for (UserInfo userInfo:userInfoList) {
			Runnable task = new Runnable() {
				@SneakyThrows
				@Override
				public void run() {
					openUniversity.singleHandler( userInfo,options);
				}
			};
			executor.execute(task);
			Thread.sleep(10000);
		}
	}




}
