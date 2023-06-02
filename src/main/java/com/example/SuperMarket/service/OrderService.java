package com.example.SuperMarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.SuperMarket.entity.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-02-25
 */
public interface OrderService extends IService<Order> {

    String createOrder(String goodsId);
}
