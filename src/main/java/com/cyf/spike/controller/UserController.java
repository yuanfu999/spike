package com.cyf.spike.controller;

import com.cyf.spike.entity.SkUser;
import com.cyf.spike.result.Result;
import com.cyf.spike.service.SkUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * @Author chenyuanfu
 * @Date 2020/8/25 16:59
 **/
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private SkUserService userService;

    @GetMapping("get")
    public SkUser getUserById(@RequestParam("id") Integer userId){
        return userService.getUserById(userId);
    }

    @GetMapping("info")
    public Result<SkUser> info(Model model, SkUser user){
        return Result.success(user);
    }
}
