package com.stylefeng.guns.rest.common.enums;

/**
 * 队员所踢位置枚举类
 */
public enum PositionEnum {

    //队员所踢位置
    FORWARD("1", "前锋"),
    CENTER("2", "中场"),
    BACK("3", "后卫"),
    DOOR("4", "门将");


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

    public static String codeOf(String message) {
        if (message == null) {
            return "";
        } else {
            for (PositionEnum s : PositionEnum.values()) {
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
            for (PositionEnum s : PositionEnum.values()) {
                if (s.getCode() .equals(code) ) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }

}
