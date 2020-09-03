package com.cyf.spike.service.impl;

import com.cyf.spike.entity.SkOrder;
import com.cyf.spike.entity.SkOrderInfo;
import com.cyf.spike.entity.SkUser;
import com.cyf.spike.mapper.SkOrderInfoMapper;
import com.cyf.spike.mapper.SkOrderMapper;
import com.cyf.spike.service.SkOrderService;
import com.cyf.spike.vo.GoodsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SkOrderServiceImpl implements SkOrderService{

    @Resource
    private SkOrderMapper orderMapper;

    @Autowired
    private SkOrderInfoMapper orderInfoMapper;

    @Override
    public SkOrder getOrderByUserIdAndGoodsId(Long userId, Long goodsId) {
        Example example = new Example(SkOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId).andEqualTo("goodsId", goodsId);
        return orderMapper.selectOneByExample(example);
    }

    @Override
    public Boolean createOrder(SkUser user, GoodsVo goodsVo) {
        SkOrderInfo orderInfo = new SkOrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getGoodsPrice());
        orderInfo.setOrderChannel((byte)1);
        orderInfo.setStatus((byte)0);
        orderInfo.setUserId(user.getId());
        int ret1 = orderInfoMapper.insertSelective(orderInfo);

        SkOrder seckillOrder = new SkOrder();
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrder.setOrderId(orderInfo.getId());
        seckillOrder.setUserId(user.getId());
        int ret2 = orderMapper.insertSelective(seckillOrder);
        return (ret1 > 0 && ret2 > 0);
    }
}
