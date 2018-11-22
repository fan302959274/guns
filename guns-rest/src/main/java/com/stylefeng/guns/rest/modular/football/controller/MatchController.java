package com.stylefeng.guns.rest.modular.football.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.enums.MatchStatusEnum;
import com.stylefeng.guns.core.enums.PayStatusEnum;
import com.stylefeng.guns.core.enums.TeamLevelEnum;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.httpclient.HttpClientUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.core.util.response.CommonListResp;
import com.stylefeng.guns.core.util.response.CommonResp;
import com.stylefeng.guns.core.util.response.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

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
    @Autowired
    PkMemberMapper pkMemberMapper;
    @Autowired
    PkTeamMapper pkTeamMapper;
    @Autowired
    PkMatchMapper pkMatchMapper;
    @Autowired
    PkOrderMapper pkOrderMapper;
    @Autowired
    PkTeamMemberMapper pkTeamMemberMapper;
    @Autowired
    PkRuleMapper pkRuleMapper;
    @Autowired
    PkParkRelationMapper pkParkRelationMapper;
    @Autowired
    PkParkMapper pkParkMapper;
    @Autowired
    AreasMapper areasMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Value("${sms.url}")
    private String smsUrl;
    @Value("${sms.charset}")
    private String charset;


    /**
     * 约战时间(周几)
     *
     * @return
     */
    @RequestMapping(value = "/weeks", method = RequestMethod.POST)
    @ApiOperation(value = "约战时间(周几)", notes = "返回码:1成功;")
    public ResponseEntity weeks() {
        try {
            Wrapper<Dict> wrapper = new EntityWrapper<Dict>();
            wrapper.eq("pid", "39");
            List<Dict> list = dictMapper.selectList(wrapper);
            List<Map> datas = new ArrayList<>();
            Map map = new HashMap();
            map.put("date", DateUtil.getRecentWeekSixDay());
            map.put("weekid", "6");
            map.put("weekname", "周六 " + DateUtil.getRecentWeekSixDay());
            datas.add(map);
            map = new HashMap();
            map.put("date", DateUtil.getRecentWeekSevenDay());
            map.put("weekid", "7");
            map.put("weekname", "周日 " + DateUtil.getRecentWeekSevenDay());
            datas.add(map);
            return ResponseEntity.ok(new CommonListResp<Map>(datas));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Dict>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * 约战时间段
     *
     * @return
     */
    @RequestMapping(value = "/times", method = RequestMethod.POST)
    @ApiOperation(value = "约战时间段", notes = "返回码:1成功;")
    public ResponseEntity times(String weekid) {
        try {

            Wrapper<PkParkRelation> wrapper = new EntityWrapper<PkParkRelation>();
            wrapper = wrapper.eq("week", weekid);
            List<PkParkRelation> list = pkParkRelationMapper.selectList(wrapper);
            List<Map> datas = new ArrayList<Map>();
            Set<String> set = new HashSet<>();
            list.forEach(pkParkRelation -> {

                String time = pkParkRelation.getStart() + "-" + pkParkRelation.getEnd();
                String type = null;
                try {
                    type = DateUtil.judgeType(pkParkRelation.getStart(), pkParkRelation.getEnd());
                } catch (ParseException e) {
                    log.error("类型转换异常");
                }
                if (StringUtils.isNoneBlank(type) && !set.contains(type)) {
                    Map map = new HashMap();
                    map.put("timeid", pkParkRelation.getId());
                    map.put("time", time);
                    map.put("type", type);
                    datas.add(map);
                    set.add(type);
                }
            });
            return ResponseEntity.ok(new CommonListResp<Map>(datas));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<Dict>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * 查询约战资格
     *
     * @return
     */
    @RequestMapping(value = "/qualify", method = RequestMethod.POST)
    @ApiOperation(value = "球队约战资格", notes = "返回码:1成功;")
    public ResponseEntity qualify(@RequestParam Long teamid, @RequestParam String openid) {
        try {

            //-------------openid相关-----------------------

            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            if (CollectionUtils.isEmpty(pkMembers)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "openid未获取到用户"));
            }
            Assert.notEmpty(pkMembers, "openid未获取到用户");

            if (!"1".equals(pkMembers.get(0).getType())) {
                return ResponseEntity.ok(new CommonResp<String>("8", "该队员不是队长"));
            }


            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("teamid", teamid).eq("status", "1");
            List<PkTeamMember> pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);
            if (pkTeamMembers.size() < 12) {
                return ResponseEntity.ok(new CommonResp<String>("3", "队伍比赛未满12人"));
            }

            PkTeam pkTeam = pkTeamMapper.selectById(teamid);
            Assert.notNull(pkTeam, "未获取到球队");
            Integer matchSum = (null == pkTeam.getWinnum() ? 0 : pkTeam.getWinnum()) + (null == pkTeam.getDebtnum() ? 0 : pkTeam.getDebtnum()) + (null == pkTeam.getDrawnum() ? 0 : pkTeam.getDrawnum());
            if (matchSum < 5) {
                return ResponseEntity.ok(new CommonResp<String>("2", "队伍比赛未满5次"));
            }

            Wrapper<PkOrder> orderWrapper = new EntityWrapper<PkOrder>();
            orderWrapper = orderWrapper.eq("teamid", teamid).eq("status", PayStatusEnum.NOPAY.getCode());
            Integer selectCount = pkOrderMapper.selectCount(orderWrapper);
            if (selectCount > 0) {
                return ResponseEntity.ok(new CommonResp<String>("4", "队伍有未支付的订单"));
            }

            if ("1".equals(pkTeam.getStatus())) {
                return ResponseEntity.ok(new CommonResp<String>("8", "队伍在黑名单中无法比赛"));
            }


            //东道主
            Wrapper<PkMatch> pkMatchWrapperHost = new EntityWrapper<PkMatch>();
            pkMatchWrapperHost = pkMatchWrapperHost.eq("hostteamid", teamid);
            List<PkMatch> pkMatchesHost = pkMatchMapper.selectList(pkMatchWrapperHost);
            if (CollectionUtils.isNotEmpty(pkMatchesHost)) {
                PkMatch pkMatchesHostPkMatch = pkMatchesHost.get(0);
                //匹配中超过12小时
                if (MatchStatusEnum.FINDING.getCode().equals(pkMatchesHostPkMatch.getStatus())) {
                    long hour = DateUtil.getHourSub(pkMatchesHostPkMatch.getCreatedate(), new Date());
                    if (hour > 12) {
                        return ResponseEntity.ok(new CommonResp<String>("7", "队伍约战12小时无反应"));
                    }
                }
                //约战中
                if (MatchStatusEnum.MATCHING.getCode().equals(pkMatchesHostPkMatch.getStatus()) || MatchStatusEnum.FINDING.getCode().equals(pkMatchesHostPkMatch.getStatus())) {
                    return ResponseEntity.ok(new CommonResp<String>("5", "队伍约战中"));
                }
                //待比赛
                if (MatchStatusEnum.WAITING.getCode().equals(pkMatchesHostPkMatch.getStatus())) {
                    PkTeam pkTeamHost = pkTeamMapper.selectById(pkMatchesHostPkMatch.getChallengeteamid());
                    return ResponseEntity.ok(new CommonResp<PkTeam>("6", "队伍待比赛", pkTeamHost));
                }

            }
            //被挑战
            Wrapper<PkMatch> pkMatchWrapperChallenge = new EntityWrapper<PkMatch>();
            pkMatchWrapperChallenge = pkMatchWrapperChallenge.eq("challengeteamid", teamid);
            List<PkMatch> pkMatchesChallenge = pkMatchMapper.selectList(pkMatchWrapperChallenge);
            if (CollectionUtils.isNotEmpty(pkMatchesChallenge)) {
                PkMatch pkMatchesChallengePkMatch = pkMatchesChallenge.get(0);
                //匹配中超过12小时
                if (MatchStatusEnum.FINDING.getCode().equals(pkMatchesChallengePkMatch.getStatus())) {
                    long hour = DateUtil.getHourSub(pkMatchesChallengePkMatch.getCreatedate(), new Date());
                    if (hour > 12) {
                        return ResponseEntity.ok(new CommonResp<String>("7", "队伍约战12小时无反应"));
                    }
                }
                //约战中
                if (MatchStatusEnum.MATCHING.getCode().equals(pkMatchesChallengePkMatch.getStatus()) || MatchStatusEnum.FINDING.getCode().equals(pkMatchesChallengePkMatch.getStatus())) {
                    return ResponseEntity.ok(new CommonResp<String>("5", "队伍约战中"));
                }
                //待比赛
                if (MatchStatusEnum.WAITING.getCode().equals(pkMatchesChallengePkMatch.getStatus())) {
                    PkTeam pkTeamChallenge = pkTeamMapper.selectById(pkMatchesChallengePkMatch.getHostteamid());
                    return ResponseEntity.ok(new CommonResp<PkTeam>("6", "队伍待比赛", pkTeamChallenge));
                }

            }


            return ResponseEntity.ok(new CommonResp<String>("能约战"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * 约战费用
     *
     * @return
     */
    @RequestMapping(value = "/fee", method = RequestMethod.POST)
    @ApiOperation(value = "约战费用", notes = "返回码:1成功;")
    public ResponseEntity fee(@RequestParam String openid, @RequestParam Long teamid, @RequestParam String date, @RequestParam Long timeid, @RequestParam Long areaid) {
        try {
            PkParkRelation pkParkRelation = pkParkRelationMapper.selectById(timeid);//获取时间段的球场信息
            PkPark pkPark = pkParkMapper.selectById(pkParkRelation.getParkid());
            Map map = new HashMap();
            map.put("minCost", pkPark.getCost());
            map.put("maxCost", pkPark.getCost().add(new BigDecimal("100")));
            return ResponseEntity.ok(new CommonResp<Map>(map));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<Dict>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * 约战
     * 规则:
     * 时间地点完全匹配上即可
     *
     * @return
     */
    @RequestMapping(value = "/willPK", method = RequestMethod.POST)
    @ApiOperation(value = "约战", notes = "返回码:1成功;")
    public ResponseEntity willPK(@RequestParam String openid, @RequestParam Long teamid, @RequestParam String date, @RequestParam Long timeid, @RequestParam Long areaid) {
        try {
            PkTeam pkTeam = pkTeamMapper.selectById(teamid);
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);
            PkParkRelation pkParkRelation = pkParkRelationMapper.selectById(timeid);//获取时间段的球场信息
            //匹配
            PkMatch mPkMatch = matching(timeid, areaid, date, pkTeam.getLevel());
            if (Objects.nonNull(mPkMatch)) {
                PkPark pkPark = pkParkMapper.selectById(pkParkRelation.getParkid());

                mPkMatch.setChallengeteamid(teamid);//挑战方
                mPkMatch.setStatus(2);//待比赛
                mPkMatch.setParkid(pkPark.getId());//球场id
                pkMatchMapper.updateById(mPkMatch);
                //生成匹配方订单(未支付)
                PkOrder pkOrderHost = new PkOrder();
                pkOrderHost.setAmount(pkPark.getCost());//金额
                pkOrderHost.setMatchid(mPkMatch.getId());
                pkOrderHost.setStatus("0");
                pkOrderHost.setTeamid(mPkMatch.getHostteamid());
                Long noHost = redisTemplate.opsForValue().increment("orderKey", 1);
                if (noHost > 99998) {
                    redisTemplate.opsForValue().set("orderKey", 1);
                }
                pkOrderHost.setNo("y" + DateUtil.getDays() + String.format("%05d", noHost));
                pkOrderMapper.insert(pkOrderHost);


                //生成挑战方订单(未支付)
                PkOrder pkOrderCh = new PkOrder();
                pkOrderCh.setAmount(pkPark.getCost());//金额
                pkOrderCh.setMatchid(mPkMatch.getId());
                pkOrderCh.setStatus("0");
                pkOrderCh.setTeamid(mPkMatch.getChallengeteamid());
                Long noCh = redisTemplate.opsForValue().increment("orderKey", 1);
                if (noCh > 99998) {
                    redisTemplate.opsForValue().set("orderKey", 1);
                }
                pkOrderCh.setNo("y" + DateUtil.getDays() + String.format("%05d", noCh));
                pkOrderMapper.insert(pkOrderCh);

                //发送约战成功短信
                sendSuccMsg(mPkMatch.getId(),pkPark.getId());
            } else {
                PkMatch pkMatch = new PkMatch();
                pkMatch.setArea(areaid);
                pkMatch.setHostteamid(teamid);
                pkMatch.setInitiatorid(pkMembers.get(0).getId());
                pkMatch.setName("约战");
                pkMatch.setStatus(1);//匹配中
                pkMatch.setDate(date);
                pkMatch.setTime(timeid);
                String formatDate = DateUtil.formatDate(DateUtil.parse(date, "yyyyMMdd"), "yyyy-MM-dd");
                pkMatch.setStarttime(DateUtil.parse((formatDate + " " + pkParkRelation.getStart()), "yyyy-MM-dd HH:mm:ss"));
                pkMatch.setEndtime(DateUtil.parse((formatDate + " " + pkParkRelation.getEnd()), "yyyy-MM-dd HH:mm:ss"));
                Long no = redisTemplate.opsForValue().increment("matchKey", 1);
                if (no > 99998) {
                    redisTemplate.opsForValue().set("matchKey", 1);
                }
                pkMatch.setNo("b" + DateUtil.getDays() + String.format("%05d", no));
                pkMatchMapper.insert(pkMatch);
            }

            return ResponseEntity.ok(new CommonResp<String>("约战成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * 约战规则 区域规则
     *
     * @return
     */
    @RequestMapping(value = "/rule", method = RequestMethod.POST)
    @ApiOperation(value = "约战规则，区域规则(1：约战规则；2：区域规则)", notes = "返回码:1成功;")
    public ResponseEntity rule() {
        try {
            Wrapper<PkRule> wrapper = new EntityWrapper<PkRule>();
            return ResponseEntity.ok(new CommonListResp<PkRule>(pkRuleMapper.selectList(wrapper)));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<PkRule>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * @description 球队匹配
     * @author sh00859
     * @date 2018/11/5
     */
    public PkMatch matching(Long timeid, Long areaid, String date, String level) {
        Wrapper<PkMatch> wrapper = new EntityWrapper<PkMatch>();
        wrapper = wrapper.eq("area", areaid).eq("time", timeid).eq("date", date).eq("status", MatchStatusEnum.FINDING.getCode()).groupBy("createdate desc");
        List<PkMatch> list = pkMatchMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    //发送约战成功短信
    public void sendSuccMsg(Long matchid, Long parkid) throws ParseException {
        PkMatch pkMatch = pkMatchMapper.selectById(matchid);
        PkTeam pkTeamHost = pkTeamMapper.selectById(pkMatch.getHostteamid());
        PkMember pkMemberHost = pkMemberMapper.selectById(pkTeamHost.getOwnerid());

        PkTeam pkTeamChallge = pkTeamMapper.selectById(pkMatch.getChallengeteamid());
        PkMember pkMemberChallge = pkMemberMapper.selectById(pkTeamChallge.getOwnerid());
        //东道主短信发送
        String msgHost = "【球王决】尊敬的" + pkMemberHost.getName() + "，您所属的球队" + pkTeamHost.getName() + "约战信息如下：\n" +
                "时间：" + getTime(pkMatch.getTime()) + " ；地点：" + getAddress(parkid) + "；对手：" + pkTeamChallge.getName() + "(" + TeamLevelEnum.calcLevel(pkTeamChallge.getPoint()) + ")\n" +
                "赛制为7+1，裁判为一主两边，同时我们为您的球队赠送恒大冰泉一箱。请您通知参赛队员提前半小时到场热身，并做好参赛准备。";
        String resultHost = new HttpClientUtil().doPost(smsUrl + "smsMob=" + pkMemberHost.getMobile() + "&smsText=" + msgHost, new HashMap<>(), charset);


        //挑战者短信发送
        String msgChallge = "【球王决】尊敬的" + pkMemberChallge.getName() + "，您所属的球队" + pkTeamChallge.getName() + "约战信息如下：\n" +
                "时间：" + getTime(pkMatch.getTime()) + " ；地点：" + getAddress(parkid) + "；对手：" + pkTeamHost.getName() + "(" + TeamLevelEnum.calcLevel(pkTeamHost.getPoint()) + ")\n" +
                "赛制为7+1，裁判为一主两边，同时我们为您的球队赠送恒大冰泉一箱。请您通知参赛队员提前半小时到场热身，并做好参赛准备。";
        String resultChallge = new HttpClientUtil().doPost(smsUrl + "smsMob=" + pkMemberChallge.getMobile() + "&smsText=" + msgChallge, new HashMap<>(), charset);

    }

    //获取时间接口
    public String getTime(Long timeid) throws ParseException {
        PkParkRelation pkParkRelation = pkParkRelationMapper.selectById(timeid);//获取时间段的球场信息
        if (Objects.nonNull(pkParkRelation)) {
            return pkParkRelation.getStart() + "-" + pkParkRelation.getEnd();
        }
        return null;
    }

    //获取地址接口
    public String getAddress(Long parkid) throws ParseException {
        PkPark pkPark = pkParkMapper.selectById(parkid);//获取球场信息
        if (Objects.nonNull(pkPark)) {
            return pkPark.getPkname();
        }
        return null;
    }


}
