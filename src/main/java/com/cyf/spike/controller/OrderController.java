package com.cyf.spike.controller;

import com.cyf.spike.entity.SkOrderInfo;
import com.cyf.spike.entity.SkUser;
import com.cyf.spike.result.CodeMsg;
import com.cyf.spike.result.Result;
import com.cyf.spike.service.SkGoodsService;
import com.cyf.spike.service.SkOrderInfoService;
import com.cyf.spike.service.SkOrderService;
import com.cyf.spike.vo.GoodsVo;
import com.cyf.spike.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author chenyuanfu
 * @Date 2020/9/2 14:03
 **/
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private SkOrderInfoService orderInfoService;

    @Autowired
    private SkGoodsService goodsService;

    /**
     * 根据订单id获取订单详情
     * @param model
     * @param user
     * @param orderId
     * @return
     */
    @PostMapping("detail")
    public Result<OrderDetailVo> detail(Model model, SkUser user, @RequestParam("orderId") Long orderId){
        if (user == null){
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        SkOrderInfo orderInfo = orderInfoService.getOrderDetailById(orderId);
        if (orderInfo == null){
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        GoodsVo goodsVo = goodsService.getGoodsDetailVoById(orderInfo.getGoodsId());
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setGoods(goodsVo);
        orderDetailVo.setOrder(orderInfo);
        return Result.success(orderDetailVo);
    }
}
