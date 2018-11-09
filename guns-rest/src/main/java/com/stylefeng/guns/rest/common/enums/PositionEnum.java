package com.stylefeng.guns.rest.common.enums;

/**
 * 队员所踢位置枚举类
 */
public enum PositionEnum {

    //队员所踢位置
    FORWARD("FORWARD", "前锋"),
    CENTER("CENTER", "中场"),
    BACK("BACK", "后卫"),
    DOOR("DOOR", "门将");


    String code;
    String message;

    PositionEnum(String code, String message) {
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

}
