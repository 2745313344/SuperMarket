package com.example.SuperMarket.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="数据统计对象", description="数据统计表")
public class Statistics {
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    private String name;
    private float price;
    private int count;
    private String avatar;
    private float sum;
    private String payid;
    private String payuser;
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getPayuser() {
        return payuser;
    }

    public void setPayuser(String payuser) {
        this.payuser = payuser;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", avatar='" + avatar + '\'' +
                ", sum=" + sum +
                ", payid='" + payid + '\'' +
                ", payuser='" + payuser + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
