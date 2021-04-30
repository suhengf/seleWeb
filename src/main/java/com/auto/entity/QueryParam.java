package com.auto.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:39
 */
@Data
@Builder
public class QueryParam {
    private Integer fixNum;
    private String userName;
    private Integer id;
}
