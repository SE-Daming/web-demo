package com.ideu.demo.server08.controller;

import cn.dev33.satoken.util.SaResult;
import com.ideu.demo.server08.common.BaseContext;
import com.ideu.demo.server08.common.PageResult;
import com.ideu.demo.server08.common.Result;
import com.ideu.demo.server08.entity.Engineer;
import com.ideu.demo.server08.entity.User;
import com.ideu.demo.server08.mapper.EngineerMapper;
import com.ideu.demo.server08.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 用户注册功能
     * 注册时用户需要填写的信息：用户名、密码、电话、地址、城市、身份证号、角色
     * @param user
     * @return
     */
    @Autowired
    EngineerMapper engineerMapper;
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result registerUser(@RequestBody User user) {
        User u=userService.getByUserName(user.getUsername());
        if(u!=null){
            return Result.error("注册失败!用户名已存在!");
        }
         userService.registerUser(user);
        User u2=userService.getByUserName(user.getUsername());
        if(user.getRole().equals("1")){
            Engineer engineer=new Engineer();
            engineer.setUserId(u2.getId());
            engineer.setQualification("初级工程师");
            engineer.setServiceArea(user.getRegion());
            engineer.setPerformanceRating("A");
            engineer.setName(user.getName());

            engineerMapper.save(engineer);
        }

            return Result.success("您已注册成功");
    }

//    /**
//     * 用户登录功能
//     * @param loginRequest
//     * @param request
//     * @return
//     */

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userService.login(username, password);
        if (user != null) {
            // 登录成功逻辑，
            String token = UUID.randomUUID().toString();
            request.getSession().setAttribute("userSession", token);
            redisTemplate.opsForValue().set("tjpu22s08:login:session:",user.toString());
            redisTemplate.opsForValue().set(token,String.valueOf(user.getId()));
            BaseContext.setCurrentId(user.getId());
            System.out.println(BaseContext.getCurrentId());
            System.out.println("线程1  "+Thread.currentThread().getName());
            //getRole
            Map map=new HashMap();
            map.put("role",user.getRole());
            map.put("token",token);
            return Result.success(map);
        } else {
            return Result.error("用户名或密码错误");
        }
    }


    /**
     * 修改密码功能
     * @param user
     * @return
     */
    @Operation(summary = "修改用户信息")
    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @PostMapping("deleteByUN/{userName}")
    public Result deleteByUN(@PathVariable("userName") String userName){
        if(StringUtils.isEmpty(userName)){
            return Result.error("请选择用户名");
        }
        userService.deleteByUserName(userName);
        return Result.success();
    }

    @PostMapping("test")
    public Result test(){
        return Result.success("测试通过");
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Operation(summary = "根据用户名查询用户")
    @GetMapping("/findUserByUsername/{username}")
    public Result<User> findUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return Result.success(user); // 返回成功，并携带用户数据
        } else {
            return Result.error("用户不存在"); // 返回错误消息
        }
    }

    /**
     * 根据用户ID查询用户
     * @param id
     * @return
     */
    @Operation(summary = "根据用户ID查询用户")
    @GetMapping("/findUserById")
    public Result<User> findUserById(HttpServletRequest request) {
        String k = request.getHeader("Authorization");
        String s = redisTemplate.opsForValue().get(k);
        Long id= Long.valueOf(s);
        System.out.println("线程2  "+Thread.currentThread().getName());
        User user = userService.findById(id);
        if (user != null) {
            return Result.success(user); // 返回成功，并携带用户数据
        } else {
            return Result.error("用户不存在"); // 返回错误消息
        }
    }

    /**
     * 查询所有用户
     * @return
     */
    @Operation(summary = "查询所有用户")
    @GetMapping("/findAllUsers")
    public Result<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();
        return Result.success(users); // 使用 Result.success 方法返回成功结果
    }

    /**
     * 根据用户名查询用户角色
     * @param username
     * @return
     */
    @Operation(summary = "根据用户名查询用户角色")
    @GetMapping("/findrole")
    public User findrole(String username) {
        return userService.findroleByUsername(username);
    }

    @Operation(summary = "查询用户余额")
    @GetMapping("/balance")
    public Result getBalance(HttpServletRequest request) {
        String k = request.getHeader("Authorization");
        Long userId= Long.valueOf(redisTemplate.opsForValue().get(k));
        if (userId == null) {
            userId=12l;
        }
        return Result.success(userService.getBalance(userId));
    }

    /**
     * 定义一个用于接收登录请求的内部类
     */
    @Data
    static class LoginRequest {
        private String username;
        private String password;
    }

    /**
     * 定义一个用于接收修改密码请求的内部类
     */
    @Data
    static class UserChangePasswordRequest {
        private String phone;
        private String idCard;
        private String newPassword;
    }
    @GetMapping("/in/{username}/{balance}")
    public int getAccountByOrderId(@PathVariable("username") String username, @PathVariable("balance") int balance)
    {
        return userService.getAccountByOrderId(username, balance);
    }
    @PostMapping("/in/{username}/{balance}")
    public int depositToAccount(
            @PathVariable("username") String username,
            @PathVariable("balance") int balance) {
        // Implement logic to update account balance by depositing `balance` amount
        return userService.getAccountByOrderId(username, balance);  // Return updated balance
    }
    @PostMapping("/out/{username}/{balance}")
    public int WithdrawalsToAccount(
            @PathVariable("username") String username,
            @PathVariable("balance") int balance) {
        // Implement logic to update account balance by depositing `balance` amount
        return userService.subtractAccountByOrderId(username, balance);  // Return updated balance
    }

    @GetMapping("pageQuery")
    public PageResult pageQuery(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                @RequestParam(required = false) String userName,
                                @RequestParam(required = false)String phone,
                                @RequestParam(required = false)String region){
        return userService.pageQuery(pageNum,pageSize,userName,phone,region);
    }

}
