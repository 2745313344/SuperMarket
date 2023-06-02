package com.example.SuperMarket.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.SuperMarket.entity.Order;
import com.example.SuperMarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import com.example.SuperMarket.utils.R;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-02-25
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
@Repository
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("createOrder/{goodsId}")
    public R saveOrder(@PathVariable String goodsId){
        String orderNo = orderService.createOrder(goodsId);
        return R.ok().data("orderId",orderNo);
    }
    @PostMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper =new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }
    @GetMapping("isBuyGoods/{goodsId}")
    public Boolean isBuyCourse(@PathVariable String courseId){
        QueryWrapper<Order> wrapper =new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if(count>0){
            return true;
        }
        return false;
    }
}

