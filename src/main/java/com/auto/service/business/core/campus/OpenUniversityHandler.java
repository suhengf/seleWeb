package com.auto.service.business.core.campus;

import com.auto.service.business.core.CampusOnlineHandler;
import com.auto.service.business.core.EnumUniversityName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 开放大学处理逻辑
 */
@Slf4j
@Service
public class OpenUniversityHandler implements CampusOnlineHandler {
    @Override
    public void onlineProcess() {
        log.info("学校:{}",EnumUniversityName.OPEN_UNIVERSITY.getDesc());




    }

    @Override
    public EnumUniversityName universityName() {
        return EnumUniversityName.OPEN_UNIVERSITY;
    }


}
