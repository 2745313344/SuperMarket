package com.example.SuperMarket.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.SuperMarket.entity.Goods;
import com.example.SuperMarket.entity.Rfid;
import org.springframework.stereotype.Component;

@Component
public interface RfidBeanService extends BaseMapper<Rfid> {
}
