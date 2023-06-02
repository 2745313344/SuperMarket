package com.example.SuperMarket.service.Impl;

import com.example.SuperMarket.service.DLL;
import com.example.SuperMarket.service.RfidService;
import org.springframework.stereotype.Component;

import static com.example.SuperMarket.service.DLL.*;


@Component
public class RfidServiceImpl implements RfidService {

    @Override
    public String getRFIDTag() {
        byte ctrlword = (byte) (BLOCK0_EN + BLOCK1_EN + BLOCK2_EN
                + EXTERNKEY
        );
        byte[] cardId = new byte[4];//卡序列号
        byte area = 8;//指定为第8区
        byte authmode = 1;//大于0表示用A密码认证，推荐用A密码认证
        byte[] secret = new byte[6];//密码
        byte[] cardData = new byte[48]; //卡数据缓冲
        //指定密码
        secret[0] = (byte)0xff;
        secret[1] = (byte)0xff;
        secret[2] = (byte)0xff;
        secret[3] = (byte)0xff;
        secret[4] = (byte)0xff;
        secret[5] = (byte)0xff;
        byte piccreadex = dll.piccreadex(ctrlword, cardId, area, authmode, secret, cardData);
        String id= String.valueOf(cardId[0]);
        StringBuffer stringBuffer=new StringBuffer(id);
        for (int i = 1; i <cardId.length ; i++) {
            stringBuffer.append(cardId[i]);
        }
        System.out.println("状态码:"+piccreadex);
        System.out.println("卡序列号："+stringBuffer);
        //发出响声
//        DLL.dll.pcdbeep(200);
        if (piccreadex==0||piccreadex==12){
            DLL.dll.pcdbeep(200);
            return new String(stringBuffer);
        }else return "false";





    }
}
