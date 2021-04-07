package com.auto.service.business.openUniversity.impl;

public class Test {
    public static void main(String[] args) {
        String str ="学习进度：0.58学时 / 60.00学时";
        String[] studySpeed = str.replace("学习进度：", "").replace("学时", "").split("/");
        String s = studySpeed[0].trim();
        String s1 = studySpeed[1].trim();
        System.out.println(s);
        System.out.println(s1);
    }

}
