package com.stylefeng.guns.rest.modular.football.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.common.util.response.CommonListResp;
import com.stylefeng.guns.rest.common.util.response.CommonResp;
import com.stylefeng.guns.rest.common.util.response.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
    RedisTemplate redisTemplate;


    /**
     * 约战时间段
     *
     * @return
     */
    @RequestMapping(value = "/times", method = RequestMethod.POST)
    @ApiOperation(value = "约战时间段", notes = "返回码:1成功;")
    public ResponseEntity times() {
        try {
            Wrapper<Dict> wrapper = new EntityWrapper<Dict>();
            wrapper.eq("pid", "39");
            List<Dict> list = dictMapper.selectList(wrapper);
            List<Map> datas = new ArrayList<>();
            list.forEach(dict -> {
                Map map = new HashMap();
                map.put("timeid", dict.getId());
                map.put("time", dict.getName());
                datas.add(map);
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
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "该队员不是队长"));
            }

            Wrapper<PkTeamMember> pkTeamMemberWrapper = new EntityWrapper<PkTeamMember>();
            pkTeamMemberWrapper = pkTeamMemberWrapper.eq("memberid", pkMembers.get(0).getId());
            List<PkTeamMember> pkTeamMembers = pkTeamMemberMapper.selectList(pkTeamMemberWrapper);
            if (CollectionUtils.isEmpty(pkTeamMembers)){
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "该队员未加入任何队伍"));
            }

            Long openTeamId = pkTeamMembers.get(0).getTeamid();
            PkTeam pkTeamOpen = pkTeamMapper.selectById(openTeamId);
            Integer matchSumOpen = (null == pkTeamOpen.getWinnum() ? 0 : pkTeamOpen.getWinnum()) + (null == pkTeamOpen.getDebtnum() ? 0 : pkTeamOpen.getDebtnum()) + (null == pkTeamOpen.getDrawnum() ? 0 : pkTeamOpen.getDrawnum());
            if (matchSumOpen < 5) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "挑战队伍比赛未满5次"));
            }
            //东道主
            Wrapper<PkMatch> pkMatchWrapperHostOpen = new EntityWrapper<PkMatch>();
            pkMatchWrapperHostOpen = pkMatchWrapperHostOpen.eq("hostteamid", openTeamId);
            List<PkMatch> pkMatchesHostOpen = pkMatchMapper.selectList(pkMatchWrapperHostOpen);
            if (CollectionUtils.isNotEmpty(pkMatchesHostOpen)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "挑战队伍约战中"));
            }
            //被挑战
            Wrapper<PkMatch> pkMatchWrapperChallengeOpen = new EntityWrapper<PkMatch>();
            pkMatchWrapperChallengeOpen = pkMatchWrapperChallengeOpen.eq("challengeteamid", openTeamId);
            List<PkMatch> pkMatchesChallengeOpen = pkMatchMapper.selectList(pkMatchWrapperChallengeOpen);
            if (CollectionUtils.isNotEmpty(pkMatchesChallengeOpen)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "挑战队伍约战中"));
            }




            //-------------teamid相关-----------------------

            PkTeam pkTeam = pkTeamMapper.selectById(teamid);
            Assert.notNull(pkTeam, "未获取到球队");
            Integer matchSum = (null == pkTeam.getWinnum() ? 0 : pkTeam.getWinnum()) + (null == pkTeam.getDebtnum() ? 0 : pkTeam.getDebtnum()) + (null == pkTeam.getDrawnum() ? 0 : pkTeam.getDrawnum());
            if (matchSum < 5) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "匹配队伍比赛未满5次"));
            }

            //东道主
            Wrapper<PkMatch> pkMatchWrapperHost = new EntityWrapper<PkMatch>();
            pkMatchWrapperHost = pkMatchWrapperHost.eq("hostteamid", teamid);
            List<PkMatch> pkMatchesHost = pkMatchMapper.selectList(pkMatchWrapperHost);
            if (CollectionUtils.isNotEmpty(pkMatchesHost)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "匹配队伍约战中"));
            }
            //被挑战
            Wrapper<PkMatch> pkMatchWrapperChallenge = new EntityWrapper<PkMatch>();
            pkMatchWrapperChallenge = pkMatchWrapperChallenge.eq("challengeteamid", teamid);
            List<PkMatch> pkMatchesChallenge = pkMatchMapper.selectList(pkMatchWrapperChallenge);
            if (CollectionUtils.isNotEmpty(pkMatchesChallenge)) {
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "匹配队伍约战中"));
            }


            return ResponseEntity.ok(new CommonResp<String>("可约战"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
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

            Wrapper<PkMatch> pkMatchWrapper = new EntityWrapper<PkMatch>();
            pkMatchWrapper = pkMatchWrapper.eq("hostteamid", teamid);
            Integer count = pkMatchMapper.selectCount(pkMatchWrapper);
            if (count>0){
                return ResponseEntity.ok(new CommonResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), "队伍已约战"));
            }


            PkTeam pkTeam = pkTeamMapper.selectById(teamid);
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("openid", openid);
            List<PkMember> pkMembers = pkMemberMapper.selectList(wrapper);

            //匹配
            PkMatch mPkMatch = matching(timeid, areaid, pkTeam.getLevel());
            if (Objects.nonNull(mPkMatch)) {
                mPkMatch.setChallengeteamid(teamid);//挑战方
                mPkMatch.setStatus(2);//待比赛
                pkMatchMapper.updateById(mPkMatch);
                //生成匹配方订单(未支付)
                PkOrder pkOrderHost = new PkOrder();
                pkOrderHost.setAmount(new BigDecimal("5.00"));//金额
                pkOrderHost.setMatchid(mPkMatch.getId());
                pkOrderHost.setStatus("0");
                pkOrderHost.setTeamid(mPkMatch.getHostteamid());
                Long noHost = redisTemplate.opsForValue().increment("orderKey",1);
                if(noHost>99998){
                    redisTemplate.opsForValue().set("orderKey",1);
                }
                pkOrderHost.setNo("b"+ DateUtil.getDays()+String.format("%05d",noHost));
                pkOrderMapper.insert(pkOrderHost);


                //生成挑战方订单(未支付)
                PkOrder pkOrderCh = new PkOrder();
                pkOrderCh.setAmount(new BigDecimal("5.00"));//金额
                pkOrderCh.setMatchid(mPkMatch.getId());
                pkOrderCh.setStatus("0");
                pkOrderCh.setTeamid(mPkMatch.getHostteamid());
                Long noCh = redisTemplate.opsForValue().increment("orderKey",1);
                if(noCh>99998){
                    redisTemplate.opsForValue().set("orderKey",1);
                }
                pkOrderCh.setNo("b"+ DateUtil.getDays()+String.format("%05d",noCh));
                pkOrderMapper.insert(pkOrderCh);

            } else {
                PkMatch pkMatch = new PkMatch();
                pkMatch.setArea(areaid);
                pkMatch.setHostteamid(teamid);
                pkMatch.setInitiatorid(pkMembers.get(0).getId());
                pkMatch.setName("约战");
                pkMatch.setStatus(1);//匹配中
                pkMatch.setDate(date);
                pkMatch.setTime(timeid);
                Long no = redisTemplate.opsForValue().increment("matchKey",1);
                if(no>99998){
                    redisTemplate.opsForValue().set("matchKey",1);
                }
                pkMatch.setNo("y"+ DateUtil.getDays()+String.format("%05d",no));
                pkMatchMapper.insert(pkMatch);
            }

            return ResponseEntity.ok(new CommonResp<String>("约战成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonListResp<String>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }


    /**
     * @description 球队匹配
     * @author sh00859
     * @date 2018/11/5
     */
    public PkMatch matching(Long timeid, Long areaid, String leavel) {
        Wrapper<PkMatch> wrapper = new EntityWrapper<PkMatch>();
        wrapper = wrapper.eq("area", areaid).eq("time", timeid).groupBy("createdate desc");
        List<PkMatch> list = pkMatchMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

}
