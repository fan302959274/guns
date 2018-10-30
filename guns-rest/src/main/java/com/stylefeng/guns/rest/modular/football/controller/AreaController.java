package com.stylefeng.guns.rest.modular.football.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.common.persistence.dao.AreasMapper;
import com.stylefeng.guns.rest.common.persistence.model.Areas;
import com.stylefeng.guns.rest.common.util.response.CommonResp;
import com.stylefeng.guns.rest.common.util.response.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
