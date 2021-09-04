package com.auto.proxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealService {

    public void realMethod(){
        log.info("real method execute");
    }


}
