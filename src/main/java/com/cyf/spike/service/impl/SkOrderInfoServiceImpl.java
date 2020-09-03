package com.cyf.spike.service.impl;

import com.cyf.spike.entity.SkOrderInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.cyf.spike.mapper.SkOrderInfoMapper;
import com.cyf.spike.service.SkOrderInfoService;
@Service
public class SkOrderInfoServiceImpl implements SkOrderInfoService{

    @Resource
    private SkOrderInfoMapper orderInfoMapper;

    @Override
    public SkOrderInfo getOrderDetailById(Long orderId) {
        return orderInfoMapper.selectByPrimaryKey(orderId);
    }
}
