package com.stylefeng.guns.rest.modular.football.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.enums.AttachCategoryEnum;
import com.stylefeng.guns.rest.common.persistence.dao.PkAdMapper;
import com.stylefeng.guns.rest.common.persistence.dao.PkAttachmentMapper;
import com.stylefeng.guns.rest.common.persistence.model.PkAd;
import com.stylefeng.guns.rest.common.persistence.model.PkAttachment;
import com.stylefeng.guns.rest.common.util.response.CommonResp;
import com.stylefeng.guns.rest.common.util.response.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@RestController
@RequestMapping("/ad")
@Api(value = "AdController|一个用来测试swagger注解的控制器")
public class AdController {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    PkAdMapper pkAdMapper;
    @Autowired
    PkAttachmentMapper pkAttachmentMapper;


    /**
     * 首页轮转图
     *
     * @return
     */
    @RequestMapping(value = "/lunboInfo", method = RequestMethod.POST)
    @ApiOperation(value = "轮播图接口", notes = "返回码:20000成功;")
    public ResponseEntity lunboInfo(@RequestParam String type) {
        log.info("轮播图接口请求参数{}", type);
        List<Map> results = new ArrayList<>();
        try {
            Assert.notNull(type, "轮播图类型不能为空");
            Wrapper<PkAd> wrapper = new EntityWrapper<PkAd>();
            wrapper.eq("type", type);
            List<PkAd> list = pkAdMapper.selectList(wrapper);
            list.forEach(pkAd -> {
                Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<PkAttachment>();
                pkAttachmentWrapper.eq("linkid", pkAd.getId()).eq("category", AttachCategoryEnum.AD.getCode());
                List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
                attachmentList.forEach(pkAttachment -> {
                    Map map = new HashMap();
                    map.put("image", pkAttachment.getUrl());
                    map.put("url", pkAd.getUrl());
                    results.add(map);
                });
            });

            return ResponseEntity.ok(new CommonResp<Map>(results));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * 联盟活动接口
     *
     * @return
     */
    @RequestMapping(value = "/activityList", method = RequestMethod.POST)
    @ApiOperation(value = "联盟活动接口", notes = "返回码:20000成功;")
    public ResponseEntity activityList(@RequestParam Integer page) {
        log.info("联盟活动接口请求参数{}", page);
        List<Map> results = new ArrayList<>();
        try {
            Wrapper<PkAd> wrapper = new EntityWrapper<PkAd>();
            wrapper.eq("type", 2);
            List<PkAd> list = pkAdMapper.selectList(wrapper);
            list.forEach(pkAd -> {
                Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<PkAttachment>();
                pkAttachmentWrapper.eq("linkid", pkAd.getId()).eq("category", AttachCategoryEnum.AD.getCode());
                List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
                attachmentList.forEach(pkAttachment -> {
                    Map map = new HashMap();
                    map.put("image", pkAttachment.getUrl());
                    map.put("name", pkAd.getMainhead());
                    map.put("date", DateUtil.formatDate(pkAd.getStarttime(), "YYYY-MM-dd") + "至" + DateUtil.formatDate(pkAd.getEndtime(), "YYYY-MM-dd"));
                    map.put("url", pkAd.getUrl());
                    results.add(map);
                });
            });

            return ResponseEntity.ok(new CommonResp<Map>(results));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


}
