package com.ideu.demo.server08.service.impl;

import com.ideu.demo.server08.entity.UserFeedback;
import com.ideu.demo.server08.mapper.UserFeedbackMapper;
import com.ideu.demo.server08.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserFeedbackServiceImpl implements UserFeedbackService {

    @Autowired
    private UserFeedbackMapper userFeedbackMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserFeedback> findAll() {
        return userFeedbackMapper.findAll();
    }

    @Override
    @Transactional
    public UserFeedback save(UserFeedback userFeedback) {
        if (userFeedback.getId() == null) {
            userFeedbackMapper.save(userFeedback);
        } else {
            return null;
        }
        return userFeedback;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userFeedbackMapper.delete(id);
    }
}
