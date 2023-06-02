package com.example.SuperMarket.utils;

import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

public class R {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private R(){}

    public static R ok(){
        R r=new R();
        r.setSuccess(true);
        r.setCode(2000);
        r.setMessage("success");
        return r;
    }
    public static R error(){
        R r=new R();
        r.setSuccess(false);
        r.setCode(2001);
        r.setMessage("error");
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }



    public R code(String code){
        this.setCode(Integer.valueOf(code));
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key, value);
        return this;
    }
    public R data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }
}

