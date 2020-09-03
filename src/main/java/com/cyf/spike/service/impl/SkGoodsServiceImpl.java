package com.cyf.spike.service.impl;

import com.cyf.spike.Constants.Constant;
import com.cyf.spike.entity.SkGoodsSeckill;
import com.cyf.spike.vo.GoodsVo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.cyf.spike.mapper.SkGoodsMapper;
import com.cyf.spike.service.SkGoodsService;

import java.util.List;

@Service
public class SkGoodsServiceImpl implements SkGoodsService{

    @Resource
    private SkGoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> getGoodsVoList() {
        return goodsMapper.findGoodsVoList();
    }

    @Override
    public GoodsVo getGoodsDetailVoById(Long goodsId) {
        return goodsMapper.findGoodsVoById(goodsId);
    }

    @Override
    public GoodsVo getGoodsVoById(Long goodsId) {
        return goodsMapper.findGoodsVoById(goodsId);
    }

    /**
     * 根据版本控制字段防止库存超卖
     * @param goodsVo
     * @return
     */
    @Override
    public Boolean reduceStock(GoodsVo goodsVo) {
        SkGoodsSeckill goodsSeckill = new SkGoodsSeckill();
        goodsSeckill.setGoodsId(goodsVo.getId());
        goodsSeckill.setVersion(goodsVo.getVersion());
        int attemptNum = 0;
        int ret = 0;
        do {
            attemptNum ++;
            try {
                goodsSeckill.setVersion(goodsMapper.getVersionByGoodsId(goodsVo.getId()));
                ret = goodsMapper.reduceStockByVersion(goodsSeckill);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ret != 0){
                break;
            }
        }while (attemptNum < Constant.MAX_ATTEMPT_NUM);
        return ret > 0;
    }
}
