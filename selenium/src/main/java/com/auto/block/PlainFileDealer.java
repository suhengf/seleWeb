package com.auto.block;

import com.alibaba.fastjson.JSONObject;
import com.auto.common.exception.BizException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.List;

@Slf4j
public class PlainFileDealer {


    public static List<JobShared> nioFileSharding(String localFilePath,int fixNum){
         //读取文件
        File file = new File(localFilePath);
        if(file==null ||!file.exists()){
            log.info("本地路径{}下文件不存在",localFilePath);
            throw  new BizException("文件不存在");
        }
        List<JobShared> shardingBlockList = Lists.newArrayList();
        try (RandomAccessFile raf = new RandomAccessFile(file,"r")){
           long rafLength = raf.length();
            if (rafLength==0) {
                log.info("文件内容为空");
                return  Collections.emptyList();
            }

            long t1= System.currentTimeMillis();

            //获取RandomAccessFile 对象文件指针的位置  初始位置为0
            Long pointer = 0L;

            raf.seek(pointer);
            FileChannel fileChannel = raf.getChannel();
            ByteBuffer readByteBuffer = ByteBuffer.allocate(100000);

            //行号
            int lineNumber = 0;
            //最后一次分片游标记行号
            int lastMarkLineNum = 0;
            //最后一次分片游标记坐标
            long lastMarkPointer =0;
            // 分片流水
            int batchNum =0;

            final int LF =10;
            final int CR =13;

            byte []lineByte = new byte[0];
            byte []temp = new byte[0];
            while((fileChannel.read(readByteBuffer)!=-1)){
                int readSize = readByteBuffer.position();
                byte[] bs = new byte[readSize];
                readByteBuffer.rewind();
                readByteBuffer.get(bs);
                readByteBuffer.clear();
                int startNum =0;
                boolean hasLF = false;

                for(int i=0;i<readSize;i++){
                    if(bs[i]==LF||(rafLength-1L)==i){
                        hasLF= true;
                        int tempNum = temp.length;
                        int lineNum = i-startNum;
                        lineByte = new byte[tempNum+lineNum];
                        System.arraycopy(temp,0,lineByte,0,tempNum);
                        temp = new byte[0];
                        System.arraycopy(bs,startNum,lineByte,tempNum,lineNum);
                        if(lineNumber % fixNum==0){
                            lastMarkLineNum = lineNumber;
                            lastMarkPointer=pointer;
                            FileLineData fileLineData = new FileLineData();
                            fileLineData.setLineNum(lastMarkLineNum+1);
                            fileLineData.setPointer(lastMarkPointer);
                            fileLineData.setNextLineNum(fixNum);
                            fileLineData.setFileName(file.getName());
                            //  流水号批次号叠加
                            batchNum++;
                             JobShared shardingBlock = new JobShared();
                            shardingBlock.setBatchSerialNo("1");
                            shardingBlock.setInPara("1");
                            shardingBlock.setBatchNum(batchNum);
                            shardingBlock.setOffset(lastMarkLineNum+1);
                            shardingBlock.setFixNum(1);
                            shardingBlock.setLimit(fileLineData.getNextLineNum());
                            shardingBlock.setInfimumValue(pointer.toString());
                            shardingBlock.setBusinessDate(1);
                            //将原始查询条件 赋值倒额外参数
                            shardingBlock.setExtParam(JSONObject.toJSONString(fileLineData));
                            shardingBlockList.add(shardingBlock);
                        }
                    //游标前移
                    pointer=raf.getFilePointer()-readSize+i+1;
                    //当前行
                      ++lineNumber;

                        if ((i+1<readSize && bs[i+1]==CR)) {
                            startNum = i+2;
                            pointer++;
                        }else{
                            startNum =i+1;
                        }
                    }
                }

                if(hasLF){
                    temp = new byte[bs.length-startNum];
                    System.arraycopy(bs,startNum,temp,0,temp.length);
                }else{
                    byte[]toTemp= new byte[temp.length+bs.length];
                    System.arraycopy(temp,0,toTemp,0,temp.length);
                    System.arraycopy(bs,0,toTemp,temp.length,bs.length);
                    temp=toTemp;
                }
            }

            if(CollectionUtils.isNotEmpty(shardingBlockList)){
                int nextLineNum= lineNumber-lastMarkLineNum+1;
                FileLineData fileLineData = JSONObject.parseObject(shardingBlockList.get(shardingBlockList.size()-1).getExtParam(),FileLineData.class);
                fileLineData.setPointer(lastMarkPointer);
                fileLineData.setNextLineNum(nextLineNum);


                JobShared jobShared= shardingBlockList.get(shardingBlockList.size()-1);
                jobShared.setExtParam(JSONObject.toJSONString(fileLineData));
                jobShared.setLimit(nextLineNum);
            }
            log.info("文件分片数:{}",shardingBlockList.size());
            log.info("文件分片总耗时:{}",(System.currentTimeMillis()-t1));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shardingBlockList;
    }



    public static List<String> nioShardingRead(JobShared jobShared,String localFilePath){
        List<String> shardingResult = Lists.newArrayList();
         File file = new File(localFilePath);
        FileLineData fileLineData =  JSONObject.parseObject(jobShared.getExtParam(),FileLineData.class);

        if(file==null ||!file.exists()){
            log.info("本地路径{}下文件不存在",localFilePath+" ,从"+jobShared.getOffset()+"行开始," +
                    "游标位置为"+fileLineData.getPointer()+",往后读取"+fileLineData.getNextLineNum()+"行数据处理");
            throw  new BizException("文件不存在");
        }

        log.info("开始读取文件 "+localFilePath+" 从"+jobShared.getOffset()+"行开始," +
                "游标位置为"+fileLineData.getPointer()+",往后读取"+fileLineData.getNextLineNum()+"行数据处理");
        List<String> creditSliceBatchLogDoList = Lists.newArrayList();
        try (RandomAccessFile raf = new RandomAccessFile(file, "r");){
            long pointer = fileLineData.getPointer();
            int nextLineNum = fileLineData.getNextLineNum();
            raf.seek(pointer);
            //行内容
            String line;

            while((line=raf.readLine())!=null){
                //文本转为utf-8
                creditSliceBatchLogDoList.add(new String(line.getBytes("ISO-8859-1"),"UTF-8"));
                //游标前移
                pointer = raf.getFilePointer();
                if(--nextLineNum<=0){
                    break;
                }


            }

        } catch (Exception e) {
            throw  new BizException("分片信息异常"+e.getMessage());
        }

        shardingResult.addAll(creditSliceBatchLogDoList);
        return shardingResult;
    }


}
