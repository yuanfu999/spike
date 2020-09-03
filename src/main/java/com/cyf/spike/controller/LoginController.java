package com.cyf.spike.controller;

import com.cyf.spike.result.Result;
import com.cyf.spike.service.SkUserService;
import com.cyf.spike.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author chenyuanfu
 * @Date 2020/8/31 17:57
 **/
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private SkUserService userService;

    @GetMapping(value = { "/","login"})
    public String index(){
        return "login";
    }

    @PostMapping(value = {"doLogin"})
    @ResponseBody
    public Result<String> login(HttpServletResponse response, @Valid LoginVo loginVo){
        log.info("login userInfo: {}", loginVo);
        String token = userService.login(response, loginVo);
        return Result.success(token);
    }

}
