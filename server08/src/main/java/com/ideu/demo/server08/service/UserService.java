package com.ideu.demo.server08.service;

import com.ideu.demo.server08.common.PageResult;
import com.ideu.demo.server08.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    boolean registerUser(User user);

    User login(String username, String password);

    int updateUser(User user);

    List<User> findAllUsers();

    User findByUsername(String username);

    User findById(Long id);

    int checkExistByUsername(String username);

    User findroleByUsername(String username);

     BigDecimal getBalance(Long userId);

     int subtractAccountByOrderId(String username, int balance);

     int getAccountByOrderId(String username, int balance);

    User getByUserName(String username);

    PageResult pageQuery(Integer pageNum, Integer pageSize, String userName, String phone, String region);

    void update(User user);

    void deleteByUserName(String userName);

    User getById(Long userId);
}
