package com.auto.verify;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Data
public class VerifycationTask {

    private String maxTime;

    private String seqNo;

    private String verifyId;

    private String verifyType;

    private BigDecimal verifyTime;

    private Date updateTime;

    private String status;

    private String  createTime;


}
