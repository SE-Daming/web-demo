package com.ideu.demo.server08.mapper;

import com.ideu.demo.server08.entity.Earnings;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EarningsMapper {

    @Insert("INSERT INTO earnings(order_id, platform_fee, engineer_fee, total_amount, earning_time) " +
            "VALUES(#{orderId}, #{platformFee}, #{engineerFee}, #{totalAmount}, #{earningTime})")
    void save(Earnings earnings);

    @Select("SELECT id, order_id AS orderId, platform_fee AS platformFee, " +
            "engineer_fee AS engineerFee, total_amount AS totalAmount, earning_time AS earningTime " +
            "FROM earnings WHERE id = #{id}")
    Earnings findById(Long id);

    @Select("SELECT id, order_id AS orderId, platform_fee AS platformFee, " +
            "engineer_fee AS engineerFee, total_amount AS totalAmount, earning_time AS earningTime " +
            "FROM earnings")
    List<Earnings> findAll();

    @Select("SELECT id, order_id AS orderId, platform_fee AS platformFee, " +
            "engineer_fee AS engineerFee, total_amount AS totalAmount, earning_time AS earningTime " +
            "FROM earnings LIMIT #{pageSize} OFFSET #{offset}")
    List<Earnings> findAllPaged(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("SELECT COUNT(*) FROM earnings")
    long countAll();

    @Delete("DELETE FROM earnings WHERE id = #{id}")
    void deleteById(Long id);

    @Delete("<script>" +
            "DELETE FROM earnings WHERE id IN " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    void deleteBatch(List<Long> ids);

    @Select("SELECT id, order_id AS orderId, platform_fee AS platformFee, " +
            "engineer_fee AS engineerFee, total_amount AS totalAmount, earning_time AS earningTime " +
            "FROM earnings WHERE order_id = #{orderId}")
    Earnings findByOrderId(Long orderId);

    // 其他自定义查询方法可以根据需要在这里添加
}
