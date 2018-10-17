package com.stylefeng.guns.rest.common.enums;

/**
 * 附件种类枚举类
 *
 */
public enum AttachCategoryEnum {

    AD("AD", "队员"),
    MEMBER("MEMBER","队员");

    String code;
    String message;

    AttachCategoryEnum(String code, String message) {
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
