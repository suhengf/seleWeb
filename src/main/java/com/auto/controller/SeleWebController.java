package com.auto.controller;


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

    @PostMapping("/maritimeUniversity")
    public void  universityWork() throws Exception {
        maritimeUniversity.excute();
    }



}
