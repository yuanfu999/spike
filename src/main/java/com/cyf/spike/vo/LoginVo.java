package com.cyf.spike.vo;

import com.cyf.spike.Validate.IsMobile;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author chenyuanfu
 * @Date 2020/9/1 14:52
 **/
@Data
public class LoginVo {

    @NotNull
    @IsMobile  //因为框架没有校验手机格式注解，所以自己定义
    private String mobile;

    @NotNull
    private String password;
}
