package com.example.SuperMarket.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.SuperMarket.entity.Pay;
import com.example.SuperMarket.service.PayService;
import com.example.SuperMarket.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(tags = "订单管理中心")
@RequestMapping("/pay")
@ComponentScan("com.example")
public class PayController {
    @Autowired
    private PayService service;
    @PostMapping("addPay")
    public R addPay(@RequestBody Pay pay){
        QueryWrapper<Pay> wrapper = new QueryWrapper<>();
        pay.setUser("郭佳乐");
        int insert = service.insert(pay);
//        wrapper.eq("gmt_create", pay.getGmtCreate().toString());
//        Pay pay1 = service.selectOne(wrapper);
//        String id = pay1.getId();
        return R.ok().data("id",pay.getId());
    }
}
