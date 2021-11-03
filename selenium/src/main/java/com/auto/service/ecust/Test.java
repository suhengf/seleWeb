package com.auto.service.ecust;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
//        String str ="2:24:36";
//        String str ="74:36";
//        countSec(str);
//
//
//        //   /html/body/div/div/p/div[1]/iframe
//        //   /html/body/div/div/div[1]/iframe
//        String str2 = "/html/body/div/div/div[1]/iframe";
//        System.out.println(str2);
//        System.out.println(str2.substring(0,19)+"p/"+str2.substring(19));
//        System.out.println("/html/body/div/div/p/div[1]/iframe");
        //  /html/body/div/div/p/div[2]/iframe


        List<String> list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        List<String> list1 = new ArrayList();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        List<String> collect = list.stream().filter(e -> (!list1.contains(e))).collect(Collectors.toList());
        System.out.println(collect);


    }


    public static long countSec(String str) {
        if(str.length()>5){
            long hour = Long.parseLong(String.valueOf((str.indexOf(":")+1)))*60000*60000;
            long min = Integer.parseInt(str.substring(str.indexOf(":")+1,str.indexOf(":")+3))*60000;
            long sec = Integer.parseInt(str.substring(str.indexOf(":")+4))*1000;
            System.out.println(hour+min+sec);
            return hour+min+sec;
        }else{
            long min = Integer.parseInt(str.substring(0,str.indexOf(":")))*60000;
            long sec = Integer.parseInt(str.substring(str.indexOf(":")+1))*1000;
            System.out.println(min+sec);
            return min+sec;
        }
    }






}
