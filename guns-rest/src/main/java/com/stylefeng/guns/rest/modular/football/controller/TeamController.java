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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /**
     * 查询球队
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "查询球队", notes = "返回码:1成功;")
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
    @ApiOperation(value = "添加球队", notes = "返回码:1成功;")
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
            PkMember pkMember = pkMembers.get(0);
            pkMember.setType("1");
            pkMemberMapper.updateById(pkMember);//更新创建球队的人为队长
            PkTeam pkTeam = new PkTeam();
            PropertyUtils.copyProperties(pkTeam, pkTeamDto);
            pkTeam.setOwnerid(pkMember.getId());
            pkTeam.setStartpoint(0);//初始积分
            pkTeam.setPoint(0);//初始积分
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
            //保存球队球员对应关系
            PkTeamMember pkTeamMember = new PkTeamMember();
            pkTeamMember.setStatus("1");
            pkTeamMember.setTeamid(pkTeam.getId());
            pkTeamMember.setMemberid(pkMember.getId());
            pkTeamMemberMapper.insert(pkTeamMember);

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
    @ApiOperation(value = "球队详情", notes = "返回码:1成功;")
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
    @RequestMapping(value = "/appleList", method = RequestMethod.POST)
    @ApiOperation(value = "加入球队", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "teamid", value = "球队id", required = true, dataType = "Long")
    public ResponseEntity appleList(@RequestParam Long teamid, @RequestParam String openid) {
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

            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("teamid", teamid);
            List<PkTeamMember> pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);

            List<Map> datas = new ArrayList<>();
            pkTeamMembers.forEach(teamMember -> {
                Map data = new HashMap();
                PkMember pkMember = pkMemberMapper.selectById(teamMember.getMemberid());
                data.put("manid", pkMember.getOpenid());
                data.put("manName", pkMember.getName());
                data.put("manPlayer", pkMember.getPosition());

                Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<>();
                pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkMember.getId()).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.HEAD.getCode());
                List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
                if (!org.apache.commons.collections.CollectionUtils.isEmpty(attachmentList)) {
                    data.put("manImage", attachmentList.get(0).getUrl());
                }
                datas.add(data);
            });
            return ResponseEntity.ok(new CommonListResp<Map>(datas));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * 拒绝加入球队
     *
     * @param teamid
     * @return
     */
    @RequestMapping(value = "/deleteApple", method = RequestMethod.POST)
    @ApiOperation(value = "加入球队", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "teamid", value = "球队id", required = true, dataType = "Long")
    public ResponseEntity deleteApple(@RequestParam Long teamid, @RequestParam String openid, @RequestParam String manid) {
        log.info("拒绝加入球队请求参数为:{}", teamid);
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("teamid", teamid).eq("memberid", pkMembers.get(0).getId());
            pkTeamMemberMapper.delete(pkTeamMemberWrapper);

            return ResponseEntity.ok(new CommonResp<String>("拒绝成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * 同意加入球队
     *
     * @param teamid
     * @return
     */
    @RequestMapping(value = "/agreeApple", method = RequestMethod.POST)
    @ApiOperation(value = "同意加入球队", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "teamid", value = "球队id", required = true, dataType = "Long")
    public ResponseEntity agreeApple(@RequestParam Long teamid, @RequestParam String openid, @RequestParam String manid) {
        log.info("同意加入球队请求参数为:{}", teamid);
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("teamid", teamid).eq("memberid", pkMembers.get(0).getId());
            List<PkTeamMember> pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);

            PkTeamMember pkTeamMember = pkTeamMembers.get(0);
            pkTeamMember.setStatus("1");//同意

            pkTeamMemberMapper.updateById(pkTeamMember);
            return ResponseEntity.ok(new CommonResp<String>("同意成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * 解散球队，退出球队接口
     *
     * @param teamid
     * @return
     */
    @RequestMapping(value = "/exitTeam", method = RequestMethod.POST)
    @ApiOperation(value = "解散球队，退出球队接口", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "teamid", value = "球队id", required = true, dataType = "Long")
    public ResponseEntity exitTeam(@RequestParam Long teamid, @RequestParam String openid, @RequestParam String type) {
        log.info("解散球队，退出球队请求参数为:{}", teamid);
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            if ("1".equals(type)) {
                Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
                pkTeamMemberWrapper = pkTeamMemberWrapper.eq("teamid", teamid).eq("memberid", pkMembers.get(0).getId());
                pkTeamMemberMapper.delete(pkTeamMemberWrapper);
            }

            if ("2".equals(type)) {
                Wrapper<PkTeam> pkTeamWrapper = new EntityWrapper<PkTeam>();
                pkTeamWrapper = pkTeamWrapper.eq("id", teamid);
                pkTeamMapper.delete(pkTeamWrapper);
            }


            return ResponseEntity.ok(new CommonResp<String>("操作成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * 球队区域
     *
     * @return
     */
    @RequestMapping(value = "/area", method = RequestMethod.POST)
    @ApiOperation(value = "球队区域", notes = "返回码:1成功;")
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
     * 球王榜
     *
     * @return
     */
    @RequestMapping(value = "/rankList", method = RequestMethod.POST)
    @ApiOperation(value = "球王榜", notes = "返回码:1成功;")
    public ResponseEntity rankList(@RequestParam Integer levelid) {
        try {
            Wrapper<PkTeam> wrapper = new EntityWrapper<PkTeam>();
            switch (levelid) {
                case 1:
                    wrapper.eq("level", "使者");
                    break;
                case 2:
                    wrapper.eq("level", "守卫");
                    break;
                case 3:
                    wrapper.eq("level", "战士");
                    break;
                case 4:
                    wrapper.eq("level", "统治");
                    break;
                case 5:
                    wrapper.eq("level", "经典");
                    break;
                case 6:
                    wrapper.eq("level", "传奇");
                    break;
                case 7:
                    wrapper.eq("level", "神灵");
                    break;
                default:
                    wrapper.eq("level", "使者");
                    break;
            }

            List<PkTeam> list = pkTeamMapper.selectList(wrapper);

            List<Map> datas = new ArrayList<>();
            list.forEach(pkTeam -> {
                Map map = new HashMap();
                map.put("timeid", pkTeam.getId());
                map.put("teamName", pkTeam.getName());
                Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<>();
                pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkTeam.getId()).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
                List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
                if (!CollectionUtils.isEmpty(attachmentList)) {
                    map.put("teamImage", attachmentList.get(0).getUrl());
                }
                List<Integer> grades = new ArrayList<>();
                grades.add(pkTeam.getWinnum());
                grades.add(pkTeam.getDrawnum());
                grades.add(pkTeam.getDebtnum());
                map.put("grade", grades);
                map.put("teamScore", pkTeam.getPoint());
                datas.add(map);
            });

            return ResponseEntity.ok(new CommonListResp<PkTeam>(list));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Areas>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


}
