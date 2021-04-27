package com.auto.service.abstr;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ThreadPoolParam {
    //核心线程数
    private int corePoolSize;
    //最大线程数
    private int maximumPoolSize;

}
