package com.stylefeng.guns.common.enums;

/**
 * 附件类型枚举类
 *
 */
public enum AttachEnum {

    AD("AD", "队员");

    String code;
    String message;

    AttachEnum(String code, String message) {
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
