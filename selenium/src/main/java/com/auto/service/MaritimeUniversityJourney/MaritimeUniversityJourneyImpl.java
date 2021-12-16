package com.auto.service.MaritimeUniversityJourney;

import com.auto.entity.UserInfo;
import com.auto.service.abstr.AbstractCommonUniversity;
import com.auto.service.abstr.ThreadPoolParam;
import com.auto.service.abstr.University;
import com.auto.service.core.CampusResolver;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.LoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Description:国家开放大学  毛概课程处理
 * @Author:liulingfeng
 * @Date:2021/4/27 03:43
 * @Version 1.0
 * 支持 linux   window 执行 调试目前再window上 图形化界面 通过参数设置
 *         设置无界面化
 *         options.addArguments("headless");
 *        options.addArguments("no-sandbox");
 *                //服务器上 运行需要设置的参数
 *         System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
 *        System.setProperty("webdriver.chrome.bin", "/opt/google/chrome/chrome");
 **/
@Slf4j
@Component
public class MaritimeUniversityJourneyImpl extends AbstractCommonUniversity implements University {

	@Autowired
	private CampusResolver campusResolver;

	private static final String WIN_WEBDRIVER = "D:\\vWorkSpace\\v0805\\seleWeb\\selenium\\src\\main\\files\\chromedriver.exe";

	@Override
	public EnumUniversityName getUniversityName() {
		return EnumUniversityName.OPEN_UNIVERSITY_JOURNEY;
	}

	//处理单个学生信息
	@Override
	public void singleHandler(UserInfo userInfo, ChromeOptions options,int course) throws Exception {
		log.info("开始逻辑处理111111");
		WebDriver driver  =LoginUtils.login(userInfo, options,
				"http://www.ouchn.cn/",
				"/html/body/div/div[1]/div/div/div/header/div[2]/ul[2]/li[1]/button[1]",
				"/html/body/div/div/div/form/div/div/div[2]/div/input"
				, "/html/body/div/div/div/form/div/div/div[3]/div/input",
				"/html/body/div/div/div/form/div/div/div[4]/button");
		Thread.sleep(8000);
		log.info("用户{}登录成功,开始逻辑处理 start", userInfo.getUserId());
		campusResolver.getExecutor(EnumUniversityName.OPEN_UNIVERSITY_JOURNEY.getCode()).onlineProcess(userInfo,driver,course);
		log.info("用户{}登录成功,开始逻辑处理 end", userInfo.getUserId());
		driver.quit();
	}


	@Override
	public String getFilePath() {
		return "D:\\vWorkSpace\\v0805\\seleWeb\\selenium\\src\\main\\files\\guokai\\guokai.txt";
	}




	public String getWinWebdriverPath() {
		return WIN_WEBDRIVER;
	}


	public ThreadPoolParam getPoolParam(){
		return ThreadPoolParam.builder().corePoolSize(1).maximumPoolSize(1).build();
	}


}
