package com.stylefeng.guns.core.util.response;/**
 * Created by sh00859 on 2017/7/20.
 */


/**
 * 通用返回类
 *
 * @author
 * @create 2017-07-20 10:57
 **/
public class CommonResp<T> {
    private String status = ResponseCode.SUCCESS.getCode();
    private String msg = ResponseCode.SUCCESS.getMsg();
    private T data;

    public CommonResp(T body) {
        this.data = body;
    }


    public CommonResp(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public CommonResp(String status,String msg,T body) {
        this.status = status;
        this.msg = msg;
        this.data = body;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}