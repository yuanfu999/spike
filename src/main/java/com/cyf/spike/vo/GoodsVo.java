package com.cyf.spike.vo;

import com.cyf.spike.entity.SkGoods;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author chenyuanfu
 * @Date 2020/8/31 16:26
 **/
@Data
public class GoodsVo extends SkGoods implements Serializable {

    private Double seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private Integer version;
}
