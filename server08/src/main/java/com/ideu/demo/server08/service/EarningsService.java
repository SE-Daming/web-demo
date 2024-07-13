package com.ideu.demo.server08.service;

import com.ideu.demo.server08.entity.Earnings;

import java.util.List;
import java.util.Optional;

public interface EarningsService {

    /**
     * 保存收益记录
     * @param earnings 收益记录对象
     * @return 保存后的收益记录对象
     */
    Earnings saveEarnings(Earnings earnings);

    /**
     * 根据ID查询收益记录
     * @param id 收益记录ID
     * @return 包含收益记录的Optional对象，如果不存在则为空
     */
    Optional<Earnings> getEarningsById(Long id);

    /**
     * 查询所有收益记录
     * @return 收益记录列表
     */
    List<Earnings> findAll();

    /**
     * 删除收益记录
     * @param id 收益记录ID
     */
    void deleteEarnings(Long id);

    /**
     * 分页查询收益记录
     * @param pageNo 当前页码
     * @param pageSize 每页数量
     * @return 当前页的收益记录列表
     */
    List<Earnings> findAllPaged(int pageNo, int pageSize);

    /**
     * 统计收益记录总数
     * @return 收益记录总数
     */
    long countAll();

    /**
     * 根据条件查询收益记录
     * @param condition 查询条件
     * @return 符合条件的收益记录列表
     */

    /**
     * 批量删除收益记录
     * @param ids 收益记录ID列表
     */
    void deleteBatch(List<Long> ids);

    // 其他自定义查询方法可以根据需要在这里添加
}
