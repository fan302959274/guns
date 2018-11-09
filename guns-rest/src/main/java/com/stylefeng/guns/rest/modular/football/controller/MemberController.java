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
import com.stylefeng.guns.rest.modular.football.transfer.PkMemberDto;
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

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@RestController
@RequestMapping("/member")
@Api(value = "MemberController|一个用来测试swagger注解的控制器")
public class MemberController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PkMemberMapper pkMemberMapper;
    @Resource
    private PkAttachmentMapper pkAttachmentMapper;
    @Resource
    private PkTeamMapper pkTeamMapper;
    @Resource
    private PkTeamReviewMapper pkTeamReviewMapper;
    @Resource
    PkTeamMemberMapper pkTeamMemberMapper;
    @Autowired
    PkMatchMapper pkMatchMapper;

    /**
     * 队员注册接口
     *
     * @param pkMemberDto
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "注册队员", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "body", name = "pkMemberDto", value = "队员实体", required = true, dataType = "PkMemberDto")
    public ResponseEntity register(@RequestBody PkMemberDto pkMemberDto) {
        log.info("注册队员请求参数{}", JSONObject.toJSONString(pkMemberDto));
        try {
//            Assert.notNull(pkMemberDto.getMobile(), "手机号不能为空");
            Assert.notNull(pkMemberDto.getOpenid(), "openid不能为空");
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", pkMemberDto.getOpenid());
            Integer count = pkMemberMapper.selectCount(wrapper);
            if (count > 0) {
                return ResponseEntity.ok(new CommonResp<PkMember>(ResponseCode.SYSTEM_ERROR.getCode(), "openid已经注册过"));
            }
            PkMember pkMember = new PkMember();
            pkMember.setAccount(pkMemberDto.getMobile());//以手机号作为account
            pkMember.setName(pkMemberDto.getName());
            pkMember.setMobile(pkMemberDto.getMobile());
            pkMember.setOpenid(pkMemberDto.getOpenid());
            pkMember.setType("2");//普通队员
            pkMemberMapper.insert(pkMember);
            //        保存头像
            if (StringUtils.isNoneBlank(pkMemberDto.getAvatar())) {
                PkAttachment pkAttachment = new PkAttachment();
                pkAttachment.setCategory(AttachCategoryEnum.MEMBER.getCode());
                pkAttachment.setType(AttachTypeEnum.HEAD.getCode());
                pkAttachment.setLinkid(pkMember.getId());
                pkAttachment.setName(pkMemberDto.getAvatar());
                pkAttachment.setSuffix(pkMemberDto.getAvatar().substring(pkMemberDto.getAvatar().lastIndexOf(".") + 1));
                pkAttachment.setUrl(pkMemberDto.getAvatar());
                pkAttachmentMapper.insert(pkAttachment);
            }
            //        保存身份证
            if (StringUtils.isNoneBlank(pkMemberDto.getIdcard())) {
                PkAttachment pkAttachment = new PkAttachment();
                pkAttachment.setCategory(AttachCategoryEnum.MEMBER.getCode());
                pkAttachment.setType(AttachTypeEnum.IDCARD.getCode());
                pkAttachment.setLinkid(pkMember.getId());
                pkAttachment.setName(pkMemberDto.getIdcard());
                pkAttachment.setSuffix(pkMemberDto.getIdcard().substring(pkMemberDto.getIdcard().lastIndexOf(".") + 1));
                pkAttachment.setUrl(pkMemberDto.getIdcard());
                pkAttachmentMapper.insert(pkAttachment);
            }
            return ResponseEntity.ok(new CommonResp<PkMember>(pkMember));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<PkMember>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * @description 评价接口
     * @author sh00859
     * @date 2018/10/30
     */
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    @ApiOperation(value = "评价", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "openid", value = "队员评价", required = true, dataType = "String")
    public ResponseEntity review(@RequestParam String openid, @RequestParam Long teamid, @RequestParam Long oppoid, @RequestParam BigDecimal culture, @RequestParam BigDecimal ontime, @RequestParam BigDecimal friendly) {
        log.info("队员评价请求参数{}", JSONObject.toJSONString(openid));
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            if (CollectionUtils.isEmpty(pkMembers)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "openid未获取到用户"));
            }
            Assert.notEmpty(pkMembers, "openid未获取到用户");
            Wrapper<PkTeamReview> pkTeamReviewWrapper = new EntityWrapper<PkTeamReview>();
            pkTeamReviewWrapper = pkTeamReviewWrapper.eq("openid", openid).eq("teamid", teamid).eq("oppoid", oppoid);
            List<PkTeamReview> pkTeamReviews = pkTeamReviewMapper.selectList(pkTeamReviewWrapper);
            if (CollectionUtils.isNotEmpty(pkTeamReviews)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "该用户对球队已经评价过"));
            }

            //存储评价记录
            PkTeamReview pkTeamReview = new PkTeamReview();
            pkTeamReview.setOpenid(openid);
            pkTeamReview.setOppoid(oppoid);
            pkTeamReview.setCulture(culture);
            pkTeamReview.setFriendly(friendly);
            pkTeamReview.setOntime(ontime);
            pkTeamReview.setTeamid(teamid);
            pkTeamReviewMapper.insert(pkTeamReview);

            return ResponseEntity.ok(new CommonResp<String>("成功"));

        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }
    }


    /**
     * 个人信息接口
     *
     * @param openid
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ApiOperation(value = "查询队员", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "openid", value = "队员实体", required = true, dataType = "String")
    public ResponseEntity getData(@RequestParam String openid) {
        log.info("队员信息请求参数{}", JSONObject.toJSONString(openid));
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            PkMember pkMember = pkMembers.get(0);
            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("memberid", pkMember.getId());
            List<PkTeamMember> pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);

            Map data = new HashMap();
            data.put("name", pkMember.getName());
            if (CollectionUtils.isNotEmpty(pkTeamMembers)) {
                PkTeam pkTeam = pkTeamMapper.selectById(pkTeamMembers.get(0).getTeamid());
                data.put("team", pkTeam.getName());
            }
            data.put("height", pkMember.getHeight());
            data.put("weight", pkMember.getWeight());
            data.put("birth", pkMember.getBirth());
            data.put("player", pkMember.getPosition());
            data.put("foot", pkMember.getHabitfeet());
            Wrapper pkAttachmentWrapper = new EntityWrapper<>();
            pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkMember.getId()).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.HEAD.getCode());
            List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
            if (!CollectionUtils.isEmpty(attachmentList)) {
                data.put("image", attachmentList.get(0).getUrl());

            }

            return ResponseEntity.ok(new CommonResp<Map>(data));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }
    }

    /**
     * 修改资料接口
     *
     * @param pkMemberDto
     * @return
     */
    @RequestMapping(value = "/editData", method = RequestMethod.POST)
    @ApiOperation(value = "编辑队员", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "body", name = "pkMemberDto", value = "队员实体", required = true, dataType = "PkMemberDto")
    public ResponseEntity editData(@RequestBody PkMemberDto pkMemberDto) {
        log.info("队员信息修改请求参数{}", JSONObject.toJSONString(pkMemberDto));
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", pkMemberDto.getOpenid());
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            PkMember pkMember = new PkMember();

            PropertyUtils.copyProperties(pkMember, pkMemberDto);


            String position = "1";
            switch (pkMemberDto.getPlayer()) {
                case "前锋":
                    position = "1";
                    break;
                case "中场":
                    position = "2";
                    break;
                case "后卫":
                    position = "3";
                    break;
                case "门将":
                    position = "4";
                    break;
                default:
                    position = "1";
                    break;
            }
            String foot = "1";
            switch (pkMemberDto.getPlayer()) {
                case "左":
                    foot = "1";
                    break;
                case "右":
                    foot = "2";
                    break;
                default:
                    foot = "1";
                    break;
            }
            pkMember.setPosition(position);
            pkMember.setHabitfeet(foot);
            pkMember.setId(pkMembers.get(0).getId());
            pkMemberMapper.updateById(pkMember);
            return ResponseEntity.ok(new CommonResp<String>("修改成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }
    }


    /**
     * 我的页面信息接口
     *
     * @param openid
     * @return
     */
    @RequestMapping(value = "/myInfo", method = RequestMethod.POST)
    @ApiOperation(value = "查询队员", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "openid", value = "openid", required = true, dataType = "String")
    public ResponseEntity editData(@RequestParam String openid) {
        log.info("队员信息修改请求参数{}", openid);
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            PkMember pkMember = pkMembers.get(0);

            Map data = new HashMap();
            data.put("name", pkMember.getName());
            Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<>();
            pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkMember.getId()).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.HEAD.getCode());
            List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
            if (!CollectionUtils.isEmpty(attachmentList)) {
                data.put("headImage", attachmentList.get(0).getUrl());
            }

            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("memberid", pkMember.getId());
            List<PkTeamMember> pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);
            if (CollectionUtils.isNotEmpty(pkTeamMembers)) {
                PkTeam pkTeam = pkTeamMapper.selectById(pkTeamMembers.get(0).getTeamid());
                data.put("team", pkTeam.getName());
                data.put("teamid", pkTeam.getId());
                data.put("teamScore", pkTeam.getPoint());
                data.put("levelid", pkTeam.getLevel());
                pkAttachmentWrapper = new EntityWrapper<>();
                pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkTeam.getId()).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
                attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
                if (!CollectionUtils.isEmpty(attachmentList)) {
                    data.put("teamImage", attachmentList.get(0).getUrl());

                }

            }
            data.put("isCaptain", "1".equals(pkMember.getType()) ? 1 : 0);
            return ResponseEntity.ok(new CommonResp<Map>(data));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }
    }


    /**
     * 我的约战信息接口
     *
     * @param openid
     * @return
     */
    @RequestMapping(value = "/myPK", method = RequestMethod.POST)
    @ApiOperation(value = "查询约战信息", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "openid", value = "队员实体", required = true, dataType = "String")
    public ResponseEntity myPK(@RequestParam String openid, @RequestParam Long teamid, @RequestParam Integer type) {
        log.info("我的约战信息请求参数{}", openid);
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            Wrapper<PkMatch> pkMatchWrapper = new EntityWrapper<PkMatch>();
            pkMatchWrapper = pkMatchWrapper.eq("hostteamid", teamid).eq("status", type);
            List<PkMatch> pkMatches = pkMatchMapper.selectList(pkMatchWrapper);
            if (CollectionUtils.isEmpty(pkMatches)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "未获取到比赛信息"));
            }

            PkTeam pkTeam = pkTeamMapper.selectById(teamid);
            List<Map> datas = new ArrayList<>();
            pkMatches.forEach(pkMatch -> {
                Map data = new HashMap();
                data.put("team", pkTeam.getName());
                PkTeam hostTeam = pkTeamMapper.selectById(pkMatch.getChallengeteamid());
                data.put("opponent", hostTeam.getName());
                data.put("oppoid", hostTeam.getId());

                Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<>();
                pkAttachmentWrapper = new EntityWrapper<>();
                pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkTeam.getId()).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
                List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
                if (!CollectionUtils.isEmpty(attachmentList)) {
                    data.put("oppoImage", attachmentList.get(0).getUrl());
                }
                data.put("address", pkMatch.getPlace());
                data.put("time", pkMatch.getTime());
                data.put("pkstatus", pkMatch.getStatus());
                datas.add(data);

            });

            return ResponseEntity.ok(new CommonListResp<Map>(datas));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }
    }


    /**
     * 取消约战接口 TODO
     *
     * @param openid
     * @return
     */
    @RequestMapping(value = "/cancelPK", method = RequestMethod.POST)
    @ApiOperation(value = "取消约战", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "query", name = "openid", value = "队员实体", required = true, dataType = "String")
    public ResponseEntity cancelPK(@RequestParam String openid, @RequestParam Long teamid) {
        log.info("取消约战请求参数{}", openid);
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            return ResponseEntity.ok(new CommonResp<String>("取消约战成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }
    }


}
