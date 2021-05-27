package com.auto.block;

import com.auto.common.exception.BizException;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestFile {
    public static void main(String[] args) throws Exception {

//        readFile();
        StringBuilder stringBuilder = readFile("src\\main\\files\\20210124_0.txt", 0);

        writeFile(stringBuilder,"src\\main\\files\\20210124_101.txt");
    }

    public static  StringBuilder readFile(String filePath,int skipLine){
        File file = new File(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        try (RandomAccessFile raf = new RandomAccessFile(file, "r");){
            raf.seek(0);
            String line;
            int i=0;
            while((line=raf.readLine())!=null){
                if(i>=skipLine) {
                    stringBuilder.append(line).append(System.lineSeparator());
                }
                i++;
            }
            System.out.println(i);
            System.out.println(stringBuilder);
            return  stringBuilder;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    public static void writeFile(StringBuilder stringBuilder,String fullPath) throws Exception {
         Path path = Paths.get(fullPath);
//        Files.deleteIfExists(path);
        Files.write(path,stringBuilder.toString().getBytes(),new StandardOpenOption[]{StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE});
    }





}
