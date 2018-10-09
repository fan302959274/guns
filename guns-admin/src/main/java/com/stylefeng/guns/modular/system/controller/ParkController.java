package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.system.dao.ParkDao;
import com.stylefeng.guns.modular.system.warpper.ParkWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 广告控制器
 *
 * @author fengshuonan
 * @Date 2018-10-08 22:51:10
 */
@Controller
@RequestMapping("/park")
public class ParkController extends BaseController {

    private String PREFIX = "/football/park/";
    @Resource
    ParkDao parkDao;


    /**
     * 跳转到广告首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "park.html";
    }

    /**
     * 跳转到添加广告
     */
    @RequestMapping("/park_add")
    public String parkAdd() {
        return PREFIX + "park_add.html";
    }

    /**
     * 跳转到修改广告
     */
    @RequestMapping("/banner_update/{bannerId}")
    public String bannerUpdate(@PathVariable Integer bannerId, Model model) {
        return PREFIX + "banner_edit.html";
    }

    /**
     * 获取广告列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> banners = this.parkDao.selectParks(super.getPara("roleName"));
        return super.warpObject(new ParkWarpper(banners));
    }

    /**
     * 新增广告
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除广告
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改广告
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 广告详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
