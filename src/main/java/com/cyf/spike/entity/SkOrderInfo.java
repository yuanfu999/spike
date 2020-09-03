package com.cyf.spike.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "sk_order_info")
public class SkOrderInfo implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "delivery_addr_id")
    private Long deliveryAddrId;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_count")
    private Integer goodsCount;

    @Column(name = "goods_price")
    private BigDecimal goodsPrice;

    /**
     * 订单渠道，1在线，2android，3ios
     */
    @Column(name = "order_channel")
    private Byte orderChannel;

    /**
     * 订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成
     */
    @Column(name = "`status`")
    private Byte status;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "pay_date")
    private Date payDate;

    private static final long serialVersionUID = 1L;
}