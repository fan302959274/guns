package com.stylefeng.guns.rest.common.enums;

/**
 * 球队级别枚举类
 */
public enum TeamLevelEnum {

    //球队级别
    SHIZHE("0",0,999, "使者"),
    SHOUWEI("1",1000,1499, "守卫"),
    ZHANSHI("1",1500,1999, "战士"),
    TONGZHI("1",2000,2499, "统治"),
    JINGDIAN("1",2500,2999, "经典"),
    CHUANQI("1",3000,3499, "传奇"),
    SHENLING("1",3500,9999, "神灵")
    ;


    String code;
    Integer min;
    Integer max;
    String message;

    TeamLevelEnum(String code,Integer min ,Integer max,String message) {
        this.code = code;
        this.min = min;
        this.max = max;
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

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public static String codeOf(String message) {
        if (message == null) {
            return "";
        } else {
            for (TeamLevelEnum s : TeamLevelEnum.values()) {
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
            for (TeamLevelEnum s : TeamLevelEnum.values()) {
                if (s.getCode() .equals(code) ) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }

    public static TeamLevelEnum valueOfMsg(String message) {
        if (message == null) {
            return TeamLevelEnum.SHIZHE;
        } else {
            for (TeamLevelEnum s : TeamLevelEnum.values()) {
                if (s.getMessage() .equals(message) ) {
                    return s;
                }
            }
            return TeamLevelEnum.SHIZHE;
        }
    }

}
