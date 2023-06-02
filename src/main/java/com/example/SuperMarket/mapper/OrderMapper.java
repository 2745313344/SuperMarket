package com.example.SuperMarket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.SuperMarket.entity.Order;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2023-02-25
 */
@Component
public interface OrderMapper extends BaseMapper<Order> {

}
