package com.stylefeng.guns.rest.modular.football.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.enums.AttachCategoryEnum;
import com.stylefeng.guns.core.enums.AttachTypeEnum;
import com.stylefeng.guns.core.enums.PositionEnum;
import com.stylefeng.guns.core.enums.TeamLevelEnum;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.PkAttachment;
import com.stylefeng.guns.rest.common.persistence.model.PkMember;
import com.stylefeng.guns.rest.common.persistence.model.PkTeam;
import com.stylefeng.guns.rest.common.persistence.model.PkTeamMember;
import com.stylefeng.guns.core.util.response.CommonListResp;
import com.stylefeng.guns.core.util.response.CommonResp;
import com.stylefeng.guns.core.util.response.ResponseCode;
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

import java.math.BigDecimal;
import java.util.*;

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
        try {
            Wrapper<PkTeam> wrapper = new EntityWrapper<PkTeam>();
            wrapper = wrapper.like("name", name);
            List<PkTeam> pkTeams = pkTeamMapper.selectList(wrapper);

            List<Map> datas = new ArrayList<>();
            pkTeams.forEach(pkTeam -> {
                Map data = new HashMap();

                data.put("id", pkTeam.getId());
                data.put("name", pkTeam.getName());

                Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<>();
                pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkTeam.getId()).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
                List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
                if (!org.apache.commons.collections.CollectionUtils.isEmpty(attachmentList)) {
                    data.put("image", attachmentList.get(0).getUrl());
                }
                datas.add(data);
            });
            return ResponseEntity.ok(new CommonListResp<Map>(datas));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
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
        try {
            PkTeam pkTeam = pkTeamMapper.selectById(id);
            Assert.notNull(pkTeam, "不存在该球队");

            Map data = new HashMap();

            data.put("id", pkTeam.getId());
            data.put("name", pkTeam.getName());
            data.put("level", TeamLevelEnum.calcLevel(pkTeam.getPoint()).getMessage());

            Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<>();
            pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkTeam.getId()).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
            List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
            if (!org.apache.commons.collections.CollectionUtils.isEmpty(attachmentList)) {
                data.put("image", attachmentList.get(0).getUrl());
            }
            data.put("grade", Arrays.asList(new BigDecimal[]{pkTeam.getCulture(), pkTeam.getOntime(), pkTeam.getFriendly()}));
            data.put("intro", pkTeam.getTeamdesc());
            data.put("intro", pkTeam.getTeamdesc());

            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("teamid", pkTeam.getId()).eq("status", "1");
            ;
            List<PkTeamMember> pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);
            List<Map> members = new ArrayList<>();

            pkTeamMembers.forEach(teamMember -> {
                Map member = new HashMap();
                PkMember pkMember = pkMemberMapper.selectById(teamMember.getMemberid());
                member.put("name", pkMember.getName());
                member.put("openid", pkMember.getOpenid());
                member.put("position", PositionEnum.messageOf(pkMember.getPosition()));
                member.put("isCaptain", "1".equals(pkMember.getType()) ? 1 : 0);//是否是队长
                Wrapper<PkAttachment> attachmentWrapper = new EntityWrapper<>();
                attachmentWrapper = attachmentWrapper.eq("linkid", pkMember.getId()).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.HEAD.getCode());
                List<PkAttachment> pkAttachmentList = pkAttachmentMapper.selectList(attachmentWrapper);
                if (!CollectionUtils.isEmpty(pkAttachmentList)) {
                    member.put("image", pkAttachmentList.get(0).getUrl());//队员头像
                }
                members.add(member);

            });

            data.put("mate", members);//队员数组

            data.put("winnum",pkTeam.getWinnum());//胜
            data.put("debtnum",pkTeam.getDebtnum());//负
            data.put("drawnum",pkTeam.getDrawnum());//平
            data.put("point",pkTeam.getPoint());//积分


            return ResponseEntity.ok(new CommonResp<Map>(data));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
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

            wrapper.and("point >= {0} and point <= {1}", TeamLevelEnum.valueOfCode(levelid + "").getMin(),TeamLevelEnum.valueOfCode(levelid + "").getMax()).orderBy("point",false);
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

            return ResponseEntity.ok(new CommonListResp<Map>(datas));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * 积分升级接口
     *
     * @return
     */
    @RequestMapping(value = "/point", method = RequestMethod.POST)
    @ApiOperation(value = "球队积分", notes = "返回码:1成功;")
    public ResponseEntity point(@RequestParam String openid, @RequestParam Long teamid) {
        try {
            PkTeam pkTeam = pkTeamMapper.selectById(teamid);

            Map data = new HashMap();
            data.put("teamName", pkTeam.getName());
            data.put("score", pkTeam.getPoint());
            data.put("level", TeamLevelEnum.calcLevel(pkTeam.getPoint()).getMessage());
            data.put("differValue", (TeamLevelEnum.calcLevel(pkTeam.getPoint()).getMax() - pkTeam.getPoint())+1);
            Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<>();
            pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkTeam.getId()).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
            List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
            if (!org.apache.commons.collections.CollectionUtils.isEmpty(attachmentList)) {
                data.put("teamImage", attachmentList.get(0).getUrl());
            }

            return ResponseEntity.ok(new CommonResp<Map>(data));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * 加入球队列表
     *
     * @param teamid
     * @return
     */
    @RequestMapping(value = "/appleList", method = RequestMethod.POST)
    @ApiOperation(value = "加入球队列表", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "teamid", value = "球队id", required = true, dataType = "Long")
    public ResponseEntity appleList(@RequestParam Long teamid, @RequestParam String openid) {
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("teamid", teamid).ne("status", "1");
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
     * 添加球队
     *
     * @param pkTeamDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加球队", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "body", name = "pkTeamDto", value = "球队实体", required = true, dataType = "PkTeamDto")
    public ResponseEntity add(@RequestBody PkTeamDto pkTeamDto) {
        try {
            Assert.notNull(pkTeamDto.getName(), "名称不能为空");
            Assert.notNull(pkTeamDto.getOpenid(), "openid不能为空");
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", pkTeamDto.getOpenid());
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");
            PkMember pkMember = pkMembers.get(0);

            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("memberid", pkMember.getId());
            Integer count = pkTeamMemberMapper.selectCount(pkTeamMemberWrapper);
            if (count > 0) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "该球员已经创建其它球队"));
            }


            if (Objects.nonNull(pkMember.getLastjointime()) && DateUtil.getDaySub(pkMember.getLastjointime(),new Date())<=30){
                return ResponseEntity.ok(new CommonResp<String>("2", "您一个月内加入过球队！"));
            }


            pkMember.setType("1");
            pkMemberMapper.updateById(pkMember);//更新创建球队的人为队长
            PkTeam pkTeam = new PkTeam();
            PropertyUtils.copyProperties(pkTeam, pkTeamDto);
            pkTeam.setOwnerid(pkMember.getId());
            pkTeam.setStartpoint(0);//初始积分
            pkTeam.setPoint(0);//初始积分
            pkTeam.setWinnum(0);//赢的场数
            pkTeam.setDebtnum(0);//输的场数
            pkTeam.setDrawnum(0);//平的场数
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
     * 编辑球队资料
     *
     * @param pkTeamDto
     * @return
     */
    @RequestMapping(value = "/editData", method = RequestMethod.POST)
    @ApiOperation(value = "编辑球队资料", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "body", name = "pkTeamDto", value = "球队实体", required = true, dataType = "PkTeamDto")
    public ResponseEntity editData(@RequestBody PkTeamDto pkTeamDto) {
        try {
            Assert.notNull(pkTeamDto.getName(), "名称不能为空");
            Assert.notNull(pkTeamDto.getId(), "id不能为空");

            PkTeam pkTeam = new PkTeam();
            pkTeam.setId(pkTeamDto.getId());
            pkTeam.setName(pkTeamDto.getName());
            pkTeam.setTeamdesc(pkTeamDto.getTeamdesc());

            //        保存logo
            if (StringUtils.isNoneBlank(pkTeamDto.getLogo())) {

                Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<>();
                pkAttachmentWrapper = new EntityWrapper<>();
                pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkTeam.getId()).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
                pkAttachmentMapper.delete(pkAttachmentWrapper);


                PkAttachment pkAttachment = new PkAttachment();
                pkAttachment.setCategory(AttachCategoryEnum.TEAM.getCode());
                pkAttachment.setType(AttachTypeEnum.LOGO.getCode());
                pkAttachment.setLinkid(pkTeam.getId());
                pkAttachment.setName(pkTeamDto.getLogo());
                pkAttachment.setSuffix(pkTeamDto.getLogo().substring(pkTeamDto.getLogo().lastIndexOf(".") + 1));
                pkAttachment.setUrl(pkTeamDto.getLogo());
                pkAttachmentMapper.insert(pkAttachment);
            }
            pkTeamMapper.updateById(pkTeam);
            return ResponseEntity.ok(new CommonResp<PkTeam>(pkTeam));
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
    @ApiOperation(value = "加入球队", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "teamid", value = "球队id", required = true, dataType = "Long")
    public ResponseEntity join(@RequestParam Long teamid, @RequestParam String openid) {
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            PkMember pkMember = pkMembers.get(0);

            Assert.notNull(pkTeamMapper.selectById(teamid), "未查询到该球队");


            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("memberid", pkMember.getId()).eq("teamid", teamid);
            ;
            List<PkTeamMember> pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);
            if (!CollectionUtils.isEmpty(pkTeamMembers)) {
                return ResponseEntity.ok(new CommonResp<String>("2", "已经申请过加入该球队"));
            }

            pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("memberid", pkMember.getId()).eq("status", "1");
            ;
            pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);
            if (!CollectionUtils.isEmpty(pkTeamMembers)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "已经加入其它球队"));
            }

            pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("teamid", teamid);
            pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);
            if (!CollectionUtils.isEmpty(pkTeamMembers) && pkTeamMembers.size() >= 24) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "球队人员已满24人"));
            }

            if (Objects.nonNull(pkMember.getLastjointime()) && DateUtil.getDaySub(pkMember.getLastjointime(),new Date())<=30){
                return ResponseEntity.ok(new CommonResp<String>("3", "您一个月内加入过球队！"));
            }


            PkTeamMember pkTeamMember = new PkTeamMember();
            pkTeamMember.setMemberid(pkMember.getId());
            pkTeamMember.setTeamid(teamid);
            pkTeamMemberMapper.insert(pkTeamMember);

            //更新球员的最近加入球队时间
            pkMemberMapper.updateById(pkMember);
            return ResponseEntity.ok(new CommonResp<String>("加入成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * 拒绝加入球队
     *
     * @param teamid
     * @return
     */
    @RequestMapping(value = "/deleteApple", method = RequestMethod.POST)
    @ApiOperation(value = "拒绝加入球队", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "teamid", value = "球队id", required = true, dataType = "Long")
    public ResponseEntity deleteApple(@RequestParam Long teamid, @RequestParam String openid, @RequestParam String manid) {
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", manid);
            pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "manid未获取到用户");

            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("teamid", teamid).eq("memberid", pkMembers.get(0).getId());
            pkTeamMemberMapper.delete(pkTeamMemberWrapper);

            return ResponseEntity.ok(new CommonResp<String>("拒绝成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * 球队踢出球员
     *
     * @param teamid
     * @return
     */
    @RequestMapping(value = "/kickMember", method = RequestMethod.POST)
    @ApiOperation(value = "剔除球员", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "teamid", value = "球队id", required = true, dataType = "Long")
    public ResponseEntity kickMember(@RequestParam Long teamid, @RequestParam String openid, @RequestParam String manid) {
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", manid);
            pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "manid未获取到用户");

            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("teamid", teamid).eq("memberid", pkMembers.get(0).getId());
            pkTeamMemberMapper.delete(pkTeamMemberWrapper);

            return ResponseEntity.ok(new CommonResp<String>("踢出成功"));
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
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");


            wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", manid);
            pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "manid未获取到用户");


            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("memberid", pkMembers.get(0).getId()).eq("status", "1");
            ;
            List<PkTeamMember> pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);
            if (!CollectionUtils.isEmpty(pkTeamMembers)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "已经加入其它球队"));
            }

            Wrapper<PkTeamMember> pkTeamMemberEntityWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberEntityWrapper = pkTeamMemberEntityWrapper.eq("teamid", teamid).eq("memberid", pkMembers.get(0).getId());
            List<PkTeamMember> teamMembers = pkTeamMemberMapper.selectList(pkTeamMemberEntityWrapper);

            PkTeamMember pkTeamMember = teamMembers.get(0);
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
                //删除球队
                Wrapper<PkTeam> pkTeamWrapper = new EntityWrapper<PkTeam>();
                pkTeamWrapper = pkTeamWrapper.eq("id", teamid);
                pkTeamMapper.delete(pkTeamWrapper);
                //删除球队球员关系
                Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
                pkTeamMemberWrapper = pkTeamMemberWrapper.eq("teamid", teamid).eq("memberid", pkMembers.get(0).getId());
                pkTeamMemberMapper.delete(pkTeamMemberWrapper);
                //队长转换为普通球员
                PkMember pkMember = pkMembers.get(0);
                pkMember.setType("2");//普通球员
                pkMember.setLastjointime(new Date());//最近解散球队时间
                pkMemberMapper.updateById(pkMember);
            }


            return ResponseEntity.ok(new CommonResp<String>("操作成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


}
