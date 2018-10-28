package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.enums.AttachCategoryEnum;
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
import com.stylefeng.guns.modular.system.dao.AdDao;
import com.stylefeng.guns.modular.system.transfer.PkAdDto;
import com.stylefeng.guns.modular.system.warpper.AdWarpper;
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
    AdDao adDao;

    @Resource
    PkAdMapper pkAdMapper;

    @Resource
    PkAttachmentMapper pkAttachmentMapper;


    /**
     * 跳转到广告管理首页
     */
    @RequestMapping("")
    public String index(String type,Model model) {
        model.addAttribute("type",type);
        return PREFIX + "ad.html";
    }

    /**
     * 跳转到添加广告
     */
    @RequestMapping("/ad_add")
    public String adAdd(String type,Model model) {
        model.addAttribute("type",type);
        return PREFIX + "ad_add.html";
    }

    /**
     * 跳转到修改广告
     */
    @RequestMapping("/ad_update/{adId}")
    public String adUpdate(@PathVariable Integer adId, Model model) {
        PkAd pkAd = pkAdMapper.selectById(adId);
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> menuMap = BeanKit.beanToMap(pkAd);
        Wrapper<PkAttachment> wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("linkid", adId).eq("category",AttachCategoryEnum.AD.getCode());
        List<PkAttachment> list = pkAttachmentMapper.selectList(wrapper);
        String ads = "";
        if (!CollectionUtils.isEmpty(list)) {
            for (PkAttachment pkAttachment : list) {
                if (StringUtils.isNoneBlank(ads)) {
                    ads = ads.concat(",").concat(pkAttachment.getUrl());
                } else {
                    ads = ads.concat(pkAttachment.getUrl());
                }
            }

        }
        menuMap.put("adsImg", list);
        menuMap.put("ads", ads);
        model.addAttribute("ad", menuMap);
        model.addAttribute("endtime", ss.format(pkAd.getEndtime()));
        model.addAttribute( "starttime", ss.format(pkAd.getStarttime()));
//        model.addAttribute(pkAd);
        LogObjectHolder.me().set(pkAd);
        return PREFIX + "ad_edit.html";
    }


    /**
     * 跳转到修改广告
     */
    @RequestMapping("/ad_view/{adId}")
    public String adView(@PathVariable Integer adId, Model model) {
        PkAd pkAd = pkAdMapper.selectById(adId);
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> menuMap = BeanKit.beanToMap(pkAd);
        Wrapper<PkAttachment> wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("linkid", adId).eq("category",AttachCategoryEnum.AD.getCode());
        List<PkAttachment> list = pkAttachmentMapper.selectList(wrapper);
        String ads = "";
        if (!CollectionUtils.isEmpty(list)) {
            for (PkAttachment pkAttachment : list) {
                if (StringUtils.isNoneBlank(ads)) {
                    ads = ads.concat(",").concat(pkAttachment.getUrl());
                } else {
                    ads = ads.concat(pkAttachment.getUrl());
                }
            }

        }
         menuMap.put("adsImg", list);
        menuMap.put("ads", ads);
        model.addAttribute("ad", menuMap);
        model.addAttribute("endtime", ss.format(pkAd.getEndtime()));
        model.addAttribute( "starttime", ss.format(pkAd.getStarttime()));
        model.addAttribute("ad", menuMap);
        LogObjectHolder.me().set(pkAd);
        return PREFIX + "ad_view.html";
    }

    /**
     * 新增广告
     */
    @RequestMapping(value = "/add")
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
                pkAttachment.setCategory(AttachCategoryEnum.AD.getCode());
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
     * 获取所有广告列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String adMainHead,Integer type) {
        List<Map<String, Object>> ads = this.adDao.selectAds(type,adMainHead);
        return super.warpObject(new AdWarpper(ads));
    }

    /**
     * 广告详情
     */
    @RequestMapping(value = "/detail/{adId}")
    @ResponseBody
    public Object detail(@PathVariable("adId") Integer adId) {
        return pkAdMapper.selectById(adId);
    }

    /**
     * 修改广告
     */
//    @BussinessLog(value = "修改部门", key = "simplename", dict = PkAd.class)
    @RequestMapping(value = "/update")
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
                pkAttachment.setCategory(AttachCategoryEnum.AD.getCode());
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
     * 删除广告
     */
//    @BussinessLog(value = "删除部门", key = "adId", dict = pkAdMapper.class)
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long adId) {
        pkAdMapper.deleteById(adId);

        return SUCCESS_TIP;
    }


}
