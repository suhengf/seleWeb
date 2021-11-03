package com.auto.service.core.campus.component;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConvertEntity {
    //转化的异常
    private boolean flag;

    //转化后的int值
    private int todoInt;



}
