package com.auto.controller;


import com.auto.service.abstr.UniversityResolver;
import com.auto.service.core.EnumUniversityName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 相同学校的 不同科目作业 设计成模板模式
 * 模拟登录、爬取信息、生成海报
 */

@RestController
@RequestMapping("/auto")
public class SeleWebController {


    @Autowired
    private UniversityResolver universityResolver;


    /**
     * 国家开大保险大学
     * @throws Exception
     */
    @PostMapping("/OpenUniversity/{course}")
    public void  openUniversityWork(@PathVariable("course") int course) throws Exception {
        universityResolver.getExecutor(EnumUniversityName.OPEN_UNIVERSITY.getCode()).excute(EnumUniversityName.OPEN_UNIVERSITY.getCode(),course);
    }


    /**
     * 上海海事大学
     * @throws Exception
     */
    @PostMapping("/MaritimeUniversity/{course}")
    public void  universityWork(@PathVariable("course") int course) throws Exception {
        universityResolver.getExecutor(EnumUniversityName.MARITIME_UNIVERSITY.getCode()).excute(EnumUniversityName.MARITIME_UNIVERSITY.getCode(),course);
    }


    /**
     * 华东师范大学
     * @throws Exception
     */
    @PostMapping("/EcnusoleUniversity/{course}")
    public void ecnusoleWork(@PathVariable("course") int course) throws Exception {
        universityResolver.getExecutor(EnumUniversityName.ECNUSOLE_UNIVERSITY.getCode()).excute(EnumUniversityName.ECNUSOLE_UNIVERSITY.getCode(),course);
    }

    /**
     * 国开旅游学院
     * http://www.ouchn.cn/
     */
    @PostMapping("/MaritimeUniversityJourney/{course}")
    public void MaritimeUniversityJourneyWork(@PathVariable("course") int course) throws Exception {
        universityResolver.getExecutor(EnumUniversityName.OPEN_UNIVERSITY_JOURNEY.getCode()).excute(EnumUniversityName.OPEN_UNIVERSITY_JOURNEY.getCode(),course);
    }

    /**
     * 上海大学
     * http://www.ouchn.cn/
     */
    @PostMapping("/ShUniversity/{course}")
    public void ShUniversityWork(@PathVariable("course") int course) throws Exception {
        universityResolver.getExecutor(EnumUniversityName.SH_UNIVERSITY.getCode()).excute(EnumUniversityName.SH_UNIVERSITY.getCode(),course);
    }

}
