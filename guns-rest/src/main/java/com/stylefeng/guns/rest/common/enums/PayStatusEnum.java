package com.stylefeng.guns.rest.common.enums;

/**
 * 支付状态枚举类
 */
public enum PayStatusEnum {

    //支付状态
    NOPAY("0", "未支付"),
    PAYED("1", "已支付");


    String code;
    String message;

    PayStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String codeOf(String message) {
        if (message == null) {
            return "";
        } else {
            for (PayStatusEnum s : PayStatusEnum.values()) {
                if (s.getMessage() .equals(message) ) {
                    return s.getCode();
                }
            }
            return "";
        }
    }

    public static String messageOf(String code) {
        if (code == null) {
            return "";
        } else {
            for (PayStatusEnum s : PayStatusEnum.values()) {
                if (s.getCode() .equals(code) ) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }

}
