package com.ideu.demo.server08.mapper;

import com.ideu.demo.server08.entity.Engineer;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EngineerMapper {

    @Select("SELECT id, user_id AS userId, qualification, service_area AS serviceArea, " +
            "performance_rating AS performanceRating, types " +
            "FROM engineers WHERE id = #{id}")
    Engineer findById(Long id);

    @Insert("INSERT INTO engineers(user_id, qualification, service_area, performance_rating, types,name) " +
            "VALUES(#{userId}, #{qualification}, #{serviceArea}, #{performanceRating}, #{types},#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Engineer engineer);

   

    @Delete("DELETE FROM engineers WHERE id = #{id}")
    void delete(Long id);

    @Select("SELECT id, user_id AS userId, qualification, service_area AS serviceArea, " +
            "performance_rating AS performanceRating, types " +
            "FROM engineers")
    List<Engineer> findAll();

    @Update("UPDATE engineers SET performance_rating = #{performanceRating} WHERE id = #{id}")
    void updatePerformanceRating(@Param("id") Long id, @Param("performanceRating") Integer performanceRating);

    @Update("UPDATE engineers SET service_area = #{serviceArea} WHERE id = #{id}")
    void updateServiceArea(@Param("id") Long id, @Param("serviceArea") String serviceArea);

    @Update("UPDATE engineers SET types = #{types} WHERE id = #{id}")
    void updateTypes(@Param("id") Long id, @Param("types") String types);

    @Select("<script>" +
            "SELECT id, user_id AS userId, qualification, service_area AS serviceArea, " +
            "performance_rating AS performanceRating, types " +
            "FROM engineers " +
            "<where> " +
            "<if test='serviceArea != null and serviceArea != \"\"'> " +
            "AND service_area = #{serviceArea} " +
            "</if> " +
            "<if test='types != null and types != \"\"'> " +
            "AND types = #{types} " +
            "</if> " +
            "</where> " +
            "LIMIT #{pageSize} OFFSET #{pageNum}" +
            "</script>")
    List<Engineer> pageQuery(@Param("pageNum") String  pageNum,
                             @Param("pageSize") String  pageSize,
                             @Param("serviceArea") String serviceArea,
                             @Param("types") String types);

    @Select("SELECT * FROM engineers WHERE id = #{assignedEngineer}")
    Engineer getById(Long assignedEngineer);

    List<Engineer> pageQuery2(String serviceArea, String name);

    void update(Engineer engineer);
}
