package com.ideu.demo.server08.mapper;

import com.github.pagehelper.Page;
import com.ideu.demo.server08.entity.User;
import org.apache.ibatis.annotations.*;

import javax.sql.RowSet;
import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM users WHERE phone = #{phone}")
    User findByPhone(String phone);

    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password}")
    User login(@Param("username") String username, @Param("password") String password);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    @Insert("INSERT INTO users(username, password, role, phone, address, region, balance,name) " +
            "VALUES(#{username}, #{password}, #{role}, #{phone}, #{address}, #{region}, #{balance},#{name})")
    void addUser(User user);

    @Update("UPDATE users" +
            " SET username = #{username}, " +
            "password = #{password}, " +
            "role = #{role}, " +
            "phone = #{phone}, " +
            "address = #{address}, " +
            "region = #{region}, " +
            "balance = #{balance} " +
            "WHERE id = #{id}")
    int update(User user);

    @Update("UPDATE users SET password = #{newPassword} WHERE phone = #{phone}")
    void updatePassword(@Param("phone") String phone, @Param("newPassword") String newPassword);

    @Select("SELECT COUNT(*) FROM users WHERE username = #{username}")
    int selectExistByUsername(String username);

    @Select("select * FROM users WHERE username = #{username}")
    User findroleByUsername(String username);

    BigDecimal getBalance(Long userId);

    @Select("SELECT * FROM users WHERE id = #{assignedEngineer}")
    User getById(Long assignedEngineer);

    @Update("UPDATE users SET balance = balance + #{balance} WHERE username = #{username}")
    void upupdateBalance(@org.springframework.data.repository.query.Param("balance") Integer balance, @org.springframework.data.repository.query.Param("username") String username);

    @Update("UPDATE users SET balance = balance - #{balance} WHERE username = #{username}")
    void downupdateBalance(@org.springframework.data.repository.query.Param("balance") Integer balance, @org.springframework.data.repository.query.Param("username") String username);

    @Select("SELECT balance FROM users WHERE username = #{username}")
    int getAccountByOrderId(@org.springframework.data.repository.query.Param("username") String username);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User getByUserName(String username);

    Page<User> pageQuery(String userName, String phone, String region);

    void update2(User user);

    void deleteByUA(String userName);

    void updateBalance(BigDecimal balance,Long userId);
}
