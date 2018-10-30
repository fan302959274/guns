package com.stylefeng.guns.rest.common.util.response;

/**
 * @author sh00859
 * @description 返回参数枚举类
 * @date 2017/7/20
 */
public enum ResponseCode {
    SUCCESS("1", "操作成功"),
    FILE_NOT_FOUND("30001", "文件未找到"),
    FILE_IS_EMPTY("30002", "文件为空"),
    COLLECTION_USERINFO_ERROR("40003", "收集用户信息失败"),

    SMSCODE_INVALID("50001","验证码无效或已过期"),
    SMSCODE_ERROR("50002","验证码错误"),
    IO_ERROR("99998", "IO异常"),
    SYSTEM_ERROR("99999", "系统错误");
    private String code;
    private String msg;

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
