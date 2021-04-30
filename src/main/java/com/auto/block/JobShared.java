package com.auto.block;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class JobShared implements Serializable {

    /**
     * 批次号
     */
    private Integer batchNum;

    /**
     * 固定分片数量
     */
    private Integer fixNum;

    /**
     * 其实位置 相当于 sql中的limit m,n  中的m
     */
    private Integer offset;

    /**
     * 需要查询的条数
     */
    private Integer limit;

    /**
     * 当前分片上确界值
     */
    private String infimumValue;

    /**
     * 当前分片上却戒指
     */
    private String supermumValue;


    /**
     * 批次总流水
     */
    private String batchSerialNo;

    /**
     * 扩展参数
     */
    private String extParam;

    /**
     * 分片job的 任务参数
     */
    private String inPara;


    /**
     * 分片固定值初始化 业务时间
     */
    private Integer businessDate;

    public JobShared(){

    }

    public JobShared(Integer total,int step ,
                        Integer businessDate,Integer batchNum,
                        Integer fixNum,String infimumValue,
                        String supermumValue,String batchSerialNo,String inPara){
            this.businessDate=businessDate;
            this.batchSerialNo=batchSerialNo;
            this.inPara=inPara;
            this.batchNum=batchNum;
            this.fixNum=fixNum;
            this.infimumValue=infimumValue;
            this.supermumValue=supermumValue;
            this.offset = step*fixNum;
            this.limit=(step+1)*fixNum >total?(total-step*fixNum):fixNum;
    }


}
