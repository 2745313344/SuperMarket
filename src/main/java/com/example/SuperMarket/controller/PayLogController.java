package com.example.SuperMarket.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.SuperMarket.entity.Goods;
import com.example.SuperMarket.entity.Pay;
import com.example.SuperMarket.service.GoodsService;
import com.example.SuperMarket.service.PayLogService;
import com.example.SuperMarket.service.PayService;
import com.example.SuperMarket.utils.R;
import com.github.wxpay.sdk.WXPayUtil;
import io.swagger.annotations.Api;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import com.example.SuperMarket.utils.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@Api(tags = "支付管理中心")
@RequestMapping("/paylog")
@ComponentScan("com.example")
@MapperScan("com.example")
@Repository
public class PayLogController {
    @Autowired
    private PayLogService payLogService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private PayService service;
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        try {
            QueryWrapper<Pay> wrapper =new QueryWrapper();
            wrapper.eq("id",orderNo);
            Pay pay = service.selectOne(wrapper);
            Map m = new HashMap();
            //1、设置支付参数
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", pay.getUser());
            m.put("out_trade_no", orderNo);
            m.put("total_fee", new BigDecimal(Float.toString(pay.getPrice())).multiply(new
                    BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url",
                    "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");
            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4、封装返回结果集
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", pay.getId());
            map.put("total_fee", new BigDecimal(Float.toString(pay.getPrice())));
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));
                    System.out.println(resultMap.get("code_url"));
            return R.ok().data("map",map);
        }catch (Exception e){ }
       return R.ok();
    }

    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
//        Map<String,String> map =payLogService.queryPayStatus(orderNo);
//        System.out.println("****************查询订单状态map集合："+map);
        Map m = new HashMap();
        //1、设置支付参数
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        m.put("out_trade_no", orderNo);
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(m,
                    "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
//3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
//6、转成Map
//7、返回
//            return resultMap;
            if(resultMap.get("trade_state").equals("SUCCESS")){
//                payLogService.updateOrdersStatus(resultMap);
                return R.ok().code("200").message("支付成功");
            }
            return R.ok().code("25000");
        } catch (Exception e) {
            e.printStackTrace();
//            return null;
            return R.error();
        }









//        if (map == null){
//            return R.error().message("支付出错了");
//        }
//        if(map.get("trade_state").equals("SUCCESS")){
//            payLogService.updateOrdersStatus(map);
//            return R.ok().code("200").message("支付成功");
//        }
//        return R.ok().code("25000").message("支付中");
    }
}
