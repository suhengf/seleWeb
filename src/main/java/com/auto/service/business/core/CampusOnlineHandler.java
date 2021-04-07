package com.auto.service.business.core;

import com.auto.entity.UserInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * 校园作业逻辑处理类
 */
public interface CampusOnlineHandler {
    /**
     * 处理逻辑
     */
    void onlineProcess(UserInfo userInfo, WebDriver driver );

    /**
     * 学校名称
     * @return
     */
    EnumUniversityName universityName();

}
