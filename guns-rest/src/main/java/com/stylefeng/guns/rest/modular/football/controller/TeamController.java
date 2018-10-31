package com.stylefeng.guns.rest.modular.football.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.common.enums.AttachCategoryEnum;
import com.stylefeng.guns.rest.common.enums.AttachTypeEnum;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.common.util.response.CommonListResp;
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
    @Autowired
    PkMemberMapper pkMemberMapper;
    @Autowired
    AreasMapper areasMapper;
    @Autowired
    PkTeamMemberMapper pkTeamMemberMapper;
    @Autowired
    PkMatchMapper pkMatchMapper;


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
            return ResponseEntity.ok(new CommonListResp<PkTeam>(pkTeams));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<PkTeam>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
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
            Assert.notNull(pkTeamDto.getOpenid(), "openid不能为空");
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", pkTeamDto.getOpenid());
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");
            PkTeam pkTeam = new PkTeam();
            PropertyUtils.copyProperties(pkTeam, pkTeamDto);
            pkTeam.setOwnerid(pkMembers.get(0).getId());
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
            return ResponseEntity.ok(new CommonResp<PkTeam>(pkTeam));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<PkTeam>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
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
            PkTeam pkTeam = pkTeamMapper.selectById(id);
            Assert.notNull(pkTeam, "不存在该球队");
            return ResponseEntity.ok(new CommonResp<PkTeam>(pkTeamMapper.selectById(id)));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<PkTeam>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * 加入球队
     *
     * @param teamid
     * @return
     */
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    @ApiOperation(value = "加入球队", notes = "返回码:20000成功;")
    @ApiImplicitParam(paramType = "query", name = "teamid", value = "球队id", required = true, dataType = "Long")
    public ResponseEntity join(@RequestParam Long teamid, @RequestParam String openid) {
        log.info("加入球队请求参数为:{}", teamid);
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");
            PkTeamMember pkTeamMember = new PkTeamMember();
            pkTeamMember.setMemberid(pkMembers.get(0).getId());
            pkTeamMember.setTeamid(teamid);
            pkTeamMemberMapper.insert(pkTeamMember);

            return ResponseEntity.ok(new CommonResp<PkTeamMember>(pkTeamMember));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<PkTeamMember>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * 球队区域
     *
     * @return
     */
    @RequestMapping(value = "/area", method = RequestMethod.POST)
    @ApiOperation(value = "球队区域", notes = "返回码:20000成功;")
    public ResponseEntity area() {
        try {
            Wrapper<Areas> wrapper = new EntityWrapper<Areas>();
            List<Areas> list = areasMapper.selectList(wrapper);

            return ResponseEntity.ok(new CommonListResp<Areas>(list));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Areas>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * 查询约战资格
     *
     * @return
     */
    @RequestMapping(value = "/qualify", method = RequestMethod.POST)
    @ApiOperation(value = "球队约战资格", notes = "返回码:20000成功;")
    public ResponseEntity qualify(@RequestParam Long teamid, @RequestParam String openid) {
        try {
            PkTeam pkTeam = pkTeamMapper.selectById(teamid);
            Assert.notNull(pkTeam, "未获取到球队");
            Integer matchSum = (null == pkTeam.getWinnum() ? 0 : pkTeam.getWinnum()) + (null == pkTeam.getDebtnum() ? 0 : pkTeam.getDebtnum()) + (null == pkTeam.getDrawnum() ? 0 : pkTeam.getDrawnum());
            if (matchSum < 5) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "队伍比赛未满5次"));
            }
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            if (CollectionUtils.isEmpty(pkMembers)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "openid未获取到用户"));
            }
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            if (!"1".equals(pkMembers.get(0).getType())) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "该队员不是队长"));
            }
            //东道主
            Wrapper<PkMatch> pkMatchWrapperHost = new EntityWrapper<PkMatch>();
            pkMatchWrapperHost = pkMatchWrapperHost.eq("hostteamid", teamid);
            List<PkMatch> pkMatchesHost = pkMatchMapper.selectList(pkMatchWrapperHost);
            if (CollectionUtils.isNotEmpty(pkMatchesHost)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "约战中"));
            }
            //被挑战
            Wrapper<PkMatch> pkMatchWrapperChallenge = new EntityWrapper<PkMatch>();
            pkMatchWrapperChallenge = pkMatchWrapperChallenge.eq("challengeteamid", teamid);
            List<PkMatch> pkMatchesChallenge = pkMatchMapper.selectList(pkMatchWrapperChallenge);
            if (CollectionUtils.isNotEmpty(pkMatchesChallenge)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "约战中"));
            }


            return ResponseEntity.ok(new CommonResp<String>("可约战"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


}
