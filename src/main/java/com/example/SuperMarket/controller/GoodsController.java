package com.example.SuperMarket.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.SuperMarket.entity.Goods;
import com.example.SuperMarket.entity.GoodsQuery;
import com.example.SuperMarket.service.FileService;
import com.example.SuperMarket.service.GoodsService;
import com.example.SuperMarket.service.Impl.FileServiceImpl;
import com.example.SuperMarket.service.Impl.RfidServiceImpl;
import com.example.SuperMarket.service.RfidService;
import com.example.SuperMarket.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Api(tags = "商品管理中心")
@RequestMapping("/goods")
@ComponentScan("com.example")
public class GoodsController {
    @Autowired
    private GoodsService service;
    private FileService fileService = new FileServiceImpl();
    RfidService rfidservice = new RfidServiceImpl();
    @GetMapping("read")
    public R getRFID(){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        String tag=rfidservice.getRFIDTag();
        System.out.println(tag.replaceAll("-",""));
        if (tag.equals("false")){
            return R.error().code("-1").message("扫描失败，请放置标签");
        }
        wrapper.eq("rfid",tag.replaceAll("-",""));
        List<Goods> goods = service.selectList(wrapper);
        if(goods.get(0).getDiscount()!=0){
            return R.ok().code("200").data("goods",goods);
        }else {
            return R.ok().code("404");
        }
    }
    @GetMapping("getGoodsByRfid/{id}")
    public R getGoodsByRfid(@PathVariable String id){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("rfid",id);
        wrapper.ne("rfid",0);
        Goods goods = service.selectOne(wrapper);
        return R.ok().data("goods",goods);

    }
    @GetMapping("getGoodsById/{id}")
    public R getGoodsById (@PathVariable String id){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        Goods goods = service.selectOne(wrapper);
        return R.ok().data("item",goods);
    }
    @PostMapping("xiaoci")
    public R xiaoci(@RequestBody Goods goods){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("id",goods.getId());
        goods.setDiscount(0);
        service.update(goods,wrapper);
        return R.ok();
    }
    @PostMapping("sell")
    public R sell(@RequestBody Goods goods){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("id",goods.getId());
        goods.setCount(goods.getCount()-1);
        service.update(goods,wrapper);
        return R.ok();
    }
    @PostMapping("updateGoodsById")
    public R updateGoodsById(@RequestBody Goods goods){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("id",goods.getId());
        service.update(goods,wrapper);
        return R.ok();
    }
    @GetMapping("getAllGoods")
    public R getAllGoods(){
        List<Goods> goods = service.selectList(null);
        for (int i = 0; i < goods.size(); i++) {
            System.out.println(goods.get(i));
        }
        return R.ok().data("list",goods);
    }
    @GetMapping("getAllGoodsName")
    public R getAllGoodsName(){
        List<Goods> goods = service.selectList(null);
        ArrayList<String> list =new ArrayList<>();
        for (int i = 0; i < goods.size(); i++) {
            list.add(goods.get(i).getName());
        }
        return R.ok().data("list",list);
    }
    @GetMapping("getIdByName/{name}")
    public R getIdByName(@PathVariable String name){
        QueryWrapper<Goods> wrapper = new QueryWrapper();
        wrapper.eq("name",name);
        Goods goods = service.selectOne(wrapper);
        String id = goods.getId();
        return R.ok().data("id",id);
    }
    @PostMapping("addGoods")
    public R addGoods(@RequestBody Goods goods){
        QueryWrapper<Goods> wrapper =new QueryWrapper<>();
        service.insert(goods);
        return R.ok();
    }
    @PostMapping("deleteGoodsById/{id}")
    public R deleteGoodsById(@PathVariable String id){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        service.delete(wrapper);
        return R.ok();
    }
    @PostMapping("/uploading")
    public R uploading(MultipartFile file){
        HashMap<String,String> map = fileService.uploadingMusicFile(file);
        return R.ok().data("url",map);
    }
    @PostMapping("pageMusic/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false)GoodsQuery goods){
        Page<Goods> page=new Page<>(current,limit);
        QueryWrapper<Goods> wrapper=new QueryWrapper<>();
        String name = goods.getName();
        String begin = null;
        String end =null;
        if (goods.getBegin()!=null){
         begin = goods.getBegin();}
        if(goods.getEnd()!=null){
        end = goods.getEnd();}
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }


        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        wrapper.orderByDesc("gmt_modified");
        service.selectPage(page,wrapper);
        long total = page.getTotal();
        List<Goods> records = page.getRecords();
//        for(int i=0;i<records.size();i++){
//            System.out.println(records.get(i));
//        }
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }
}
