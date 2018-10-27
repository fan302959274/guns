package com.stylefeng.guns.rest.modular.football.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.common.enums.AttachCategoryEnum;
import com.stylefeng.guns.rest.common.enums.AttachTypeEnum;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.common.util.response.CommonResp;
import com.stylefeng.guns.rest.common.util.response.ResponseCode;
import com.stylefeng.guns.rest.modular.football.transfer.PkTeamDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 区域控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@RestController
@RequestMapping("/area")
@Api(value = "AreaController|一个用来测试swagger注解的控制器")
public class AreaController {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    AreasMapper areasMapper;





    /**
     * 球队区域,约战区域
     *
     * @return
     */
    @RequestMapping(value = "/areas", method = RequestMethod.POST)
    @ApiOperation(value = "球队区域", notes = "返回码:20000成功;")
    public ResponseEntity area() {
        try {
            Wrapper<Areas> wrapper = new EntityWrapper<Areas>();
            List<Areas> list = areasMapper.selectList(wrapper);

            return ResponseEntity.ok(new CommonResp<Areas>(list));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<Areas>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }




}
