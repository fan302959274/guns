package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.enums.AttachCategoryEnum;
import com.stylefeng.guns.common.enums.AttachTypeEnum;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.PkAttachmentMapper;
import com.stylefeng.guns.common.persistence.dao.PkMemberMapper;
import com.stylefeng.guns.common.persistence.model.PkAttachment;
import com.stylefeng.guns.common.persistence.model.PkMember;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.support.BeanKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.transfer.PkMemberDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import java.util.List;
import java.util.Map;

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


    /**
     * 跳转到广告管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "member.html";
    }

    /**
     * 跳转到添加广告
     */
    @RequestMapping("/member_add")
    public String memberAdd() {
        return PREFIX + "member_add.html";
    }

    /**
     * 跳转到修改广告
     */
    @RequestMapping("/member_update/{memberId}")
    public String memberUpdate(@PathVariable Integer memberId, Model model) {
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
        model.addAttribute("member", menuMap);
        LogObjectHolder.me().set(pkMember);
        return PREFIX + "member_edit.html";
    }

    /**
     * 跳转到修改广告
     */
    @RequestMapping("/member_view/{memberId}")
    public String memberView(@PathVariable Integer memberId, Model model) {
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
        model.addAttribute("member", menuMap);
        LogObjectHolder.me().set(pkMember);
        return PREFIX + "member_view.html";
    }


    /**
     * 新增广告
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PkMemberDto pkMemberDto) throws ParseException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
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


        return result;
    }


    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String name, Integer page, Integer pageSize) {
        RowBounds rowBounds = new RowBounds();
        Wrapper<PkMember> wrapper = new EntityWrapper<>();
        if (StringUtils.isNoneBlank(name)) {
            wrapper = wrapper.like("name", name);
        }
        List<PkMember> list = this.pkMemberMapper.selectPage(rowBounds, wrapper);
        return list;
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{memberId}")
    @ResponseBody
    public Object detail(@PathVariable("memberId") Integer memberId) {
        return pkMemberMapper.selectById(memberId);
    }

    /**
     * 修改部门
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


}
