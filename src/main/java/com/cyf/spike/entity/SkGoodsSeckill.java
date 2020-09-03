package com.cyf.spike.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "sk_goods_seckill")
public class SkGoodsSeckill implements Serializable {
    /**
     * 秒杀商品id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     * 秒杀价
     */
    @Column(name = "seckill_price")
    private BigDecimal seckillPrice;

    /**
     * 库存数量
     */
    @Column(name = "stock_count")
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 并发版本控制
     */
    @Column(name = "version")
    private Integer version;

    private static final long serialVersionUID = 1L;
}