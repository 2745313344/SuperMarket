package com.example.SuperMarket.controller;

import com.example.SuperMarket.utils.R;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(tags = "用户管理中心")
@ComponentScan("com.example")
public class user {
    @PostMapping("login")
    public R Login(){
        return R.ok().data("token","admin");
    }
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
