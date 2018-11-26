package com.stylefeng.guns.pay;

import com.stylefeng.guns.core.util.httpclient.HttpClientUtil;
import org.junit.Test;

import java.util.HashMap;

/**
 * 实体生成
 *
 * @author fengshuonan
 * @date 2017-08-23 12:15
 */
public class PayTest {

    @Test
    public void entityGenerator() throws Exception {
//        String notityXml = "<xml><appid><![CDATA[wx7fbf903391250b56]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1517700411]]></mch_id><nonce_str><![CDATA[yaaepvqsii4ui1kigih10tm9mny5a4n5]]></nonce_str><openid><![CDATA[osO_i5Nw8D7tILw9qFq7kDJ7-sIk]]></openid><out_trade_no><![CDATA[iqatx10rqfkk55b82tqvwwt0jph2hxce]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[4A9BC1B49C178A45243EC55C24CD5FC2]]></sign><time_end><![CDATA[20181119171844]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000216201811193015378563]]></transaction_id></xml>";
//
//
//
//        Map map = PayUtil.doXMLParse(notityXml);
//        String sign = PayUtil.sign(PayUtil.createLinkString(PayUtil.paraFilter(map)), Configure.getKey(),"utf-8");
//        boolean b = PayUtil.verify(PayUtil.createLinkString(PayUtil.paraFilter(map)), (String) map.get("sign"), Configure.getKey(), "utf-8");
//        System.out.println("12312");


        //发送第三方 TODO2018112321382320659
        String msg = "【球王决】尊敬的郭大王，您所属的球队测试球队约战信息如下：时间：2018年12月01日12:00:00-14:00:00；地点：球场1；对手：郭郭的球队(使者)赛制为7十1，裁判为一主两边，同时我们为您的球队赠送恒大冰泉一箱。请您通知参赛队员提前半小时到场热身，并做好参赛准备。";
        String result = new HttpClientUtil().doPost("http://utf8.api.smschinese.cn/?Uid=毕盛小程序&Key=d57ba7e3dcc263b1ff5f&smsMob=13651638713&smsText="+msg, new HashMap<>(), "utf-8");
        System.out.println(result);
    }
}
