package com.cyf.spike.Constants;

/**
 * @Author chenyuanfu
 * @Date 2020/8/31 12:20
 **/
public interface PrefixContant {

    //token缓存前缀
    String USER_TOKEN_PREFIX = "user:token:";

    //商品详情缓存前缀
    String GOODS_DETAIL_PREFIX = "goods:detail:";

    //秒杀商品缓存前缀
    String SECKILL_GOODS_PREFIX = "goods:seckill:";

    //秒杀商品是否秒杀结束前缀
    String SECKILL_GOODS_OVER_PREFIX = "goods:seckill:over:";

    //秒杀商品消息队列路由键前缀
    String SECKILL_GOODS_KEY_PREFIX = "seckill.goods.";


}
