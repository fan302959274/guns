package com.stylefeng.guns.rest.modular.football.controller;

import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.rest.modular.football.transfer.SmsEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 短信控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@RestController
@RequestMapping("/sms")
@Api(value = "SmsController|一个用来测试swagger注解的控制器")
public class SmsController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 短信发送接口
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ApiOperation(value = "发送短信验证码", notes = "返回码:200成功;")
    @ApiImplicitParam(paramType = "query", name = "mobile", value = "手机号", required = true, dataType = "String")
    public ResponseEntity register(@RequestParam String mobile) {
        log.info("请求的手机号为:{}", mobile);
        try {
            Integer random1 = new Random().nextInt(10);
            Integer random2 = new Random().nextInt(10);
            Integer random3 = new Random().nextInt(10);
            Integer random4 = new Random().nextInt(10);
            String smsCode = random1 + "" + random2 + "" + random3 + "" + random4;
            redisTemplate.opsForValue().set(mobile + "_registercode", smsCode, 120, TimeUnit.SECONDS);
            //发送第三方 TODO
            return new ResponseEntity(smsCode, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * 短信发送接口
     *
     * @param smsEntity
     * @return
     */
    @RequestMapping(value = "/valid", method = RequestMethod.POST)
    @ApiOperation(value = "验证短信验证码", notes = "返回码:200成功;")
    @ApiImplicitParam(paramType = "body", name = "smsEntity", value = "请求实体", required = true, dataType = "SmsEntity")
    public ResponseEntity valid(@RequestBody SmsEntity smsEntity) {
        log.info("验证码验证请求参数为:{}", JSONObject.toJSONString(smsEntity));
        try {
            Assert.notNull(smsEntity.getMobile(), "手机号不能为空");
            Assert.notNull(smsEntity.getSmscode(), "验证码不能为空");
            String smscode = (String) redisTemplate.opsForValue().get(smsEntity.getMobile() + "_registercode");
            if (StringUtils.isBlank(smscode)) {
                return new ResponseEntity("验证码无效或者已过期", HttpStatus.BAD_REQUEST);
            }
            if (!Objects.equals(smscode,smsEntity.getSmscode())){
                return new ResponseEntity("验证码错误", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity("验证成功", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }

    }


}
