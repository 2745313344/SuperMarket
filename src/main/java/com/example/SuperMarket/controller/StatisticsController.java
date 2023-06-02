package com.example.SuperMarket.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.SuperMarket.entity.Goods;
import com.example.SuperMarket.entity.GoodsQuery;
import com.example.SuperMarket.entity.Statistics;
import com.example.SuperMarket.entity.StatisticsQuery;
import com.example.SuperMarket.service.StatisticsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.example.SuperMarket.utils.R;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@Api(tags = "数据统计管理中心")
@RequestMapping("/statistics")
@ComponentScan("com.example")
public class StatisticsController {
    @Autowired
    private StatisticsService service;
    @PostMapping("addStatistics")
    public R addStatistics(@RequestBody Goods goods){
        Statistics statistics = new Statistics();
        statistics.setAvatar(goods.getAvatar());
        statistics.setCount(1);
        statistics.setName(goods.getName());
        statistics.setIsDeleted(0);
        statistics.setPayid(goods.getId());
        statistics.setPayuser("郭佳乐");
        statistics.setPrice(goods.getPrice());
        statistics.setSum(goods.getPrice());
        service.insert(statistics);
        return R.ok();
    }
    @GetMapping("test/{id}")
    public R test(@PathVariable String id){
        return R.ok().data("id",id);
    }
    @GetMapping("getWeekStatistics/{current}/{limit}")
    public R getWeekStatistics(@PathVariable long current,@PathVariable long limit){
        Page<Statistics> page=new Page<>(current,limit);
        QueryWrapper<Statistics> wrapper=new QueryWrapper<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = f.format(new Date());
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,-7);
        Date time = c.getTime();
        wrapper.ge("gmt_create",time);
        wrapper.le("gmt_create",format1);
        wrapper.orderByDesc("gmt_create");
        service.selectPage(page,wrapper);
        long total = page.getTotal();
        List<Statistics> records = page.getRecords();
        float sum=0;
        for(int i=0;i<records.size();i++){
            sum+=records.get(i).getSum();
        }
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",records);
        map.put("sum",sum);
        return R.ok().data(map);
    }
    @GetMapping("getTodayStatistics/{current}/{limit}")
    public R getTodayStatistics(@PathVariable long current,@PathVariable long limit){
        Page<Statistics> page=new Page<>(current,limit);
        QueryWrapper<Statistics> wrapper=new QueryWrapper<>();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = f.format(new Date());
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,-1);
        Date time = c.getTime();
        wrapper.ge("gmt_create",time);
        wrapper.le("gmt_create",format1);
        wrapper.orderByDesc("gmt_create");
        service.selectPage(page,wrapper);
        long total = page.getTotal();
        List<Statistics> records = page.getRecords();
        float sum=0;
        for(int i=0;i<records.size();i++){
            sum+=records.get(i).getSum();
        }
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",records);
        map.put("sum",sum);
        return R.ok().data(map);
    }
    @GetMapping("getMonthStatistics/{current}/{limit}")
    public R getMonthStatistics(@PathVariable long current,@PathVariable long limit){
        Page<Statistics> page=new Page<>(current,limit);
        QueryWrapper<Statistics> wrapper=new QueryWrapper<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c =Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,1);
        String time = format.format(c.getTime());
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH,ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String time2 = format.format(ca.getTime());

//        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//        String format1 = f.format(new Date());
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        c.add(Calendar.MONTH,-1);
//        Date time = c.getTime();
        wrapper.ge("gmt_create",time);
        wrapper.le("gmt_create",time2);
        wrapper.orderByDesc("gmt_create");
        service.selectPage(page,wrapper);
        long total = page.getTotal();
        List<Statistics> records = page.getRecords();
        float sum=0;
        for(int i=0;i<records.size();i++){
            sum+=records.get(i).getSum();
        }
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",records);
        map.put("sum",sum);
        return R.ok().data(map);
    }
    @PostMapping("pageStatistics/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) StatisticsQuery statisticsQuery){
        Page<Statistics> page=new Page<>(current,limit);
        QueryWrapper<Statistics> wrapper=new QueryWrapper<>();
        String name = statisticsQuery.getName();
        String begin = null;
        String end =null;
        if (statisticsQuery.getBegin()!=null){
            begin = statisticsQuery.getBegin();}
        if(statisticsQuery.getEnd()!=null){
            end = statisticsQuery.getEnd();}
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        wrapper.orderByDesc("gmt_create");
        service.selectPage(page,wrapper);
        long total = page.getTotal();
        List<Statistics> records = page.getRecords();
        float sum=0;
        for(int i=0;i<records.size();i++){
            sum+=records.get(i).getSum();
        }
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",records);
        map.put("sum",sum);
        return R.ok().data(map);
    }

}
