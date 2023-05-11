package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.Result;
import com.example.pojo.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登陆
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User one = userService.getOne(queryWrapper);
        if(one==null){
            return Result.error("该用户名不存在，请先注册");
        }
        if(user.getPassword().equals(one.getPassword())){
            one.setPassword(null);
            return Result.success(one);
        }else{
            return Result.error("登陆失败");
        }
    }

    /**
     * 注册
     */
    @PostMapping("/signUp")
    public Result<String> signUp(@RequestBody User user){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User one = userService.getOne(queryWrapper);
        if(one==null){
            userService.save(user);
            return Result.success("注册成功");
        }else{
            return Result.error("该用户名已存在");

        }
    }
}
