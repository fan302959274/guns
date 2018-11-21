package com.stylefeng.guns.rest.common.enums;

/**
 * 队员左右脚枚举类
 */
public enum FootEnum {

    //队员惯用脚
    LEFT("1", "左"),
    RIGHT("2", "右"),
    BOTH("3","双脚");


    String code;
    String message;

    FootEnum(String code, String message) {
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
            for (FootEnum s : FootEnum.values()) {
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
            for (FootEnum s : FootEnum.values()) {
                if (s.getCode() .equals(code) ) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
