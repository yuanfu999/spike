package com.cyf.spike.service.impl;

import com.cyf.spike.Constants.Constant;
import com.cyf.spike.Constants.KeyConstant;
import com.cyf.spike.Constants.PrefixContant;
import com.cyf.spike.entity.SkUser;
import com.cyf.spike.exception.GlobalException;
import com.cyf.spike.result.CodeMsg;
import com.cyf.spike.utils.MD5Util;
import com.cyf.spike.utils.UUIDUtil;
import com.cyf.spike.vo.LoginVo;
import org.apache.catalina.User;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.cyf.spike.mapper.SkUserMapper;
import com.cyf.spike.service.SkUserService;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class SkUserServiceImpl implements SkUserService{

    @Resource
    private SkUserMapper userMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public SkUser getUserById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public SkUser getUserByToken(HttpServletResponse response, String token) {
        String redisToken = PrefixContant.USER_TOKEN_PREFIX + token;
        SkUser user = (SkUser) redisTemplate.opsForValue().get(redisToken);
        if (user != null){
            addCookie(response, token, user);
            return user;
        }
        return null;
    }

    @Override
    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        Example example = new Example(SkUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile", loginVo.getMobile());
        SkUser user = userMapper.selectOneByExample(example);
        String token = "";
        if(user != null){
            String formPass = MD5Util.formPassToDBPass(loginVo.getPassword(), user.getSalt());
            if (!formPass.equals(user.getPassword())){
                throw new GlobalException(CodeMsg.PASSWORD_ERROR);
            }
            user.setLastLoginDate(new Date());
            user.setLoginCount(user.getLoginCount() + 1);
            userMapper.updateByPrimaryKey(user);
            token = UUIDUtil.uuid();
            addCookie(response, token, user);
        }
        return token;
    }

    private void addCookie(HttpServletResponse response, String token, SkUser user) {
        redisTemplate.opsForValue().set(PrefixContant.USER_TOKEN_PREFIX + token, user, Constant.TOKEN_EXPIRE, TimeUnit.SECONDS);
        Cookie cookie = new Cookie(KeyConstant.COOKIE_NAME_TOKEN, token);
        cookie.setPath("/");
        cookie.setMaxAge(Constant.TOKEN_EXPIRE);
        response.addCookie(cookie);
    }
}
