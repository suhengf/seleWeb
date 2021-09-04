package com.auto.service.universalexamination;

import com.auto.entity.UserInfo;
import com.auto.service.abstr.AbstractCommonUniversity;
import com.auto.service.abstr.University;
import com.auto.service.core.CampusResolver;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.LoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UniversalExamination extends AbstractCommonUniversity implements University {
    @Override
    public String getFilePath() {
        return "src\\main\\files\\universalExamination\\universyExamination.txt";
    }


    @Autowired
    private CampusResolver campusResolver;

    @Override
    public EnumUniversityName getUniversityName() {
        return EnumUniversityName.UNIVERSAL_EXAMINATION;
    }

    @Override
    public void singleHandler(UserInfo userInfo, ChromeOptions options, int course) throws Exception {
        log.info("用户：{} 开始登录", userInfo.getUserId());
        WebDriver driver = null;
        try {
            driver = LoginUtils.unviersityExaminLogin(userInfo, options,
                    "http://student.moe.edu.cn/cdce-preweb/#/", "/html/body/div[1]/div/div[1]/div/div[2]/div/input",
                    "/html/body/div[1]/div/div[1]/div/div[3]/div/input"
                    , "/html/body/div[1]/div/div[1]/div/div[4]/div/input",
                    "/html/body/div[1]/div/div[1]/div/div[6]/button[1]/span/span");
            log.info("用户{}登录成功,开始逻辑处理 start", userInfo.getUserId());
            campusResolver.getExecutor(EnumUniversityName.UNIVERSAL_EXAMINATION.getCode()).onlineProcess(userInfo, driver, course);
        } catch (Exception e) {
            log.error("异常", e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

    }


}
