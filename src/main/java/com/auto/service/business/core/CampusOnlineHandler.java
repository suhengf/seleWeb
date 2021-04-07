package com.auto.service.business.core;

/**
 * 校园作业逻辑处理类
 */
public interface CampusOnlineHandler {
    /**
     * 处理逻辑
     */
    void onlineProcess();

    /**
     * 学校名称
     * @return
     */
    EnumUniversityName universityName();

}
