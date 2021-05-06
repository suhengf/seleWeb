package com.auto.block;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ReadFileTest {

    private static  final  String FILE_PATH ="src\\main\\files\\20210124_0.txt";
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(200));

    @Test
    public void TestTwo(){
        List<ShardingBlock> shardingBlockList = PlainFileHandler.nioFileSharding(FILE_PATH,3);
        shardingBlockList.forEach(shardingBlock -> {
            List<String>shardInfo=  PlainFileHandler.nioShardingRead(shardingBlock.getPointer(),shardingBlock.getLength(), FILE_PATH);
            shardInfo.forEach(System.out::println);

        });
    }

    @Test
    public void TestReadFile(){
        List<JobShared> jobSharedList = PlainFileDealer.nioFileSharding(FILE_PATH,8);
        jobSharedList.forEach(shared -> {
            List<String>shardInfo=  PlainFileDealer.nioShardingRead(shared, FILE_PATH);
            shardInfo.forEach(System.out::println);

        });
    }


    @Test
    public void TestThreadReadFile(){
        List<JobShared> jobSharedList = PlainFileDealer.nioFileSharding(FILE_PATH,3);
        jobSharedList.forEach(shared -> {
            List<String>shardInfo=  PlainFileDealer.nioShardingRead(shared, FILE_PATH);
            shardInfo.forEach(System.out::println);

        });

    }




}
