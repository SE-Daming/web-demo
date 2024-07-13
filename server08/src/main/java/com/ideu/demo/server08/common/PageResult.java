package com.ideu.demo.server08.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    private long total; // 总记录数

    private List<T> records; // 当前页数据集合

    private int pageNo; // 当前页码

    private int pageSize; // 每页数量

    // 可选的构造函数
    public PageResult(List<T> records, long total) {
        this.records = records;
        this.total = total;
    }

    // 可选的构造函数
    public PageResult(List<T> records, long total, int pageNo, int pageSize) {
        this.records = records;
        this.total = total;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
