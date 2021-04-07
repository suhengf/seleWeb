package com.auto.controller;


import com.auto.service.business.ecnusole.Ecnusole;
import com.auto.service.business.openUniversity.Guarantee;
import com.auto.service.business.shanghaimaritimeuniversity.IMaritimeUniversity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auto")
public class SeleWebController {


    @Autowired
    private IMaritimeUniversity maritimeUniversity;

    @Autowired
    private Ecnusole ecnusole;

    @Autowired
    private Guarantee guarantee;


    /**
     * 上海海事大学
     * @throws Exception
     */
    @PostMapping("/guarantee")
    public void  openUniversityWork() throws Exception {
        guarantee.excute();
    }


    /**
     * 上海海事大学
     * @throws Exception
     */
    @PostMapping("/maritimeUniversity")
    public void  universityWork() throws Exception {
        maritimeUniversity.excute();
    }


    /**
     * 华东师范大学
     * @throws Exception
     */
    @PostMapping("/ecnusole")
    public void ecnusoleWork() throws Exception {
        ecnusole.excute();
    }


}
