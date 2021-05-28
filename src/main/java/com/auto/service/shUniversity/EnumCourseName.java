package com.auto.service.shUniversity;

import com.auto.service.core.EnumUniversityName;
import lombok.Getter;

@Getter
public enum EnumCourseName {

    yansgheng(1,"传统养生"),
    history(2,"中国近现代史纲要"),
    gonggongguanxi(3,"公关社交礼仪"),
    xingshi(4,"形势与政策实践"),;

    private int course;
    private String desc;


    EnumCourseName(int course, String desc) {
        this.course=course;
        this.desc=desc;
    }

    public static EnumCourseName getEnumCourseName(int  course){

        for (EnumCourseName type: values()) {
            if(type.getCourse()==course){
                return type;
            }
        }
        return null;
    }


}
