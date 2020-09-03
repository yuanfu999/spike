package com.cyf.spike.config;

import com.cyf.spike.Constants.Constant;
import com.cyf.spike.Constants.KeyConstant;
import com.cyf.spike.entity.SkUser;
import com.cyf.spike.service.SkUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 将客户端传过来的token解析成user
 *
 * @Author chenyuanfu
 * @Date 2020/8/31 9:52
 **/
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private SkUserService userService;

    /**
     * 判断参数类型是否是User,只有当类型为User时才做处理
     *
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == SkUser.class;
    }


    /**
     * 通过客户端传过来的token作为key去redis中换取用户信息
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String requestToken = request.getParameter(KeyConstant.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, KeyConstant.COOKIE_NAME_TOKEN);
        if (requestToken == null && cookieToken == null){
            return null;
        }
        String token = StringUtils.isEmpty(requestToken) ? cookieToken : requestToken;
        return userService.getUserByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null && cookies.length <= 0){
            return null;
        }
        for (Cookie cookie : cookies){
            if (cookieName.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
