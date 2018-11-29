package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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


    /**
     * 跳转到开关管理首页
     */
    @RequestMapping("")
    public String index(String type, Model model) {
        return PREFIX + "switch.html";
    }


}
