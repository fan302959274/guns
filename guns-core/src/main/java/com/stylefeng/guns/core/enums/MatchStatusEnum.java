package com.stylefeng.guns.core.enums;

/**
 * 约战状态枚举类
 */
public enum MatchStatusEnum {

    //约战状态1:匹配中;2:待比赛;3:约战中;4:约战完成;5:约战失败;
    FINDING("1", "匹配中"),
    WAITING("2", "待比赛"),
    MATCHING("3", "约战中"),
    COMPLETE("4", "约战完成"),
    FAIL("5","约战失败");


    String code;
    String message;

    MatchStatusEnum(String code, String message) {
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
            for (MatchStatusEnum s : MatchStatusEnum.values()) {
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
            for (MatchStatusEnum s : MatchStatusEnum.values()) {
                if (s.getCode() .equals(code) ) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }

}
