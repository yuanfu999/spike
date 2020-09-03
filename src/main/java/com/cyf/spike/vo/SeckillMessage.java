package com.cyf.spike.vo;

import com.cyf.spike.entity.SkUser;

/**
 * Created by jiangyunxiong on 2018/5/29.
 *
 * 消息体
 */
public class SeckillMessage {

    private SkUser user;
    private long goodsId;

    public SkUser getUser() {
        return user;
    }

    public void setUser(SkUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
