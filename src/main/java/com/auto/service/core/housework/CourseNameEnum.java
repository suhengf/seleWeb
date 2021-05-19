package com.auto.service.core.housework;

import com.auto.service.core.EnumUniversityName;
import lombok.Getter;

@Getter
public enum CourseNameEnum {

    ChineseCourse("大学语文","大学语文"),
    EnlishCourse("公共英语A","公共英语A"),
    MarxismCourse("思想道德与法治","思想道德与法治"),
    PreEducationCourse("学前教育学","学前教育学"),
    CumputerCourse("计算机入门","计算机入门"),;

    private String chineseName;
    private String desc;


    CourseNameEnum(String chineseName, String desc) {
        this.chineseName=chineseName;
        this.desc=desc;
    }

    public static CourseNameEnum getCourseName(String chineseName){
        if(chineseName==null){
            return null;
        }
        for (CourseNameEnum type: values()) {
            if(type.getChineseName().equals(chineseName)){
                return type;
            }
        }
        return null;
    }

}
