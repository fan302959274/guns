package com.stylefeng.guns.rest.modular.football.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.common.enums.AttachCategoryEnum;
import com.stylefeng.guns.rest.common.enums.AttachTypeEnum;
import com.stylefeng.guns.rest.common.persistence.dao.PkAttachmentMapper;
import com.stylefeng.guns.rest.common.persistence.dao.PkTeamMapper;
import com.stylefeng.guns.rest.common.persistence.model.PkAttachment;
import com.stylefeng.guns.rest.common.persistence.model.PkTeam;
import com.stylefeng.guns.rest.common.util.response.CommonResp;
import com.stylefeng.guns.rest.common.util.response.ResponseCode;
import com.stylefeng.guns.rest.modular.football.transfer.PkTeamDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 球队控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@RestController
@RequestMapping("/team")
@Api(value = "TeamController|一个用来测试swagger注解的控制器")
public class TeamController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PkTeamMapper pkTeamMapper;
    @Autowired
    PkAttachmentMapper pkAttachmentMapper;

    /**
     * 查询球队
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "查询球队", notes = "返回码:20000成功;")
    @ApiImplicitParam(paramType = "query", name = "name", value = "球队名称", required = true, dataType = "String")
    public ResponseEntity register(@RequestParam String name) {
        log.info("查询球队参数为:{}", name);
        try {
            Wrapper<PkTeam> wrapper = new EntityWrapper<PkTeam>();
            wrapper = wrapper.like("name", name);
            List<PkTeam> pkTeams = pkTeamMapper.selectList(wrapper);
            return ResponseEntity.ok(new CommonResp<PkTeam>(pkTeams));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<PkTeam>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * 添加球队
     *
     * @param pkTeamDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加球队", notes = "返回码:20000成功;")
    @ApiImplicitParam(paramType = "body", name = "pkTeamDto", value = "球队实体", required = true, dataType = "PkTeamDto")
    public ResponseEntity valid(@RequestBody PkTeamDto pkTeamDto) {
        log.info("添加球队请求参数为:{}", JSONObject.toJSONString(pkTeamDto));
        try {
            Assert.notNull(pkTeamDto.getName(), "名称不能为空");
            PkTeam pkTeam = new PkTeam();
            PropertyUtils.copyProperties(pkTeam, pkTeamDto);
            pkTeamMapper.insert(pkTeam);
            //        保存logo
            if (StringUtils.isNoneBlank(pkTeamDto.getLogo())) {
                PkAttachment pkAttachment = new PkAttachment();
                pkAttachment.setCategory(AttachCategoryEnum.TEAM.getCode());
                pkAttachment.setType(AttachTypeEnum.LOGO.getCode());
                pkAttachment.setLinkid(pkTeam.getId());
                pkAttachment.setName(pkTeamDto.getLogo());
                pkAttachment.setSuffix(pkTeamDto.getLogo().substring(pkTeamDto.getLogo().lastIndexOf(".") + 1));
                pkAttachment.setUrl(pkTeamDto.getLogo());
                pkAttachmentMapper.insert(pkAttachment);
            }
            return ResponseEntity.ok(new CommonResp<PkTeamDto>(pkTeamDto));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<PkTeamDto>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * 球队详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "球队详情", notes = "返回码:20000成功;")
    @ApiImplicitParam(paramType = "query", name = "id", value = "球队id", required = true, dataType = "String")
    public ResponseEntity detail(@PathVariable String id) {
        log.info("球队详情参数为:{}", id);
        try {
            return ResponseEntity.ok(new CommonResp<PkTeam>(pkTeamMapper.selectById(id)));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<PkTeam>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

//    /**
//     * 加入球队
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/join", method = RequestMethod.POST)
//    @ApiOperation(value = "加入球队", notes = "返回码:20000成功;")
//    @ApiImplicitParam(paramType = "query", name = "id", value = "球队id", required = true, dataType = "String")
//    public ResponseEntity join(@RequestParam String id) {
//        log.info("球队详情参数为:{}", id);
//        try {
//            return ResponseEntity.ok(new CommonResp<PkTeam>(pkTeamMapper.selectById(id)));
//        } catch (Exception e) {
//            return ResponseEntity.ok(new CommonResp<PkTeam>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
//        }
//
//    }


}
