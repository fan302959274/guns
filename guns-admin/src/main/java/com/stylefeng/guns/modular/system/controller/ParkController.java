package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.annotion.BussinessLog;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.dictmap.UserDict;
import com.stylefeng.guns.common.enums.AttachCategoryEnum;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.PkAttachmentMapper;
import com.stylefeng.guns.common.persistence.dao.PkParkMapper;
import com.stylefeng.guns.common.persistence.dao.mapping.PkParkRelationMapper;
import com.stylefeng.guns.common.persistence.model.PkAttachment;
import com.stylefeng.guns.common.persistence.model.PkPark;
import com.stylefeng.guns.common.persistence.model.PkParkRelation;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.ParkDao;
import com.stylefeng.guns.modular.system.dao.TeamDao;
import com.stylefeng.guns.modular.system.warpper.ParkWarpper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
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

    @Resource
    PkParkMapper pkParkMapper;

    @Resource
    TeamDao teamDao;
    @Resource
    PkAttachmentMapper pkAttachmentMapper;
    @Resource
    PkParkRelationMapper pkParkRelationMapper;
    /**
     * 跳转到球场首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "park.html";
    }

    /**
     * 跳转到添加球场
     */
    @RequestMapping("/park_add")
    public String parkAdd(Model  model) {
        List<Map<String, Object>> areaInfo = this.teamDao.getAreaInfos();
        model.addAttribute("areaInfo",areaInfo);
        return PREFIX + "park_add.html";
    }

    /**
     * 新增球场
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PkPark park ,String imgs ,String usetime) {
        this.pkParkMapper.insert(park);
        // 保存可用时间
        if (StringUtils.isNoneBlank(usetime)) {
            Arrays.asList(usetime.split(",")).forEach(s -> {
                PkParkRelation pkParkRelation = new PkParkRelation();
                pkParkRelation.setParkid(park.getId());
                pkParkRelation.setUsetime(s);
                pkParkRelationMapper.insert(pkParkRelation);
            });
        }
        // 保存附件
        if (StringUtils.isNoneBlank(imgs)) {
            Arrays.asList(imgs.split(",")).forEach(s -> {
                PkAttachment pkAttachment = new PkAttachment();
                pkAttachment.setCategory(AttachCategoryEnum.PARK.getCode());
                pkAttachment.setLinkid(park.getId());
                pkAttachment.setName(s);
                pkAttachment.setSuffix(s.substring(s.lastIndexOf(".") + 1));
                pkAttachment.setUrl(s);
                pkAttachmentMapper.insert(pkAttachment);
            });
        }
        return super.SUCCESS_TIP;
    }

    /**
     * 跳转到修改球场
     */
    @RequestMapping("/park_edit/{parkId}")
    public String bannerUpdate(@PathVariable Integer parkId, Model model) {
        if (ToolUtil.isEmpty(parkId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        List<Map<String, Object>> areaInfo = this.teamDao.getAreaInfos();
        List<Map<String, Object>> relation = this.parkDao.selectParksRelation(parkId);
        model.addAttribute("areaInfo",areaInfo);
        model.addAttribute("relation",relation);
        PkPark park = this.pkParkMapper.selectById(parkId);

        Wrapper<PkAttachment> wrapper = new EntityWrapper<>();
        wrapper = wrapper.eq("linkid", parkId).eq("category",AttachCategoryEnum.PARK.getCode());
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
        model.addAttribute("adsImg", list);
        model.addAttribute("ads", ads);
        model.addAttribute(park);
        LogObjectHolder.me().set(park);
        return PREFIX + "park_edit.html";
    }

    /**
     * 获取球场列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> banners = this.parkDao.selectParks(super.getPara("roleName"));
        return super.warpObject(new ParkWarpper(banners));
    }

    /**
     * 删除球场
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Object delete(@RequestParam Long parkId) {
        this.pkParkMapper.deleteById(parkId);
        return SUCCESS_TIP;
    }


    /**
     * 修改球场
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object update(@Valid PkPark park, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.pkParkMapper.updateById(park);
        return SUCCESS_TIP;

    }

    /**
     * 球场详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }


    /**
     * 禁用球场
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "禁用球场", key = "parkId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip freeze(@RequestParam Integer parkId) {
        if (ToolUtil.isEmpty(parkId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.parkDao.setStatus(parkId,1 );
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除禁用球场", key = "parkId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip unfreeze(@RequestParam Integer parkId) {
        if (ToolUtil.isEmpty(parkId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.parkDao.setStatus(parkId, 0);
        return SUCCESS_TIP;
    }
}
