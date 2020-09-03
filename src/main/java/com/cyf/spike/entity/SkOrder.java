package com.cyf.spike.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "sk_order")
public class SkOrder implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "goods_id")
    private Long goodsId;

    private static final long serialVersionUID = 1L;
}