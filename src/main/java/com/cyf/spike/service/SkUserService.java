package com.cyf.spike.service;

import com.cyf.spike.entity.SkUser;
import com.cyf.spike.vo.LoginVo;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

public interface SkUserService{



    SkUser getUserById(Integer userId);

    SkUser getUserByToken(HttpServletResponse response, String token);

    @Transactional
    String login(HttpServletResponse response, LoginVo loginVo);
}
