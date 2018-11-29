package com.stylefeng.guns.biz;

import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.modular.biz.service.ITestService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 业务测试
 *
 * @author fengshuonan
 * @date 2017-06-23 23:12
 */
public class BizTest extends BaseJunit {

    @Autowired
    ITestService testService;

    private String PREFIX = "/football/switch/";

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        //testService.testGuns();

//        testService.testBiz();

        //testService.testAll();

        redisTemplate.delete("sms:switch");
        Object flag = redisTemplate.opsForValue().get("sms:switch");
//        System.out.println(redisTemplate.opsForValue().get("sms:switch"));
//        System.out.println(Boolean.parseBoolean(flag.toString()));
//        System.out.println((flag == null )? true : (Boolean.parseBoolean(flag.toString())));
//        if ((flag == null )? true : (Boolean.parseBoolean(flag.toString()))) {
//            System.out.println(1);
//        }
    }
}
