package com.stylefeng.guns.rest.modular.football.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.enums.PayStatusEnum;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.common.exception.BussinessException;
import com.stylefeng.guns.rest.common.persistence.dao.PkOrderMapper;
import com.stylefeng.guns.rest.common.persistence.model.PkOrder;
import com.stylefeng.guns.rest.common.util.response.CommonListResp;
import com.stylefeng.guns.rest.common.util.response.CommonResp;
import com.stylefeng.guns.rest.common.util.response.ResponseCode;
import com.stylefeng.guns.rest.common.wxpay.*;
import com.stylefeng.guns.rest.common.wxpay.model.OrderInfo;
import com.stylefeng.guns.rest.common.wxpay.model.OrderReturnInfo;
import com.stylefeng.guns.rest.common.wxpay.model.SignInfo;
import com.thoughtworks.xstream.XStream;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 本示例仅供参考，请根据自己的使用情景进行修改
 * @Date: 2018/4/8
 * @Author: wcf
 */
@RequestMapping("/weixin")
@RestController
@Api(value = "WeixinController|一个用来测试swagger注解的控制器")
public class WeixinController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PkOrderMapper pkOrderMapper;

    /**
     * 小程序后台登录，向微信平台发送获取access_token请求，并返回openId
     *
     * @param request
     * @return openid
     * @throws IOException
     * @since Weixin4J 1.0.0
     */
    @RequestMapping(value = "/getOpenId", method = RequestMethod.GET)
    @ApiOperation(value = "openId", notes = "返回码:openId;")
    @ApiImplicitParam(paramType = "query", name = "code", value = "token_code", required = true, dataType = "String")
    public ResponseEntity getOpenId(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");
        if (code == null || "".equals(code)) {
            throw new BussinessException(BizExceptionEnum.TOKEN_CODE_ERROR);
        }
        log.info("---------Code:" + code);
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid=" + Configure.getAppID() + "&secret=" + Configure.getSecret() + "&js_code=" + code + "&grant_type=authorization_code");
        //设置请求器的配置
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse res = httpClient.execute(httpGet);
        HttpEntity entity = res.getEntity();
        String rest = EntityUtils.toString(entity, "UTF-8");
        JSONObject jsStr = JSONObject.parseObject(rest);
        String openid = (String) jsStr.get("openid");
        Map data = new HashMap();
        data.put("openid", openid);
        log.info("---------Openid:" + openid);
        return ResponseEntity.ok(new CommonResp<Map>(data));
    }
    /**
     * @param
     * @param request
     * @Description: 发起微信支付
     * @author: wcf
     * @date: 2017年8月28日
     */
    @RequestMapping(value = "/wxPay", method = RequestMethod.POST)
    @ApiOperation(value = "", notes = "返回支付需要信息")
    @ApiImplicitParam(paramType = "query", name = "openid", value = "openid", required = true, dataType = "String")
    public Object wxPay(HttpServletRequest request) {
        String openid = request.getParameter("openid");
        String orderno = request.getParameter("orderno");
        if (openid == null || "".equals(openid)) {
            throw new BussinessException(BizExceptionEnum.OPEN_ID_ERROR);
        }
        try {
            OrderInfo order = new OrderInfo();
            order.setAppid(Configure.getAppID());
            order.setMch_id(Configure.getMch_id());
            String nonce_str = RandomStringGenerator.getRandomStringByLength(32);
            order.setNonce_str(nonce_str);
            order.setBody("测试支付啦");
            order.setOut_trade_no(orderno);
            order.setTotal_fee(1);
            //获取本机的ip地址
            order.setSpbill_create_ip(IpUtils.getIpAddr(request));
            order.setNotify_url("https://qiuwangjue.mybission.com/weixin/wxNotify");
            order.setTrade_type("JSAPI");
            order.setOpenid(openid);
            order.setSign_type("MD5");
            //生成签名
            String sign = Signature.getSign(order);
            log.info("签名后结果是:{}",sign);
            order.setSign(sign);
            String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
            log.info("---------下单返回:" + result);
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnInfo.class);
            OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);
            String return_code = returnInfo.getResult_code();
            //返回给移动端需要的参数
            List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
            Map<String, Object> response = new HashMap<String, Object>();
            if ("SUCCESS".equals(return_code)) {
                // 业务结果
                String prepay_id = returnInfo.getPrepay_id();//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

                SignInfo signInfo = new SignInfo();
                signInfo.setAppId(Configure.getAppID());
//                long time = System.currentTimeMillis() / 1000;
                signInfo.setTimeStamp(timeStamp + "");
                signInfo.setNonceStr(nonce_str);
                signInfo.setRepay_id("prepay_id=" + returnInfo.getPrepay_id());
                signInfo.setSignType("MD5");
                //再次生成签名
                String paySign = Signature.getSign(signInfo);
                response.put("paySign", paySign);
                response.put("appid", Configure.getAppID());
                results.add(response);
            }
            return ResponseEntity.ok(new CommonListResp<Map<String, Object>>(results));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * @return
     * @throws Exception
     * @throws
     * @Description:微信支付
     * @author dzg
     * @date 2016年12月2日
     */
    @RequestMapping(value = "/wxNotify")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        log.info("接收到的报文：" + notityXml);
        Map map = PayUtil.doXMLParse(notityXml);
        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {
            //验证签名是否正确(过滤掉sign和sign_type)
            String sign = PayUtil.sign(PayUtil.createLinkString(PayUtil.paraFilter(map)), Configure.getKey(),"utf-8").toUpperCase();
            if (sign.equals((String) map.get("sign"))) {
                /**此处添加自己的业务逻辑代码start**/

                String orderno = (String) map.get("out_trade_no");//订单号
                Wrapper<PkOrder> pkOrderWrapper = new EntityWrapper<PkOrder>();
                pkOrderWrapper = pkOrderWrapper.eq("no", orderno);
                List<PkOrder> orders = pkOrderMapper.selectList(pkOrderWrapper);
                if (CollectionUtils.isNotEmpty(orders)){
                    PkOrder pkOrder = orders.get(0);
                    pkOrder.setStatus(PayStatusEnum.PAYED.getCode());//已支付
                    pkOrderMapper.updateById(pkOrder);
                }

                /**此处添加自己的业务逻辑代码end**/

                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        log.info(resXml);
        log.info("微信支付回调数据结束");

        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }
}
