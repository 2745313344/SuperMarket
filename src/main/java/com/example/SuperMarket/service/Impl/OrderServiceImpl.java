package com.example.SuperMarket.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.SuperMarket.entity.Goods;
import com.example.SuperMarket.entity.Order;
import com.example.SuperMarket.mapper.OrderMapper;
import com.example.SuperMarket.service.OrderService;
import com.example.SuperMarket.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-02-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private BaseMapper<Goods> baseMapper_goods;
    @Autowired
    private BaseMapper<Order> baseMapper_order;
    @Override
    public String createOrder(String goodsId) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("id",goodsId);
        Goods goods = baseMapper_goods.selectOne(wrapper);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setGoodsId(goodsId);
        order.setGoodsName(goods.getName());
        order.setGoodsCover(goods.getAvatar());
        order.setTotalFee(new BigDecimal(Float.toString(goods.getPrice())));
        order.setStatus(0);
        order.setPayType(1);
        baseMapper_order.insert(order);
        return order.getOrderNo();
    }
}
