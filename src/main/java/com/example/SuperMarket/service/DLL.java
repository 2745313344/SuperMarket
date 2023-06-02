package com.example.SuperMarket.service;

import com.sun.jna.Library;
import com.sun.jna.Native;
import org.springframework.stereotype.Component;

@Component
public interface DLL extends Library {
    DLL dll= Native.load(
//            "G:\\workspace\\RFIDProject\\src\\test\\java\\OUR_MIFARE_x32.dll",
            //32位
//            "G:\\workspace\\RFIDProject\\src\\main\\resources\\OUR_MIFARE_x32.dll",
            //64位
//            "G:\\workspace\\RFIDProject\\src\\main\\resources\\OUR_MIFARE_x64.dll",
//            "C:\\Users\\Administrator\\Desktop\\OUR_MIFARE_x64.dll",

//            "C:\\Users\\15720586534\\Desktop\\SuperMarket\\src\\main\\resources\\OUR_MIFARE_x64.dll",
"C:\\Users\\15720586534\\Desktop\\SuperMarket\\src\\main\\resources\\OUR_MIFARE_x64.dll",
            DLL.class);
    //发出声音
    byte pcdbeep(int xms);

    //读取设备编号
    byte pcdgetdevicenumber(byte[] devicenumber);
    //轻松写卡
    byte piccwriteex(byte ctrlword, byte[] serial, byte area, byte keyA1B0, byte[] picckey, byte[] piccdata0_2);

    //轻松读卡
    byte piccreadex(
            byte ctrlword,
            /*以下控制字含义：读块0、块1、块2，仅读指定序列号的卡，需要每次指定密码
            Ctrlword = BLOCK0_EN + BLOCK1_EN + BLOCK2_EN + NEEDSERIAL+ EXTERNKEY
            以下控制字含义：读块0、块2，可读任意卡，需要每次指定密码
            Ctrlword = BLOCK0_EN + BLOCK2_EN +  EXTERNKEY
            以下控制字含义：读块0、块2，可读任意卡，启用芯片内部密码
            Ctrlword = BLOCK0_EN + BLOCK2_EN */
            byte[] serial,//存放卡序列号
            byte area,//要读的区号
            byte keyA1B0,
            byte[] picckey,//存卡密码
            byte[] piccdata0_2);//存放数据
    //寻卡并选中指定序列号的IC卡，必须指定序列号
    byte piccrequest(byte[] serial);





    public static final byte BLOCK0_EN = 0x01;//操作第0块
    public static final byte BLOCK1_EN = 0x02;//操作第1块
    public static final byte BLOCK2_EN = 0x04;//操作第2块
    public static final byte NEEDSERIAL = 0x08;//仅对指定序列号的卡操作
    public static final byte EXTERNKEY = 0x10;//使用函数时需指定密码，否则使用预存在读写器中的密码（该密码只能写入，无法读出，很安全）
    public static final byte NEEDHALT = 0x20;//读卡或写卡后顺便休眠该卡，休眠后，卡必须拿离开感应区，再放回感应区，才能进行第二次操作。

}
