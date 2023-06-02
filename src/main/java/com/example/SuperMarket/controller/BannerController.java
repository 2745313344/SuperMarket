package com.example.SuperMarket.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.SuperMarket.entity.CrmBanner;
import com.example.SuperMarket.service.CrmBannerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import com.example.SuperMarket.utils.R;
import java.util.List;


@RestController
@CrossOrigin
@Api(tags = "轮播图管理中心")
@RequestMapping("/banner")
@ComponentScan("com.example")
public class BannerController {
@Autowired
    private CrmBannerService service;
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = service.selectList(null);
        return R.ok().data("list",list);
    }
}
