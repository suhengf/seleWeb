package com.seleweb.study;

import com.seleweb.study.enums.SexEnum;
import lombok.Data;

@Data
public class User {
    /**性别*/
    private SexEnum sex;
//    private String sex;
    /**
     * 年龄
     */
    private int age;


}
