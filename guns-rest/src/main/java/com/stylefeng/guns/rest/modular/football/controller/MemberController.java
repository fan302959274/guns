package com.stylefeng.guns.rest.modular.football.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.enums.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.response.CommonListResp;
import com.stylefeng.guns.core.util.response.CommonResp;
import com.stylefeng.guns.core.util.response.ResponseCode;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.modular.football.transfer.PkMemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
import java.text.ParseException;
import java.util.*;

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
    @Autowired
    PkParkRelationMapper pkParkRelationMapper;
    @Autowired
    AreasMapper areasMapper;
    @Autowired
    PkParkMapper pkParkMapper;
    @Autowired
    PkOrderMapper pkOrderMapper;

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
            data.put("birth", Objects.nonNull(pkMember.getBirth()) ? DateUtil.format(pkMember.getBirth(), "yyyy") : "");
            data.put("player", PositionEnum.messageOf(pkMember.getPosition()));
            data.put("foot", FootEnum.messageOf(pkMember.getHabitfeet()));
            Wrapper pkAttachmentWrapper = new EntityWrapper<>();
            pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkMember.getId()).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.HEAD.getCode());
            List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
            if (!CollectionUtils.isEmpty(attachmentList)) {
                data.put("headImage", attachmentList.get(0).getUrl());

            }

            pkAttachmentWrapper = new EntityWrapper<>();
            pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkMember.getId()).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.IDCARD.getCode());
            attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
            if (!CollectionUtils.isEmpty(attachmentList)) {
                data.put("idcardImage", attachmentList.get(0).getUrl());
            }


            return ResponseEntity.ok(new CommonResp<Map>(data));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
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
    public ResponseEntity myInfo(@RequestParam String openid) {
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
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("memberid", pkMember.getId()).eq("status", "1");
            List<PkTeamMember> pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);
            if (CollectionUtils.isNotEmpty(pkTeamMembers)) {
                PkTeam pkTeam = pkTeamMapper.selectById(pkTeamMembers.get(0).getTeamid());
                data.put("team", pkTeam.getName());
                data.put("teamid", pkTeam.getId());
                data.put("teamScore", pkTeam.getPoint());
                data.put("level", TeamLevelEnum.calcLevel(pkTeam.getPoint()).getMessage());
                data.put("cityid", pkTeam.getCity());
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
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            Wrapper<PkMatch> pkMatchWrapper = new EntityWrapper<PkMatch>();
            pkMatchWrapper = pkMatchWrapper.and("(hostteamid= {0} or challengeteamid={1})", teamid, teamid);
            if (0 != type) {
                pkMatchWrapper = pkMatchWrapper.eq("status", type);
            }
            List<PkMatch> pkMatches = pkMatchMapper.selectList(pkMatchWrapper);
            if (CollectionUtils.isEmpty(pkMatches)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "未获取到比赛信息"));
            }
            PkTeam pkTeam = pkTeamMapper.selectById(teamid);
            List<Map> datas = new ArrayList<>();
            pkMatches.forEach(pkMatch -> {
                Map data = new HashMap();
                data.put("team", pkTeam.getName());
                PkTeam challengeTeam;
                if (teamid.equals(pkMatch.getChallengeteamid())) {
                    challengeTeam = pkTeamMapper.selectById(pkMatch.getHostteamid());
                } else {
                    challengeTeam = pkTeamMapper.selectById(pkMatch.getChallengeteamid());
                }

                //对手信息
                if (Objects.nonNull(challengeTeam)) {
                    data.put("opponent", challengeTeam.getName());
                    data.put("oppoid", challengeTeam.getId());
                    Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<>();
                    pkAttachmentWrapper = new EntityWrapper<>();
                    pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", challengeTeam.getId()).eq("category", AttachCategoryEnum.TEAM.getCode()).eq("type", AttachTypeEnum.LOGO.getCode());
                    List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
                    if (!CollectionUtils.isEmpty(attachmentList)) {
                        data.put("oppoImage", attachmentList.get(0).getUrl());
                    }
                }
                try {
                    data.put("address", getAddress(pkMatch.getStatus(), pkMatch.getParkid(), pkMatch.getArea()));
                    data.put("time", DateUtil.formatDate(DateUtil.parse(pkMatch.getDate(), "yyyyMMdd"), "yyyy-MM-dd") + " " + getTime(pkMatch.getStatus(), pkMatch.getTime()));
                } catch (ParseException e) {
                    log.error("地址或者时间转换异常");
                }
                data.put("pkstatus", pkMatch.getStatus());

                Wrapper<PkOrder> pkOrderWrapper = new EntityWrapper<PkOrder>();
                pkOrderWrapper = pkOrderWrapper.eq("matchid", pkMatch.getId()).eq("teamid", teamid);
                List<PkOrder> list = pkOrderMapper.selectList(pkOrderWrapper);
                if (CollectionUtils.isNotEmpty(list)) {
                    data.put("orderno", list.get(0).getNo());//订单号
                    data.put("paystatus", list.get(0).getStatus());//订单状态
                }

                datas.add(data);

            });

            return ResponseEntity.ok(new CommonListResp<Map>(datas));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }
    }


    //获取时间接口
    public String getTime(Integer status, Long timeid) throws ParseException {
        //匹配中返回下午/晚上
        if (MatchStatusEnum.FINDING.getCode().equals(status + "")) {
            PkParkRelation pkParkRelation = pkParkRelationMapper.selectById(timeid);//获取时间段的球场信息
            if (Objects.nonNull(pkParkRelation)) {
                return DateUtil.judgeType(pkParkRelation.getStart(), pkParkRelation.getEnd());
            }
        } else {
            PkParkRelation pkParkRelation = pkParkRelationMapper.selectById(timeid);//获取时间段的球场信息
            if (Objects.nonNull(pkParkRelation)) {
                return pkParkRelation.getStart() + "-" + pkParkRelation.getEnd();
            }
        }
        return null;
    }

    //获取地址接口
    public String getAddress(Integer status, Long parkid, Long areaid) throws ParseException {
        //匹配中返回区域名称
        if (MatchStatusEnum.FINDING.getCode().equals(status + "")) {
            Wrapper<Areas> wrapper = new EntityWrapper<Areas>();
            wrapper = wrapper.eq("areaid", areaid);
            List<Areas> areas = areasMapper.selectList(wrapper);//获取时间段的球场信息
            if (CollectionUtils.isNotEmpty(areas)) {
                return areas.get(0).getArea();
            }
        } else {
            PkPark pkPark = pkParkMapper.selectById(parkid);//获取球场信息
            if (Objects.nonNull(pkPark)) {
                return pkPark.getPkname();
            }
        }
        return null;
    }

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
        try {
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
     * 修改资料接口
     *
     * @param pkMemberDto
     * @return
     */
    @RequestMapping(value = "/editData", method = RequestMethod.POST)
    @ApiOperation(value = "编辑队员", notes = "返回码:1成功;")
    @ApiImplicitParam(paramType = "body", name = "pkMemberDto", value = "队员实体", required = true, dataType = "PkMemberDto")
    public ResponseEntity editData(@RequestBody PkMemberDto pkMemberDto) {
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", pkMemberDto.getOpenid());
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");
            PkMember pkMember = new PkMember();
            pkMember.setPosition(PositionEnum.codeOf(pkMemberDto.getPlayer()));
            pkMember.setHabitfeet(FootEnum.codeOf(pkMemberDto.getFoot()));
            pkMember.setBirth(StringUtils.isNoneBlank(pkMemberDto.getBirth()) ? DateUtil.parse(pkMemberDto.getBirth(), "yyyy") : null);
            pkMember.setHeight(pkMemberDto.getHeight());
            pkMember.setWeight(pkMemberDto.getWeight());
            pkMember.setId(pkMembers.get(0).getId());
            pkMemberMapper.updateById(pkMember);
            return ResponseEntity.ok(new CommonResp<String>("修改成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
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
        try {
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("memberid", pkMembers.get(0).getId());
            List<PkTeamMember> pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);
            Assert.notEmpty(pkTeamMembers, "openid未加入任何球队");

            //删除该队伍匹配中的订单
            Wrapper<PkMatch> pkMatchWrapper = new EntityWrapper<PkMatch>();
            pkMatchWrapper = pkMatchWrapper.eq("hostteamid", pkTeamMembers.get(0).getTeamid()).eq("status", MatchStatusEnum.FINDING.getCode());
            pkMatchMapper.delete(pkMatchWrapper);

            return ResponseEntity.ok(new CommonResp<String>("取消约战成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<Map>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }
    }


}
