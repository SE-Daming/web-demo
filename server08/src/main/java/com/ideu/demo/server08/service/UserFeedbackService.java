package com.ideu.demo.server08.service;

import com.ideu.demo.server08.entity.UserFeedback;
import java.util.List;

public interface UserFeedbackService {
    List<UserFeedback> findAll();

    UserFeedback save(UserFeedback userFeedback);

    void deleteById(Long id);
}
