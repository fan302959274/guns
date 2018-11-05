package com.stylefeng.guns.rest.common.wxpay;

public class Configure {
	//商户号
	private static String mch_id = "1517700411";
	//支付秘钥
	private static String key = "913201133027075291320114MA1MF8WT";

	//小程序ID	
	private static String appID = "wx7fbf903391250b56";
	//小程序密钥
	private static String secret = "98c6e8064de92376e2f93e68d42a8a4b";

	public static String getSecret() {
		return secret;
	}

	public static void setSecret(String secret) {
		Configure.secret = secret;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static String getAppID() {
		return appID;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public static String getMch_id() {
		return mch_id;
	}

	public static void setMch_id(String mch_id) {
		Configure.mch_id = mch_id;
	}

}
