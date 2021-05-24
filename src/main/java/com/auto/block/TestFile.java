package com.auto.block;

import com.auto.common.exception.BizException;

import java.io.File;
import java.io.RandomAccessFile;

public class TestFile {
    public static void main(String[] args) {

        readFile();

    }

    public static  void readFile(){
        File file = new File("src\\main\\files\\20210124_0.txt");
        try (RandomAccessFile raf = new RandomAccessFile(file, "r");){
            raf.seek(0);
            String line;
            while((line=raf.readLine())!=null){
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }




}
