package com.stylefeng.guns.core.util.response;/**
 * Created by sh00859 on 2017/7/20.
 */


import java.util.List;

/**
 * 通用返回类
 *
 * @author
 * @create 2017-07-20 10:57
 **/
public class CommonListResp<T> {
    private String status = ResponseCode.SUCCESS.getCode();
    private String msg = ResponseCode.SUCCESS.getMsg();
    private List<T> data;


    public CommonListResp(List<T> list) {
        this.data = list;
    }

    public CommonListResp(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
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