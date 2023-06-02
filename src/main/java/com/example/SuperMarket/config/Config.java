package com.example.SuperMarket.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example")
public class Config {
@Bean
    public ISqlInjector sqlInjector(){return new LogicSqlInjector(); }
@Bean
    public PaginationInterceptor paginationInterceptor(){return new PaginationInterceptor();}
}
