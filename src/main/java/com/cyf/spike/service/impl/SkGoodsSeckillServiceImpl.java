package com.cyf.spike.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.cyf.spike.mapper.SkGoodsSeckillMapper;
import com.cyf.spike.service.SkGoodsSeckillService;
@Service
public class SkGoodsSeckillServiceImpl implements SkGoodsSeckillService{

    @Resource
    private SkGoodsSeckillMapper skGoodsSeckillMapper;

}
