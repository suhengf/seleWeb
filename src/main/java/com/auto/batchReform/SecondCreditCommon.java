package com.auto.batchReform;

/**
 * 二代征信批量任务
 */
public abstract class SecondCreditCommon {

    public void handle(){
        //查询数据 生成dat文件
        generateDatFile();
        //上传oss文件

    }

    //查询数据 生成dat文件
  public   abstract  String generateDatFile();






}
