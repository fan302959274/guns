package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.system.dao.TeamDao;
import com.stylefeng.guns.modular.system.warpper.TeamWarpper;
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
     * 跳转到球队管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "team.html";
    }
    @RequestMapping("teamMember")
    public String teamMember() {
        return PREFIX + "team_member.html";
    }

    /**
     * 获取球队列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String name) {
        List<Map<String, Object>> banners = this.teamDao.selectTeams(super.getPara("name"));
        return super.warpObject(new TeamWarpper(banners));
    }

    /**
     * 跳转到修改球队
     */
    @RequestMapping("/banner_update/{bannerId}")
    public String bannerUpdate(@PathVariable Integer bannerId, Model model) {
        return PREFIX + "banner_edit.html";
    }


    /**
     * 删除球队
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }

    /**
     * 修改球队
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 球队详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
