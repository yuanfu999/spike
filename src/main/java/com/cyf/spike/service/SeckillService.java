package com.cyf.spike.service;

import com.cyf.spike.entity.SkOrderInfo;
import com.cyf.spike.entity.SkUser;
import com.cyf.spike.vo.GoodsVo;

/**
 * @Author chenyuanfu
 * @Date 2020/9/3 15:25
 **/
public interface SeckillService {

    Boolean createSeckillOrder(SkUser user, GoodsVo goodsVo);

    long getSeckillResultByGoodsId(SkUser user, Long gooodsId);
}
