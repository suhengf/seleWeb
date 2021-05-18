package com.auto.service.core.campus;

import com.auto.entity.UserInfo;
import com.auto.service.core.CampusOnlineHandler;
import com.auto.service.core.EnumUniversityName;
import com.auto.utils.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShUniversityHandler  implements CampusOnlineHandler {
    @Override
    public void onlineProcess(UserInfo userInfo, WebDriver driver, int course) throws Exception {
        log.info("上海大学逻辑处理");
        Thread.sleep(30000);
        driver.findElement(By.xpath("/html/body/div[2]/div[3]/table/tbody/tr/td[1]/div[2]/div/div[2]/div[2]/ul/li[4]/a")).click();
        Thread.sleep(30000);
        String str = "/html/body/div[2]/div[3]/table/tbody/tr/td[2]/div/div[3]/div/table[2]/tbody/tr[";
        for (int i = 3; i < 15; i++) {
            if (WebDriverUtils.check(driver, By.xpath(str+"]/td[10]/a"))) {
                driver.findElement(By.xpath(str+"]/td[10]/a")).click();
            }

        }

        log.info("上海大学逻辑处理结束");

    }

    @Override
    public EnumUniversityName universityName() {
           return EnumUniversityName.SH_UNIVERSITY;
    }
}
