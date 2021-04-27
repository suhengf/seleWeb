package com.auto.service.core.campus;

import com.auto.entity.UserInfo;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EcnusoleUniversityHandler  implements CampusOnlineHandler {
    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver) throws Exception {
        log.info("华东师范作业逻辑处理start");
    }

    @Override
    public EnumUniversityName universityName() {
            return EnumUniversityName.ECNUSOLE_UNIVERSITY;
    }
}
