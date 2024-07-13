package com.ideu.demo.server08.entity;

import lombok.Data;

import java.util.Date;
@Data
public class OrderStatistic {

    private Date orderDate;
    private int orderCount;

    // 省略 getter 和 setter 方法
}
