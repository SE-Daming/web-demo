package com.ideu.demo.server08.mapper;

import com.ideu.demo.server08.entity.ComRate;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComRateMapper {

    @Insert("INSERT INTO commission_rates(order_type, commission_rate) " +
            "VALUES(#{orderType}, #{commissionRate})")
    void save(ComRate comRate);

    @Delete("DELETE FROM commission_rates WHERE id = #{id}")
    void deleteById(Long id);

    @Select("SELECT id, order_type AS orderType, commission_rate AS commissionRate " +
            "FROM commission_rates")
    List<ComRate> findAll();
}
