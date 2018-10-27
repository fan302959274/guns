package com.stylefeng.guns.rest.modular.football.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.common.persistence.dao.DictMapper;
import com.stylefeng.guns.rest.common.persistence.model.Dict;
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
 * 约战控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@RestController
@RequestMapping("/match")
@Api(value = "MatchController|一个用来测试swagger注解的控制器")
public class MatchController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DictMapper dictMapper;


    /**
     * 约战时间段
     *
     * @return
     */
    @RequestMapping(value = "/times", method = RequestMethod.POST)
    @ApiOperation(value = "约战时间段", notes = "返回码:20000成功;")
    public ResponseEntity times() {
        try {
            Wrapper<Dict> wrapper = new EntityWrapper<Dict>();
            wrapper.eq("pid", "43");
            List<Dict> list = dictMapper.selectList(wrapper);

            return ResponseEntity.ok(new CommonResp<Dict>(list));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<Dict>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


}
