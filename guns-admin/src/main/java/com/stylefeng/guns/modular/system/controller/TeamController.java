package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.persistence.dao.PkTeamMapper;
import com.stylefeng.guns.common.persistence.dao.PkTeamMemberMapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.system.dao.TeamDao;
import com.stylefeng.guns.modular.system.warpper.TeamWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Resource
    PkTeamMapper pkTeamMapper;
    @Resource
    PkTeamMemberMapper pkTeamMemberMapper;
    /**
     * 跳转到球队管理首页
     */
    @RequestMapping("")
    public String index() {

        return PREFIX + "team.html";
    }
    @RequestMapping("teamMember/{teamId}")
    public String teamMember(Model model,Long teamId) {
        model.addAttribute("teamId",teamId);
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

    @RequestMapping(value = "/memberList")
    @ResponseBody
    public Object memberList(Long teamId) {
        List<Map<String, Object>> members = this.teamDao.selectTeamsMembers(teamId);
        return super.warpObject(new TeamWarpper(members));
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
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Object delete(@RequestParam Integer teamId) {
        this.pkTeamMapper.deleteById(teamId);
        this.teamDao.deleteTeamMembers(teamId);
        return SUCCESS_TIP;
    }

    /**
     * 删除球队成员
     */
    @RequestMapping(value = "/removeMember")
    @ResponseBody
    public Object deleteMember(@RequestParam Long id) {
        this.pkTeamMemberMapper.deleteById(id);
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
