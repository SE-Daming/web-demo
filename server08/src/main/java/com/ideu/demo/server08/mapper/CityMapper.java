package com.ideu.demo.server08.mapper;

import com.ideu.demo.server08.common.Tree;
import com.ideu.demo.server08.entity.City;
import org.apache.ibatis.annotations.*;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

@Mapper
public interface CityMapper {


    @Select("SELECT * FROM city WHERE pid = 1")
    List<City> getProvince();
    @Select("SELECT * FROM city WHERE pid = #{pid}")
    List<City> getArea(@Param("pid") Long pid);
    // 查询所有城市数据，返回树形结构
    @Select("SELECT * FROM city ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "pid", column = "pid"),
            @Result(property = "cityName", column = "cityname"),
            @Result(property = "type", column = "type"),
            @Result(property = "children", javaType = List.class, column = "id", many = @Many(select = "getCitiesByPid"))
    })
    List<City> getTree();

    // 根据 id 查询城市信息
    @Select("SELECT * FROM city WHERE id = #{id}")
    City getCityById(@Param("id") Long id);

    // 根据 pid 查询子节点城市信息
    @Select("SELECT * FROM city WHERE pid = #{pid}")
    List<City> getCitiesByPid(@Param("pid") Long pid);
}
