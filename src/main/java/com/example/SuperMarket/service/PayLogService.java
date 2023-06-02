package com.example.SuperMarket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.SuperMarket.entity.PayLog;
import org.springframework.stereotype.Component;

import java.util.Map;
public interface PayLogService extends IService<PayLog> {
    Map createNative(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrdersStatus(Map<String, String> map);
}
