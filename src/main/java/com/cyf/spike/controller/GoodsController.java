package com.cyf.spike.controller;

import com.cyf.spike.Constants.Constant;
import com.cyf.spike.Constants.KeyConstant;
import com.cyf.spike.Constants.PrefixContant;
import com.cyf.spike.entity.SkUser;
import com.cyf.spike.service.SkGoodsService;
import com.cyf.spike.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author chenyuanfu
 * @Date 2020/8/31 16:04
 **/
@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private SkGoodsService goodsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 获取秒杀商品列表页面
     * @param request
     * @param response
     * @param model
     * @param user
     * @return
     */
    @GetMapping(value = "list", produces = "text/html")
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, SkUser user){
        String html = stringRedisTemplate.opsForValue().get(KeyConstant.GOODS_LIST_KEY);
        if (StringUtils.isNotBlank(html)){
            return html;
        }
        List<GoodsVo> goodsVoList = goodsService.getGoodsVoList();
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsVoList);

        /*spring4
        //手动渲染
        SpringWebContext ctx = new SpringWebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);*/

        //spring5 手动渲染
        WebContext ctx = new WebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap());
        thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);

        if (StringUtils.isNotBlank(html)){
            stringRedisTemplate.opsForValue().set(KeyConstant.GOODS_LIST_KEY, html, Constant.TOKEN_EXPIRE, TimeUnit.SECONDS);
        }
        return html;
    }

    /**
     * 根据商品id获取商品详情页
     * @param request
     * @param response
     * @param goodsId
     * @param model
     * @return
     */
    @GetMapping(value = "detail/{id}", produces = "text/html")
    public String getGoodsDetailById(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @PathVariable("id") Long goodsId,
                                     SkUser user,
                                     Model model){
        String goodsDetailKey = PrefixContant.GOODS_DETAIL_PREFIX + goodsId;
        String html = stringRedisTemplate.opsForValue().get(goodsDetailKey);
        if (StringUtils.isNotBlank(html)){
            return html;
        }
        GoodsVo goodsDetail = goodsService.getGoodsDetailVoById(goodsId);
        model.addAttribute("user", user);
        model.addAttribute("goods", goodsDetail);

        long startTime = goodsDetail.getStartDate().getTime();
        long endTime = goodsDetail.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        int remainSeconds = 0;

        if (now < startTime){  //秒杀还没开始
            seckillStatus = 0;
            remainSeconds = (int) ((startTime - now) / 1000);
        }else if (now > endTime){  //秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        }else{  //秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        /*spring 4
        //手动渲染
        SpringWebContext springWebContext = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", springWebContext);*/

        //spring5 手动渲染
        WebContext ctx = new WebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap());
        thymeleafViewResolver.getTemplateEngine().process("goods_detail",ctx);
        if (StringUtils.isNotBlank(html)){
            stringRedisTemplate.opsForValue().set(goodsDetailKey, html, Constant.TOKEN_EXPIRE, TimeUnit.SECONDS);
        }
        return html;
    }



}
