package com.auto.block;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileLineData implements Serializable {

    /**
     * 文件名
     */
    private String  fileName;
    /**
     * 文件游标
     */
    private Long pointer;


    /**
     * 文件行号
     */
    private int lineNum;

    /**
     * 文件内容
     */
    private String content;

    /**
     * 游标后的行数
     */
    private int nextLineNum;

}
