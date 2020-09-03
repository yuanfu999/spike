package com.cyf.spike.service;

import com.cyf.spike.entity.SkOrderInfo;

public interface SkOrderInfoService{


    SkOrderInfo getOrderDetailById(Long orderId);
}
