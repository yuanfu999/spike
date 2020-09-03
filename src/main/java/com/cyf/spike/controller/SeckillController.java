package com.cyf.spike.controller;

import com.cyf.spike.Constants.PrefixContant;
import com.cyf.spike.entity.SkOrder;
import com.cyf.spike.entity.SkUser;
import com.cyf.spike.rabbitmq.MQSender;
import com.cyf.spike.result.CodeMsg;
import com.cyf.spike.result.Result;
import com.cyf.spike.service.SkGoodsService;
import com.cyf.spike.service.SkOrderService;
import com.cyf.spike.vo.GoodsVo;
import com.cyf.spike.vo.SeckillMessage;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀controller
 * @Author chenyuanfu
 * @Date 2020/9/2 14:40
 **/
@RestController
@RequestMapping("seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    @Autowired
    private SkGoodsService goodsService;

    @Autowired
    private SkOrderService orderService;

    @Autowired
    private MQSender mqSender;

    //基于guava令牌桶算法的限流器，一次生成10个令牌
    RateLimiter rateLimiter = RateLimiter.create(10);

    //商品是否处理过的
    Map<Long, Boolean> isHandlerMap = new HashMap<>();



    @RequestMapping("doSeckill")
    @ResponseBody
    public Result<Integer> seckill(Model model, SkUser user, @RequestParam("goodsId") Long goodsId) throws Exception {
        if (user == null){
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        //未在指定时间获得令牌，则返回系统繁忙提示
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)){
            return Result.error(CodeMsg.ACCESS_LIMIT_REACHED);
        }
        model.addAttribute("user", user);
        //内存标记，减少redis访问
        Boolean isMark = (Boolean) redisTemplate.opsForValue().get(PrefixContant.SECKILL_GOODS_PREFIX + goodsId);
        //如果redis中标记为true，则说明已经处理过秒杀订单
        if (isMark){
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        Long stock = redisTemplate.opsForValue().decrement(PrefixContant.SECKILL_GOODS_PREFIX + goodsId);
        if (stock < 0){
            afterPropertiesSet();
            Long stock2 = redisTemplate.opsForValue().decrement(PrefixContant.SECKILL_GOODS_PREFIX + goodsId);
            //如果秒杀商品只有一件
            if (stock2 < 0){
                return Result.error(CodeMsg.SECKILL_OVER);
            }
        }
        SkOrder order = orderService.getOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null){
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        //入队
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setUser(user);
        seckillMessage.setGoodsId(goodsId);
        mqSender.sendMessage(seckillMessage);
        //排队中
        return Result.success(0);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.getGoodsVoList();
        if (goodsVoList == null){
            return ;
        }
        for (GoodsVo goodsVo : goodsVoList){
            redisTemplate.opsForValue().set(PrefixContant.SECKILL_GOODS_PREFIX + goodsVo.getId(), goodsVo.getStockCount());
            isHandlerMap.put(goodsVo.getId(), false);
        }
    }
}
