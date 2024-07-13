package com.ideu.demo.server08.mapper;

import com.ideu.demo.server08.entity.UserFeedback;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserFeedbackMapper {

    @Insert("INSERT INTO user_feedback(user_id, feedback_type, feedback_text, feedback_time) " +
            "VALUES(#{userId}, #{feedbackType}, #{feedbackText}, #{feedbackTime})")
    void save(UserFeedback userFeedback);

    @Delete("DELETE FROM user_feedback WHERE id = #{id}")
    void delete(Long id);

    @Select("SELECT * FROM user_feedback")
    List<UserFeedback> findAll();
}
