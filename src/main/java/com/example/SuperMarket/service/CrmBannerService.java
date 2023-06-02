package com.example.SuperMarket.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.SuperMarket.entity.CrmBanner;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CrmBannerService extends BaseMapper<CrmBanner> {
//List<CrmBanner>selectAllBanner();
}
