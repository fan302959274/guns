package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.enums.AttachCategoryEnum;
import com.stylefeng.guns.common.enums.AttachTypeEnum;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.PkAttachmentMapper;
import com.stylefeng.guns.common.persistence.dao.PkMemberMapper;
import com.stylefeng.guns.common.persistence.dao.PkTeamMapper;
import com.stylefeng.guns.common.persistence.model.PkAttachment;
import com.stylefeng.guns.common.persistence.model.PkMember;
import com.stylefeng.guns.common.persistence.model.PkTeam;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.support.BeanKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.MemberDao;
import com.stylefeng.guns.modular.system.dao.TeamDao;
import com.stylefeng.guns.modular.system.transfer.PkMemberDto;
import com.stylefeng.guns.modular.system.warpper.MemberWarpper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 球员控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

    private String PREFIX = "/football/member/";


    @Resource
    PkMemberMapper pkMemberMapper;
    @Resource
    PkAttachmentMapper pkAttachmentMapper;
    @Resource
    MemberDao memberDao;
    @Resource
    PkTeamMapper pkTeamMapper;
    @Resource
    TeamDao teamDao;


    /**
     * 跳转到广告管理首页
     */
    @RequestMapping("")
    public String index(String type,Model  model) {
        model.addAttribute("type",type);
        return PREFIX + "member.html";
    }

    /**
     * 跳转到添加队员
     */
    @RequestMapping("/member_add")
    public String memberAdd(String type,Model  model) {
        List<Map<String, Object>> areaInfo = this.teamDao.getAreaInfos();
        model.addAttribute("type",type);
        model.addAttribute("areaInfo",areaInfo);
        return PREFIX + "member_add.html";
    }

    /**
     * 跳转到修改队员
     */
    @RequestMapping("/member_update/{memberId}")
    public String memberUpdate(@PathVariable Integer memberId, Model model,Integer type ) {
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> teamInfo = this.teamDao.getTeamInfo(memberId,type);
        PkMember pkMember = pkMemberMapper.selectById(memberId);
        List<Map<String, Object>> areaInfo = this.teamDao.getAreaInfos();
        Map<String, Object> menuMap = BeanKit.beanToMap(pkMember);
        Wrapper<PkAttachment> wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("linkid", memberId).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.HEAD.getCode());
        List<PkAttachment> list = pkAttachmentMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(list)) {
            menuMap.put("avatar", list.get(0).getUrl());
        }
        wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("linkid", memberId).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.IDCARD.getCode());
        list = pkAttachmentMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(list)) {
            menuMap.put("idcard", list.get(0).getUrl());
        }
        if(teamInfo!=null){
            Long teamId= (Long)teamInfo.get("id");
            Wrapper<PkAttachment> teamwrapper = new EntityWrapper<>();
            teamwrapper = teamwrapper.eq("linkid", teamId).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
            List<PkAttachment> teamList = pkAttachmentMapper.selectList(teamwrapper);
            if (!CollectionUtils.isEmpty(teamList)) {
                teamInfo.put("teamLog", teamList.get(0).getUrl());
            }
        }
        model.addAttribute("member", menuMap);
        model.addAttribute("areaInfo", areaInfo);
        model.addAttribute("teamInfo", teamInfo);
        model.addAttribute("birth", Objects.nonNull(pkMember.getBirth())?ss.format(pkMember.getBirth()):"");
        LogObjectHolder.me().set(pkMember);
        return PREFIX + "member_edit.html";
    }

    /**
     * 跳转到修改队员
     */
    @RequestMapping("/member_view/{memberId}")
    public String memberView(@PathVariable Integer memberId, Model model,String type) {

        Map<String, Object> teamInfo = this.teamDao.getTeamInfo(memberId,Integer.valueOf(type));
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");
        PkMember pkMember = pkMemberMapper.selectById(memberId);

        Map<String, Object> menuMap = BeanKit.beanToMap(pkMember);
        Wrapper<PkAttachment> wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("linkid", memberId).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.HEAD.getCode());
        List<PkAttachment> list = pkAttachmentMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(list)) {
            menuMap.put("avatar", list.get(0).getUrl());
        }
        wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("linkid", memberId).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.IDCARD.getCode());
        list = pkAttachmentMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(list)) {
            menuMap.put("idcard", list.get(0).getUrl());
        }

        if(teamInfo!=null){
            Long teamId= (Long)teamInfo.get("id");
            Wrapper<PkAttachment> teamwrapper = new EntityWrapper<>();
            teamwrapper = teamwrapper.eq("linkid", teamId).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
            List<PkAttachment> teamList = pkAttachmentMapper.selectList(teamwrapper);
            if (!CollectionUtils.isEmpty(teamList)) {
                teamInfo.put("teamLog", teamList.get(0).getUrl());
            }
        }
        model.addAttribute("member", menuMap);
        model.addAttribute("type", type);
        model.addAttribute("teamInfo", teamInfo);
        model.addAttribute("birth", Objects.nonNull(pkMember.getBirth())?ss.format(pkMember.getBirth()):"");
        LogObjectHolder.me().set(pkMember);
        return PREFIX + "member_view.html";
    }


    /**
     * 新增队员
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PkMemberDto pkMemberDto, PkTeam pkTeam) throws ParseException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (ToolUtil.isOneEmpty(pkMemberDto, pkMemberDto.getName())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        PkMember record = new PkMember();
        PropertyUtils.copyProperties(record, pkMemberDto);
        Integer result = this.pkMemberMapper.insert(record);
//        保存头像
        if (StringUtils.isNoneBlank(pkMemberDto.getAvatar())) {

            PkAttachment pkAttachment = new PkAttachment();
            pkAttachment.setCategory(AttachCategoryEnum.MEMBER.getCode());
            pkAttachment.setType(AttachTypeEnum.HEAD.getCode());
            pkAttachment.setLinkid(record.getId());
            pkAttachment.setName(pkMemberDto.getAvatar());
            pkAttachment.setSuffix(pkMemberDto.getAvatar().substring(pkMemberDto.getAvatar().lastIndexOf(".") + 1));
            pkAttachment.setUrl(pkMemberDto.getAvatar());
            pkAttachmentMapper.insert(pkAttachment);

        }
        //        保存身份证
        if (StringUtils.isNoneBlank(pkMemberDto.getIdcard())) {
            PkAttachment pkAttachment = new PkAttachment();
            pkAttachment.setCategory(AttachCategoryEnum.MEMBER.getCode());
            pkAttachment.setType(AttachTypeEnum.IDCARD.getCode());
            pkAttachment.setLinkid(record.getId());
            pkAttachment.setName(pkMemberDto.getIdcard());
            pkAttachment.setSuffix(pkMemberDto.getIdcard().substring(pkMemberDto.getIdcard().lastIndexOf(".") + 1));
            pkAttachment.setUrl(pkMemberDto.getIdcard());
            pkAttachmentMapper.insert(pkAttachment);
        }

        if (StringUtils.isNoneBlank(pkMemberDto.getTeamname())) {
            pkTeam.setName(pkMemberDto.getTeamname());
            pkTeam.setOwnerid(record.getId());
            pkTeam.setArea(pkMemberDto.getArea());
            pkTeamMapper.insert(pkTeam);
            //新增球隊信息
            if (StringUtils.isNoneBlank(pkMemberDto.getTeamlogo())) {
                PkAttachment pkAttachment = new PkAttachment();
                pkAttachment.setCategory(AttachCategoryEnum.TEAM.getCode());
                pkAttachment.setType(AttachTypeEnum.LOGO.getCode());
                pkAttachment.setLinkid(pkTeam.getId());
                pkAttachment.setName("球队logo");
                pkAttachment.setSuffix(pkMemberDto.getTeamlogo().substring(pkMemberDto.getTeamlogo().lastIndexOf(".") + 1));
                pkAttachment.setUrl(pkMemberDto.getTeamlogo());
                pkAttachmentMapper.insert(pkAttachment);
            }
        }
        return result;
    }


    /**
     * 获取所有队员列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String account,String type) {
        List<Map<String, Object>> banners = this.memberDao.selectMembers(account,type);
        return super.warpObject(new MemberWarpper(banners));
    }

    /**
     * 队员详情
     */
    @RequestMapping(value = "/detail/{memberId}")
    @ResponseBody
    public Object detail(@PathVariable("memberId") Integer memberId) {
        return pkMemberMapper.selectById(memberId);
    }

    /**
     * 修改队员信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PkMemberDto pkMemberDto) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (ToolUtil.isEmpty(pkMemberDto) || pkMemberDto.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        PkMember record = new PkMember();
        PropertyUtils.copyProperties(record, pkMemberDto);
        pkMemberMapper.updateById(record);

        if(pkMemberDto.getTeamId()!=null){
            PkTeam pkTeam = new PkTeam();
            pkTeam.setId(pkMemberDto.getTeamId());
            pkTeam.setName(pkMemberDto.getTeamname());
            pkTeam.setArea(pkMemberDto.getArea());
            pkTeamMapper.updateById(pkTeam);
            //1、删除
            Wrapper<PkAttachment> wrappers = new EntityWrapper<>();
            wrappers = wrappers.eq("linkid", pkMemberDto.getTeamId());
            wrappers = wrappers.eq("category", AttachCategoryEnum.TEAM.getCode());
            pkAttachmentMapper.delete(wrappers);
            //新增球隊信息
            if (StringUtils.isNoneBlank(pkMemberDto.getTeamlogo())) {
                PkAttachment pkAttachment = new PkAttachment();
                pkAttachment.setCategory(AttachCategoryEnum.TEAM.getCode());
                pkAttachment.setType(AttachTypeEnum.LOGO.getCode());
                pkAttachment.setLinkid(pkMemberDto.getTeamId());
                pkAttachment.setName("球队logo");
                pkAttachment.setSuffix(pkMemberDto.getTeamlogo().substring(pkMemberDto.getTeamlogo().lastIndexOf(".") + 1));
                pkAttachment.setUrl(pkMemberDto.getTeamlogo());
                pkAttachmentMapper.insert(pkAttachment);
            }
        }



        //1、删除
        Wrapper<PkAttachment> wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("linkid", record.getId());
        pkAttachmentMapper.delete(wrapper);
        //        保存头像
        if (StringUtils.isNoneBlank(pkMemberDto.getAvatar())) {

            PkAttachment pkAttachment = new PkAttachment();
            pkAttachment.setCategory(AttachCategoryEnum.MEMBER.getCode());
            pkAttachment.setType(AttachTypeEnum.HEAD.getCode());
            pkAttachment.setLinkid(record.getId());
            pkAttachment.setName(pkMemberDto.getAvatar());
            pkAttachment.setSuffix(pkMemberDto.getAvatar().substring(pkMemberDto.getAvatar().lastIndexOf(".") + 1));
            pkAttachment.setUrl(pkMemberDto.getAvatar());
            pkAttachmentMapper.insert(pkAttachment);

        }
        //        保存身份证
        if (StringUtils.isNoneBlank(pkMemberDto.getIdcard())) {

            PkAttachment pkAttachment = new PkAttachment();
            pkAttachment.setCategory(AttachCategoryEnum.MEMBER.getCode());
            pkAttachment.setType(AttachTypeEnum.IDCARD.getCode());
            pkAttachment.setLinkid(record.getId());
            pkAttachment.setName(pkMemberDto.getIdcard());
            pkAttachment.setSuffix(pkMemberDto.getIdcard().substring(pkMemberDto.getIdcard().lastIndexOf(".") + 1));
            pkAttachment.setUrl(pkMemberDto.getIdcard());
            pkAttachmentMapper.insert(pkAttachment);

        }
        return super.SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long memberId) {
        pkMemberMapper.deleteById(memberId);

        return SUCCESS_TIP;
    }

    /**
     * 根据账号查询是否重复
     */
    @RequestMapping(value = "/iseExistAcount")
    @ResponseBody
    public boolean iseExistAcount(@RequestParam String account ,Long id) {
        if (id != null) {
            PkMember oldpkMember = pkMemberMapper.selectById(id);
            if(account.equals(oldpkMember.getAccount())){
                return true;
            }else{
                PkMember pkMember = this.memberDao.selectMemberByAcount(account);
                return pkMember!=null?false:true;
            }
        }
        PkMember pkMember = this.memberDao.selectMemberByAcount(account);
        return pkMember!=null?false:true;
    }

}
