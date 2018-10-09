package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.annotion.Permission;
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
            menuMap.put("headImg", list.get(0));
        }
        wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("linkid", memberId).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.IDCARD.getCode());
        if (!CollectionUtils.isEmpty(list)) {
            menuMap.put("idcardImg", list.get(0));
        }
        wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("linkid", memberId).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
        if (!CollectionUtils.isEmpty(list)) {
            menuMap.put("logoImg", list.get(0));
        }
        model.addAttribute("member", menuMap);
//        model.addAttribute(pkAd);
        LogObjectHolder.me().set(pkMember);
        return PREFIX + "member_edit.html";
    }


    /**
     * 新增广告
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(PkMemberDto pkMemberDto, @RequestParam(required = false) String headImg, @RequestParam(required = false) String idcardImg, @RequestParam(required = false) String logoImg) throws ParseException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (ToolUtil.isOneEmpty(pkMemberDto, pkMemberDto.getName())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        PkMember record = new PkMember();
        PropertyUtils.copyProperties(record, pkMemberDto);
        Integer result = this.pkMemberMapper.insert(record);
//        保存头像
        if (StringUtils.isNoneBlank(headImg)) {

            PkAttachment pkAttachment = new PkAttachment();
            pkAttachment.setCategory(AttachCategoryEnum.AD.getCode());
            pkAttachment.setLinkid(record.getId());
            pkAttachment.setName(headImg);
            pkAttachment.setSuffix(headImg.substring(headImg.lastIndexOf(".") + 1));
            pkAttachment.setUrl(headImg);
            pkAttachmentMapper.insert(pkAttachment);

        }
        //        保存身份证
        if (StringUtils.isNoneBlank(idcardImg)) {

            PkAttachment pkAttachment = new PkAttachment();
            pkAttachment.setCategory(AttachCategoryEnum.AD.getCode());
            pkAttachment.setLinkid(record.getId());
            pkAttachment.setName(idcardImg);
            pkAttachment.setSuffix(idcardImg.substring(idcardImg.lastIndexOf(".") + 1));
            pkAttachment.setUrl(idcardImg);
            pkAttachmentMapper.insert(pkAttachment);

        }
//        保存logo
        if (StringUtils.isNoneBlank(logoImg)) {

            PkAttachment pkAttachment = new PkAttachment();
            pkAttachment.setCategory(AttachCategoryEnum.AD.getCode());
            pkAttachment.setLinkid(record.getId());
            pkAttachment.setName(logoImg);
            pkAttachment.setSuffix(logoImg.substring(logoImg.lastIndexOf(".") + 1));
            pkAttachment.setUrl(logoImg);
            pkAttachmentMapper.insert(pkAttachment);

        }


        return result;
    }


    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Permission
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
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("memberId") Integer memberId) {
        return pkMemberMapper.selectById(memberId);
    }

    /**
     * 修改部门
     */
//    @BussinessLog(value = "修改部门", key = "simplename", dict = PkAd.class)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(PkMemberDto pkMemberDto, @RequestParam(required = false) String headImg, @RequestParam(required = false) String idcardImg, @RequestParam(required = false) String logoImg) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
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
        if (StringUtils.isNoneBlank(headImg)) {

            PkAttachment pkAttachment = new PkAttachment();
            pkAttachment.setCategory(AttachCategoryEnum.AD.getCode());
            pkAttachment.setLinkid(record.getId());
            pkAttachment.setName(headImg);
            pkAttachment.setSuffix(headImg.substring(headImg.lastIndexOf(".") + 1));
            pkAttachment.setUrl(headImg);
            pkAttachmentMapper.insert(pkAttachment);

        }
        //        保存身份证
        if (StringUtils.isNoneBlank(idcardImg)) {

            PkAttachment pkAttachment = new PkAttachment();
            pkAttachment.setCategory(AttachCategoryEnum.AD.getCode());
            pkAttachment.setLinkid(record.getId());
            pkAttachment.setName(idcardImg);
            pkAttachment.setSuffix(idcardImg.substring(idcardImg.lastIndexOf(".") + 1));
            pkAttachment.setUrl(idcardImg);
            pkAttachmentMapper.insert(pkAttachment);

        }
//        保存logo
        if (StringUtils.isNoneBlank(logoImg)) {

            PkAttachment pkAttachment = new PkAttachment();
            pkAttachment.setCategory(AttachCategoryEnum.AD.getCode());
            pkAttachment.setLinkid(record.getId());
            pkAttachment.setName(logoImg);
            pkAttachment.setSuffix(logoImg.substring(logoImg.lastIndexOf(".") + 1));
            pkAttachment.setUrl(logoImg);
            pkAttachmentMapper.insert(pkAttachment);

        }
        return super.SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
//    @BussinessLog(value = "删除部门", key = "adId", dict = pkAdMapper.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Long memberId) {
        pkMemberMapper.deleteById(memberId);

        return SUCCESS_TIP;
    }


}
