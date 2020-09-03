package com.cyf.spike.Validate;

import com.cyf.spike.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义手机号码格式校验器
 * @Author chenyuanfu
 * @Date 2020/9/1 14:54
 **/
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private Boolean required = false;

    @Override
    public void initialize(IsMobile isMobile) {
        this.required = isMobile.required();
    }

    @Override
    public boolean isValid(String mobile, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isMobile(mobile);
        }else{
            if (StringUtils.isBlank(mobile)){
                return true;
            }else{
                return ValidatorUtil.isMobile(mobile);
            }
        }
    }
}
