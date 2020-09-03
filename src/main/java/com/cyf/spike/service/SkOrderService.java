package com.cyf.spike.service;

import com.cyf.spike.entity.SkOrder;
import com.cyf.spike.entity.SkOrderInfo;
import com.cyf.spike.entity.SkUser;
import com.cyf.spike.vo.GoodsVo;

public interface SkOrderService{


    SkOrder getOrderByUserIdAndGoodsId(Long userId, Long goodsId);

    Boolean createOrder(SkUser user, GoodsVo goodsVo);
}
