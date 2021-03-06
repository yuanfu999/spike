package com.cyf.spike.service.impl;

import com.cyf.spike.Constants.KeyConstant;
import com.cyf.spike.Constants.PrefixContant;
import com.cyf.spike.entity.SkOrder;
import com.cyf.spike.entity.SkOrderInfo;
import com.cyf.spike.entity.SkUser;
import com.cyf.spike.service.SeckillService;
import com.cyf.spike.service.SkGoodsService;
import com.cyf.spike.service.SkOrderService;
import com.cyf.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author chenyuanfu
 * @Date 2020/9/3 15:25
 **/
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SkGoodsService goodsService;

    @Autowired
    private SkOrderService orderService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    @Transactional
    public Boolean createSeckillOrder(SkUser user, GoodsVo goodsVo) {
        //减少库存
        Boolean boo = goodsService.reduceStock(goodsVo);
        if (boo){
            //下订单
            return orderService.createOrder(user, goodsVo);
        }else{
            setOverStatus(goodsVo.getId());
            return false;
        }
    }

    @Override
    public long getSeckillResultByGoodsId(SkUser user, Long gooodsId) {
        SkOrder order = orderService.getOrderByUserIdAndGoodsId(user.getId(), gooodsId);
        if (order != null){
            return order.getOrderId();
        }else{
            if (getOverStatus(gooodsId)){
                return -1;
            }else{
                return 0;
            }
        }
    }

    public void setOverStatus(Long goodsId){
        redisTemplate.opsForValue().set(PrefixContant.SECKILL_GOODS_OVER_PREFIX + goodsId, true);
    }

    public Boolean getOverStatus(Long goodsId){
        return null != redisTemplate.opsForValue().get(PrefixContant.SECKILL_GOODS_OVER_PREFIX + goodsId);
    }
}
