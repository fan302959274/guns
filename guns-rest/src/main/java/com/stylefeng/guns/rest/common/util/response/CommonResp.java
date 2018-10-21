package com.stylefeng.guns.rest.common.util.response;/**
 * Created by sh00859 on 2017/7/20.
 */


import java.util.List;

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
    private List<T> datas;

    public CommonResp(T body) {
        this.data = body;
    }

    public CommonResp(List<T> list) {
        this.datas = list;
    }

    public CommonResp(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
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