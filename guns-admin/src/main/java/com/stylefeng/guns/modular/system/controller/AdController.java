package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.enums.AttachEnum;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.PkAdMapper;
import com.stylefeng.guns.common.persistence.dao.PkAttachmentMapper;
import com.stylefeng.guns.common.persistence.model.PkAd;
import com.stylefeng.guns.common.persistence.model.PkAttachment;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.support.BeanKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.transfer.PkAdDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 广告控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/ad")
public class AdController extends BaseController {

    private String PREFIX = "/football/ad/";


    @Resource
    PkAdMapper pkAdMapper;
    @Resource
    PkAttachmentMapper pkAttachmentMapper;


    /**
     * 跳转到广告管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "ad.html";
    }

    /**
     * 跳转到添加广告
     */
    @RequestMapping("/ad_add")
    public String adAdd() {
        return PREFIX + "ad_add.html";
    }

    /**
     * 跳转到修改广告
     */
    @RequestMapping("/ad_update/{adId}")
    public String adUpdate(@PathVariable Integer adId, Model model) {
        PkAd pkAd = pkAdMapper.selectById(adId);

        Map<String, Object> menuMap = BeanKit.beanToMap(pkAd);
        model.addAttribute("ad", menuMap);
//        model.addAttribute(pkAd);
        LogObjectHolder.me().set(pkAd);
        return PREFIX + "ad_edit.html";
    }


    /**
     * 新增广告
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(PkAdDto pkAdDto, @RequestParam(required = false) String ads) throws ParseException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (ToolUtil.isOneEmpty(pkAdDto, pkAdDto.getMainhead())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        PkAd record = new PkAd();
        PropertyUtils.copyProperties(record, pkAdDto);
        Integer result = this.pkAdMapper.insert(record);
//        保存附件
        if (StringUtils.isNoneBlank(ads)) {
            Arrays.asList(ads.split(",")).forEach(s -> {
                PkAttachment pkAttachment = new PkAttachment();
                pkAttachment.setType(AttachEnum.AD.getCode());
                pkAttachment.setLinkid(record.getId());
                pkAttachment.setName(s);
                pkAttachment.setSuffix(s.substring(s.lastIndexOf(".") + 1));
                pkAttachment.setUrl(s);
                pkAttachmentMapper.insert(pkAttachment);
            });

        }

        return result;
    }


    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String adMainHead, Integer page, Integer pageSize) {
        RowBounds rowBounds = new RowBounds();
        Wrapper<PkAd> wrapper = new EntityWrapper<>();
        if (StringUtils.isNoneBlank(adMainHead)) {
            wrapper = wrapper.like("ad_main_head", adMainHead);
        }
        List<PkAd> list = this.pkAdMapper.selectPage(rowBounds, wrapper);
        return list;
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{adId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("adId") Integer adId) {
        return pkAdMapper.selectById(adId);
    }

    /**
     * 修改部门
     */
//    @BussinessLog(value = "修改部门", key = "simplename", dict = PkAd.class)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(PkAdDto pkAdDto, @RequestParam(required = false) String ads) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (ToolUtil.isEmpty(pkAdDto) || pkAdDto.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        PkAd record = new PkAd();
        PropertyUtils.copyProperties(record, pkAdDto);
        pkAdMapper.updateById(record);
        //1、删除
        Wrapper<PkAttachment> wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("linkid", record.getId());
        pkAttachmentMapper.delete(wrapper);
        ///2、保存附件
        if (StringUtils.isNoneBlank(ads)) {
            Arrays.asList(ads.split(",")).forEach(s -> {
                PkAttachment pkAttachment = new PkAttachment();
                pkAttachment.setType(AttachEnum.AD.getCode());
                pkAttachment.setLinkid(record.getId());
                pkAttachment.setName(s);
                pkAttachment.setSuffix(s.substring(s.lastIndexOf(".") + 1));
                pkAttachment.setUrl(s);
                pkAttachmentMapper.insert(pkAttachment);
            });

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
    public Object delete(@RequestParam Long adId) {
        pkAdMapper.deleteById(adId);

        return SUCCESS_TIP;
    }


}
