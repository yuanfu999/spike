package com.cyf.spike.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 配置参数解析器
 * @Author chenyuanfu
 * @Date 2020/8/31 9:46
 **/
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private UserArgumentResolver userArgumentResolver;

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //super.addArgumentResolvers(userArgumentResolver);
        argumentResolvers.add(userArgumentResolver);
    }
}
