package com.cyf.spike.service;

import com.cyf.spike.vo.GoodsVo;

import java.util.List;

public interface SkGoodsService{


    List<GoodsVo> getGoodsVoList();

    GoodsVo getGoodsDetailVoById(Long goodsId);

    GoodsVo getGoodsVoById(Long goodsId);

    Boolean reduceStock(GoodsVo goodsVo);
}
