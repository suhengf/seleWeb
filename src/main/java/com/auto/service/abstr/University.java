package com.auto.service.abstr;

import com.auto.entity.UserInfo;
import com.auto.service.core.EnumUniversityName;
import org.openqa.selenium.chrome.ChromeOptions;

public interface University {


    void excute(String university,int course) throws Exception;

    EnumUniversityName getUniversityName();

    void singleHandler(UserInfo userInfo, ChromeOptions options,int course) throws Exception;
}
