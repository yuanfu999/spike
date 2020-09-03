package com.cyf.spike.rabbitmq;

import com.cyf.spike.entity.SkOrder;
import com.cyf.spike.entity.SkUser;
import com.cyf.spike.service.SeckillService;
import com.cyf.spike.service.SkGoodsService;
import com.cyf.spike.service.SkOrderService;
import com.cyf.spike.vo.GoodsVo;
import com.cyf.spike.vo.SeckillMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author chenyuanfu
 * @Date 2020/9/2 17:14
 **/
@Service
@Slf4j
public class MQReceive {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SkGoodsService goodsService;

    @Autowired
    private SkOrderService orderService;

    @Autowired
    private SeckillService seckillService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${seckill.queue}", durable = "false", autoDelete = "false"),
                    exchange = @Exchange(
                            value = "${seckill.exchange}",
                            ignoreDeclarationExceptions = "true",   //忽略声明异常
                            type = ExchangeTypes.TOPIC,
                            durable = "false"
                    ),
                    key = "seckill.goods.stock.decr"
            )
    )
    public void decrSeckillGoodsStock(@Payload String seckillMessageStr, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel){
        try {
            SeckillMessage seckillMessage = objectMapper.readValue(seckillMessageStr, SeckillMessage.class);
            log.info("receive message: {}" , seckillMessage);
            channel.basicAck(deliveryTag,false);
            SkUser user = seckillMessage.getUser();
            Long goodsId = seckillMessage.getGoodsId();
            GoodsVo goodsVo = goodsService.getGoodsVoById(goodsId);
            Integer stock = goodsVo.getStockCount();
            if (stock <= 0){
                return;
            }
            SkOrder order = orderService.getOrderByUserIdAndGoodsId(user.getId(), goodsId);
            if (order != null){
                return ;
            }
            seckillService.createSeckillOrder(user, goodsVo);

        } catch (JsonProcessingException e) {
            log.info("ObjectMapper: String covert to bean is fail");
            e.printStackTrace();
        } catch (IOException e) {
            log.info("Consumer hand to ack is fail");
            e.printStackTrace();
        }
    }
}
