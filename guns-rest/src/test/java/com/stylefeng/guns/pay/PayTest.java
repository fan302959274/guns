package com.stylefeng.guns.pay;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.stylefeng.guns.rest.common.wxpay.Configure;
import com.stylefeng.guns.rest.common.wxpay.PayUtil;
import com.stylefeng.guns.rest.common.wxpay.Signature;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 实体生成
 *
 * @author fengshuonan
 * @date 2017-08-23 12:15
 */
public class PayTest {

    @Test
    public void entityGenerator() throws Exception {
        String notityXml = "<xml><appid><![CDATA[wx7fbf903391250b56]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1517700411]]></mch_id><nonce_str><![CDATA[yaaepvqsii4ui1kigih10tm9mny5a4n5]]></nonce_str><openid><![CDATA[osO_i5Nw8D7tILw9qFq7kDJ7-sIk]]></openid><out_trade_no><![CDATA[iqatx10rqfkk55b82tqvwwt0jph2hxce]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[4A9BC1B49C178A45243EC55C24CD5FC2]]></sign><time_end><![CDATA[20181119171844]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000216201811193015378563]]></transaction_id></xml>";



        Map map = PayUtil.doXMLParse(notityXml);
        String sign = PayUtil.sign(PayUtil.createLinkString(PayUtil.paraFilter(map)), Configure.getKey(),"utf-8");
        boolean b = PayUtil.verify(PayUtil.createLinkString(PayUtil.paraFilter(map)), (String) map.get("sign"), Configure.getKey(), "utf-8");
        System.out.println("12312");
    }
}
