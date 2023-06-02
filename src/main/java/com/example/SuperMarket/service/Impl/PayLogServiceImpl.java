package com.example.SuperMarket.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.SuperMarket.entity.Goods;
import com.example.SuperMarket.entity.Order;
import com.example.SuperMarket.entity.PayLog;
import com.example.SuperMarket.mapper.PayLogMapper;
import com.example.SuperMarket.service.GoodsService;
import com.example.SuperMarket.service.OrderService;
import com.example.SuperMarket.service.PayLogService;
import com.example.SuperMarket.utils.HttpClient;
import com.github.wxpay.sdk.WXPayUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Override
    public Map createNative(String orderNo) {
//        System.out.println("888666");
//        try {
//            QueryWrapper<Goods> wrapper =new QueryWrapper();
//            wrapper.eq("id",orderNo);
//            Goods goods = goodsService.selectOne(wrapper);
//            Map m = new HashMap();
//            //1、设置支付参数
//            m.put("appid", "wx74862e0dfcf69954");
//            m.put("mch_id", "1558950191");
//            m.put("nonce_str", WXPayUtil.generateNonceStr());
//            m.put("body", goods.getName());
//            m.put("out_trade_no", orderNo);
//            m.put("total_fee", new BigDecimal(Float.toString(goods.getPrice())).multiply(new
//                    BigDecimal("100")).longValue()+"");
//            m.put("spbill_create_ip", "127.0.0.1");
//            m.put("notify_url",
//                    "http://guli.shop/api/order/weixinPay/weixinNotify\n");
//            m.put("trade_type", "NATIVE");
////2、HTTPClient来根据URL访问第三方接口并且传递参数
//            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
////client设置参数
//            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
//            client.setHttps(true);
//            client.post();
////3、返回第三方的数据
//            String xml = client.getContent();
//            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
////4、封装返回结果集
//            Map map = new HashMap<>();
//            map.put("out_trade_no", orderNo);
//            map.put("course_id", goods.getId());
//            map.put("total_fee", new BigDecimal(Float.toString(goods.getPrice())));
//            map.put("result_code", resultMap.get("result_code"));
//            map.put("code_url", resultMap.get("code_url"));
////微信支付二维码2小时过期，可采取2小时未支付取消订单
////redisTemplate.opsForValue().set(orderNo, map, 120,
////            TimeUnit.MINUTES);
//            return map;
//        }catch (Exception e){
//
//        }
        return null;
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
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
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

//        return null;
    }

    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        //获取订单id
        String orderNo = map.get("out_trade_no");
//根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        if(order.getStatus().intValue() == 1) return;
        order.setStatus(1);
        orderService.updateById(order);
//记录支付日志
        PayLog payLog=new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表

    }
}
