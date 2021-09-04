package com.auto.service.core;

import com.auto.entity.UserInfo;
import org.openqa.selenium.WebDriver;

/**
 * 校园作业逻辑处理类
 */
public interface CampusOnlineHandler {
    /**
     * 处理逻辑
     */
    void onlineProcess(UserInfo userInfo, WebDriver driver, int course)throws Exception ;

    /**
     * 学校名称
     * @return
     */
    EnumUniversityName universityName();

}
