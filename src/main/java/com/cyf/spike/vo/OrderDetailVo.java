package com.cyf.spike.vo;

import com.cyf.spike.entity.SkOrderInfo;
import lombok.Data;


/**
 * Created by jiangyunxiong on 2018/5/28.
 */
@Data
public class OrderDetailVo {

    private GoodsVo goods;
    private SkOrderInfo order;

}
