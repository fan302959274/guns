package com.stylefeng.guns.common.enums;

/**
 * 附件类型枚举类
 *
 */
public enum AttachTypeEnum {

    //队员的相关附件类型
    HEAD("HEAD", "头像"),
    IDCARD("IDCARD", "身份证"),
    LOGO("LOGO", "Logo")
    ;


    String code;
    String message;

    AttachTypeEnum(String code, String message) {
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
