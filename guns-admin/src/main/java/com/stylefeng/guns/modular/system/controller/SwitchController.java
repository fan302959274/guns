package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 开关控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/switch")
public class SwitchController extends BaseController {

    private String PREFIX = "/football/switch/";

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 跳转到开关管理首页
     */
    @RequestMapping("")
    public String index(String type, Model model) {
        //开关开启才发送
        Object switchObject = redisTemplate.opsForValue().get("sms:switch");
        Boolean switchFlag = (switchObject == null) ? false : (Boolean.parseBoolean(switchObject.toString()));
        model.addAttribute("switchFlag", switchFlag);
        return PREFIX + "switch.html";
    }


    /**
     * @description 开关开启/关闭
     * category:种类
     * optype:操作类型 true/false开启还是关闭
     * @author sh00859
     * @date 2018/11/29
     */
    @RequestMapping(value = "/switchs")
    @ResponseBody
    public Object switchs(@RequestParam String category, @RequestParam String optype) {
        if ("SMS".equals(category)) {
            redisTemplate.opsForValue().set("sms:switch", optype);
        }
        return SUCCESS_TIP;
    }


}
