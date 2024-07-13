package com.ideu.demo.server08.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ideu.demo.server08.common.PageResult;
import com.ideu.demo.server08.entity.User;
import com.ideu.demo.server08.mapper.UserMapper;
import com.ideu.demo.server08.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

        @Autowired
        private UserMapper userMapper;

        @Override
        public boolean registerUser(User user) {
                user.setBalance(BigDecimal.valueOf(0));
                // 假设userMapper将用户插入数据库
                try {
                        userMapper.addUser(user);
                        return true;
                } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                }
        }

        @Override
        public User login(String username, String password) {
                User user = userMapper.login(username, password);
                if (user != null && user.getPassword().equals(password)) {
                        return user;
                }
                return null; // 登录失败
        }


        @Override
        public int updateUser(User user) {
           User updateUser  = userMapper.findById(user.getId());

           if (updateUser != null){
                   if (user.getPassword() != null)
                           updateUser.setPassword(user.getPassword());
                   if (user.getUsername() != null)
                           updateUser.setUsername(user.getUsername());
                   if (user.getPhone() != null)
                           updateUser.setPhone(user.getPhone());
                   if (user.getAddress() != null)
                           updateUser.setAddress(user.getAddress());
                   if (user.getRegion() != null)
                           updateUser.setRegion(user.getRegion());

           }
           return userMapper.update(updateUser);
        }

        @Override
        public List<User> findAllUsers() {
                return userMapper.findAll();
        }

        @Override
        public User findByUsername(String username) {
                return userMapper.findByUsername(username);
        }


        @Override
        public User findById(Long id) {
                return userMapper.findById(id);
        }

        @Override
        public int checkExistByUsername(String username) {
                return userMapper.selectExistByUsername(username);
        }

        @Override
        public User findroleByUsername(String username) {
                return userMapper.findroleByUsername(username);
        }

        @Override
        public BigDecimal getBalance(Long userId) {
                return userMapper.getBalance(userId);
        }

        @Override
        public int getAccountByOrderId(String username, int balance){
                userMapper.upupdateBalance(balance,username);
                return userMapper.getAccountByOrderId(username);
        }
        @Override
        public int subtractAccountByOrderId(String username, int balance){
                userMapper.downupdateBalance(balance,username);
                return userMapper.getAccountByOrderId(username);
        }

        @Override
        public User getByUserName(String username) {
                return userMapper.getByUserName(username);
        }

        @Override
        public PageResult pageQuery(Integer pageNum, Integer pageSize, String userName, String phone, String region) {
                PageHelper.startPage(pageNum, pageSize);
               List<User>page=userMapper.pageQuery(userName,phone,region);
                PageInfo<User>pageInfo=new PageInfo<>(page);
                return new PageResult(pageInfo.getList(),pageInfo.getTotal());
        }

        @Override
        public void update(User user) {
                userMapper.update2(user);
        }

        @Override
        public void deleteByUserName(String userName) {
                userMapper.deleteByUA(userName);
        }

        @Override
        public User getById(Long userId) {
                return userMapper.getById(userId);
        }
}
