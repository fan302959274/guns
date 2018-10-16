package com.stylefeng.guns.rest.modular.football.transfer;

/**
 * <p>
 * 短信
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-10
 */
public class SmsEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String smscode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }


}
