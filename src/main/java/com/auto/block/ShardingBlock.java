package com.auto.block;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShardingBlock {

    /**
     * 当前分片索引
     */
    private int bolckIndex;

    /**
     *当前分片偏移量(起始行)
     */
    private int startNumber;
    /**
     * 当前分片总行数
      */
    private int endNumber;
    /**
     * 当前分片总行数
     */
    private int length;
    /**
     * 文件游标
     */
    private Long pointer;

    private String extParam;


}
