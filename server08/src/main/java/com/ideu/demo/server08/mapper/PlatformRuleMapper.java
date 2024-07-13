package com.ideu.demo.server08.mapper;

import com.ideu.demo.server08.entity.PlatformRule;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlatformRuleMapper {

    @Insert("INSERT INTO platform_rules(title, content, start_date, end_date, created_time) " +
            "VALUES(#{title}, #{content}, #{startDate}, #{endDate}, #{createdTime})")
    void save(PlatformRule platformRule);

    @Delete("DELETE FROM platform_rules WHERE id = #{ruleId}")
    void delete(Long ruleId);

    @Select("SELECT * FROM platform_rules")
    List<PlatformRule> findAll();
}
