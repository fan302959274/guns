package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.system.dao.ParkDao;
import com.stylefeng.guns.modular.system.dao.TeamDao;
import com.stylefeng.guns.modular.system.warpper.ParkWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 球场控制器
 *
 * @author gss
 * @Date 2018-10-08 22:51:10
 */
@Controller
@RequestMapping("/team")
public class TeamController extends BaseController {

    private String PREFIX = "/football/team/";
    @Resource
    TeamDao teamDao;


    /**
     * 跳转到球场管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "park.html";
    }


    /**
     * 获取球场列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> banners = this.teamDao.selectParks(super.getPara("roleName"));
        return super.warpObject(new ParkWarpper(banners));
    }


    /**
     * 跳转到添加广告
     */
    @RequestMapping("/banner_add")
    public String bannerAdd() {
        return PREFIX + "banner_add.html";
    }

    /**
     * 跳转到修改广告
     */
    @RequestMapping("/banner_update/{bannerId}")
    public String bannerUpdate(@PathVariable Integer bannerId, Model model) {
        return PREFIX + "banner_edit.html";
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
