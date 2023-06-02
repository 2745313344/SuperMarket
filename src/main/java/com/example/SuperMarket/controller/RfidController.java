package com.example.SuperMarket.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.SuperMarket.entity.Goods;
import com.example.SuperMarket.entity.Rfid;
import com.example.SuperMarket.entity.RfidQuery;
import com.example.SuperMarket.entity.Statistics;
import com.example.SuperMarket.service.RfidBeanService;
import com.example.SuperMarket.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@Api(tags = "标签管理中心")
@RequestMapping("/rfid")
@ComponentScan("com.example")
public class RfidController {
    @Autowired
    private RfidBeanService service;
    @PostMapping("addRfid")
    public R addRfid(@RequestBody Rfid rfid){
        service.insert(rfid);
        return R.ok();
    }
    @GetMapping("getRfidById/{id}")
    public R getAllRfid(@PathVariable String id){
        QueryWrapper<Rfid> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        Rfid rfid = service.selectOne(wrapper);
        return R.ok().data("rfid",rfid);
    }

    @GetMapping("getAllRfid")
    public R getAllRfid(){
        List<Rfid> rfids = service.selectList(null);
        return R.ok().data("rfids",rfids);
    }
    @GetMapping("getAllRfidName")
    public R getAllRfidName(){
        QueryWrapper<Rfid> wrapper = new QueryWrapper<>();
        wrapper.eq("is_used",0);
        List<Rfid> rfids = service.selectList(wrapper);
        ArrayList<String> list =new ArrayList<>();
        for (int i = 0; i < rfids.size(); i++) {
            list.add(rfids.get(i).getName());
        }
        return R.ok().data("list",list);
    }
    @GetMapping("getIdByName/{name}")
    public R getIdByName(@PathVariable String name){
        QueryWrapper<Rfid> wrapper = new QueryWrapper();
        wrapper.eq("name",name);
        Rfid rfid = service.selectOne(wrapper);
        String id = rfid.getId();
        return R.ok().data("id",id);
    }
    @GetMapping("getAvailableRfid")
    public R getAvailableRfid(){
        QueryWrapper<Rfid> wrapper = new QueryWrapper<>();
        wrapper.eq("flag",0);
        List<Rfid> rfids = service.selectList(wrapper);
        return R.ok().data("rfids",rfids);
    }
    @DeleteMapping("deleteRfidById/{id}")
    public R deleteRfidById(@PathVariable String id){
        QueryWrapper<Rfid> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        service.delete(wrapper);
        return R.ok();
    }
    @PostMapping("updateRfidById")
    public R updateGoodsById(@RequestBody Rfid rfid){
        QueryWrapper<Rfid> wrapper = new QueryWrapper<>();
        wrapper.eq("id",rfid.getId());
        service.update(rfid,wrapper);
        return R.ok();
    }
    @PostMapping("getRfidPage/{current}/{limit}")
    public R getRfidPage(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false)RfidQuery rfidQuery){
        Page<Rfid> page=new Page<>(current,limit);
        QueryWrapper<Rfid> wrapper=new QueryWrapper<>();
        if(rfidQuery.getBegin()!=null){
        wrapper.ge("gmt_create",rfidQuery.getBegin());}
        if(rfidQuery.getEnd()!=null){
        wrapper.le("gmt_create",rfidQuery.getEnd());}
        wrapper.orderByDesc("gmt_modified");
        if(rfidQuery.getName()!=null){
            wrapper.like("name",rfidQuery.getName());
        }
        service.selectPage(page,wrapper);
        long total = page.getTotal();
        List<Rfid> records = page.getRecords();
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }
}
