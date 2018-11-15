package com.stylefeng.guns.rest.modular.football.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.common.persistence.dao.AreasMapper;
import com.stylefeng.guns.rest.common.persistence.dao.CitiesMapper;
import com.stylefeng.guns.rest.common.persistence.model.Areas;
import com.stylefeng.guns.rest.common.persistence.model.Cities;
import com.stylefeng.guns.rest.common.util.response.CommonListResp;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    CitiesMapper citiesMapper;


    /**
     * 球队区域
     *
     * @return
     */
    @RequestMapping(value = "/cities", method = RequestMethod.POST)
    @ApiOperation(value = "球队区域", notes = "返回码:1成功;")
    public ResponseEntity cities() {
        try {
            Wrapper<Cities> wrapper = new EntityWrapper<Cities>();
            List<Cities> list = citiesMapper.selectList(wrapper);
            List<Map> datas = new ArrayList<>();
            list.forEach(cities -> {
                Map map = new HashMap();
                map.put("cityid", cities.getId());
                map.put("cityName", cities.getCity());
                map.put("cityIntro", cities.getCity());
                datas.add(map);
            });

            return ResponseEntity.ok(new CommonListResp<Map>(datas));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Areas>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * 约战区域
     *
     * @return
     */
    @RequestMapping(value = "/areas", method = RequestMethod.POST)
    @ApiOperation(value = "球队区域", notes = "返回码:1成功;")
    public ResponseEntity area() {
        try {
            Wrapper<Areas> wrapper = new EntityWrapper<Areas>();
            List<Areas> list = areasMapper.selectList(wrapper);
            List<Map> datas = new ArrayList<>();
            list.forEach(areas -> {
                Map map = new HashMap();
                map.put("areaid", areas.getId());
                map.put("areaName", areas.getArea());
                map.put("areaIntro", areas.getArea());
                datas.add(map);
            });

            return ResponseEntity.ok(new CommonListResp<Map>(datas));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Areas>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


}
