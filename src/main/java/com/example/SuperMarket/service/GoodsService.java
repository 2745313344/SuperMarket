package com.example.SuperMarket.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.SuperMarket.entity.Goods;
import org.springframework.stereotype.Component;

@Component
public interface GoodsService extends BaseMapper<Goods> {
}
