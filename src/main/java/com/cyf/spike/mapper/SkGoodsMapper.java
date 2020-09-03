package com.cyf.spike.mapper;

import com.cyf.spike.entity.SkGoods;
import com.cyf.spike.entity.SkGoodsSeckill;
import com.cyf.spike.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkGoodsMapper extends Mapper<SkGoods> {

    List<GoodsVo> findGoodsVoList();

    GoodsVo findGoodsVoById(@Param("goodsId") Long goodsId);

    Integer getVersionByGoodsId(@Param("goodsId") Long goodsId);

    Integer reduceStockByVersion(SkGoodsSeckill goodsSeckill);
}