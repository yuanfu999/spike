package com.cyf.spike.rabbitmq;

import com.cyf.spike.Constants.PrefixContant;
import com.cyf.spike.vo.SeckillMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author chenyuanfu
 * @Date 2020/9/2 17:14
 **/

@Service
@Slf4j
public class MQSender {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void sendMessage(SeckillMessage seckillMessage){
        String messageStr = "";
        try {
            messageStr = objectMapper.writeValueAsString(seckillMessage);
            log.info("send message: {}", messageStr);
            amqpTemplate.convertAndSend(PrefixContant.SECKILL_GOODS_KEY_PREFIX + "stock.decr",messageStr);
        } catch (JsonProcessingException e) {
            log.info("ObjectMapper: bean covert to String is fail");
            e.printStackTrace();
        }
    }
}
