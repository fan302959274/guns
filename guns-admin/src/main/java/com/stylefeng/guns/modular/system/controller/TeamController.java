package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.annotion.BussinessLog;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.dictmap.UserDict;
import com.stylefeng.guns.common.enums.AttachCategoryEnum;
import com.stylefeng.guns.common.enums.AttachTypeEnum;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.PkAttachmentMapper;
import com.stylefeng.guns.common.persistence.dao.PkTeamMapper;
import com.stylefeng.guns.common.persistence.dao.PkTeamMemberMapper;
import com.stylefeng.guns.common.persistence.model.PkAttachment;
import com.stylefeng.guns.common.persistence.model.PkTeam;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.support.BeanKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.TeamDao;
import com.stylefeng.guns.modular.system.warpper.TeamWarpper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
    @Resource
    PkAttachmentMapper pkAttachmentMapper;
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
    /**
     * 获取球队成员列表
     */
    @RequestMapping(value = "/memberList")
    @ResponseBody
    public Object memberList(Long teamId) {
        List<Map<String, Object>> members = this.teamDao.selectTeamsMembers(teamId);
        return super.warpObject(new TeamWarpper(members));
    }
    /**
     * 跳转到修改球队
     */
    @RequestMapping("/team_edit/{teamId}")
    public String toUpdateTeam(@PathVariable Integer teamId, Model model) {
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PkTeam  pkTeam = pkTeamMapper.selectById(teamId);
        Map<String, Object> teamMap = BeanKit.beanToMap(pkTeam);
        Wrapper<PkAttachment> teamwrapper = new EntityWrapper<>();
        teamwrapper = teamwrapper.eq("linkid", teamId).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
        List<PkAttachment> teamList = pkAttachmentMapper.selectList(teamwrapper);
        if (!CollectionUtils.isEmpty(teamList)) {
            teamMap.put("teamLog", teamList.get(0).getUrl());
        }
        teamMap.put("createTime",ss.format(pkTeam.getCreatedate()));
        model.addAttribute("team", teamMap);
        return PREFIX + "team_edit.html";
    }


    /**
     * 删除球队
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Object delete(@RequestParam Long teamId) {
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
    public Object update(PkTeam  pkTeam,String logo) {
        if (ToolUtil.isEmpty(pkTeam) || pkTeam.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        PkTeam pkTeams=pkTeamMapper.selectById(pkTeam.getId());
        pkTeam.setPoint(pkTeams.getPoint()+(pkTeam.getStartpoint()-pkTeams.getStartpoint()));
        pkTeamMapper.updateById(pkTeam);
        //1、删除
        Wrapper<PkAttachment> wrappers = new EntityWrapper<>();
        wrappers = wrappers.eq("linkid", pkTeam.getId());
        wrappers = wrappers.eq("category", AttachCategoryEnum.TEAM.getCode());
        pkAttachmentMapper.delete(wrappers);
        //新增球隊信息logo
        if (StringUtils.isNoneBlank(logo)) {
            PkAttachment pkAttachment = new PkAttachment();
            pkAttachment.setCategory(AttachCategoryEnum.TEAM.getCode());
            pkAttachment.setType(AttachTypeEnum.LOGO.getCode());
            pkAttachment.setLinkid(pkTeam.getId());
            pkAttachment.setName("球队logo");
            pkAttachment.setSuffix(logo.substring(logo.lastIndexOf(".") + 1));
            pkAttachment.setUrl(logo);
            pkAttachmentMapper.insert(pkAttachment);
        }
        return super.SUCCESS_TIP;
    }

    /**
     * 球队详情
     */
    @RequestMapping(value = "/detail/{teamId}")
    public String detail(@PathVariable Integer teamId, Model model) {
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PkTeam  pkTeam = pkTeamMapper.selectById(teamId);
        Map<String, Object> teamMap = BeanKit.beanToMap(pkTeam);
        Wrapper<PkAttachment> teamwrapper = new EntityWrapper<>();
        teamwrapper = teamwrapper.eq("linkid", teamId).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
        List<PkAttachment> teamList = pkAttachmentMapper.selectList(teamwrapper);
        if (!CollectionUtils.isEmpty(teamList)) {
            teamMap.put("teamLog", teamList.get(0).getUrl());
        }
        model.addAttribute("createTime",ss.format(pkTeam.getCreatedate()));
        model.addAttribute("team", teamMap);

        return PREFIX +"team_view.html";
    }

    /**
     * 根据账号查询是否重复
     */
    @RequestMapping(value = "/isExistTeamName")
    @ResponseBody
    public boolean isExistTeamName(@RequestParam String name ,Long id) {
        if (id != null) {
            PkTeam team = pkTeamMapper.selectById(id);
            if(name.equals(team.getName())){
                return true;
            }else{
                PkTeam teams = this.teamDao.selectTeamByName(name);
                return teams!=null?false:true;
            }
        }
        PkTeam teams = this.teamDao.selectTeamByName(name);
        return teams!=null?false:true;
    }


    /**
     * 禁用球场
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "禁用球队", key = "teamId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip freeze(@RequestParam Integer teamId) {
        if (ToolUtil.isEmpty(teamId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.teamDao.setStatus(teamId,1 );
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除禁用球场", key = "parkId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip unfreeze(@RequestParam Integer teamId) {
        if (ToolUtil.isEmpty(teamId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.teamDao.setStatus(teamId,0 );
        return SUCCESS_TIP;
    }
}
