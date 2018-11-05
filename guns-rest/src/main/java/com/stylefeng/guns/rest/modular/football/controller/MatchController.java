package com.stylefeng.guns.rest.modular.football.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.common.persistence.dao.DictMapper;
import com.stylefeng.guns.rest.common.persistence.dao.PkMatchMapper;
import com.stylefeng.guns.rest.common.persistence.dao.PkMemberMapper;
import com.stylefeng.guns.rest.common.persistence.dao.PkTeamMapper;
import com.stylefeng.guns.rest.common.persistence.model.Dict;
import com.stylefeng.guns.rest.common.persistence.model.PkMatch;
import com.stylefeng.guns.rest.common.persistence.model.PkMember;
import com.stylefeng.guns.rest.common.persistence.model.PkTeam;
import com.stylefeng.guns.rest.common.util.response.CommonListResp;
import com.stylefeng.guns.rest.common.util.response.CommonResp;
import com.stylefeng.guns.rest.common.util.response.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

            //匹配
            PkMatch mPkMatch = matching(timeid, areaid, pkTeam.getLevel());
            if (Objects.nonNull(mPkMatch)) {
                mPkMatch.setChallengeteamid(teamid);//挑战方
                mPkMatch.setStatus(2);//待比赛
                pkMatchMapper.updateById(mPkMatch);
            } else {
                PkMatch pkMatch = new PkMatch();
                pkMatch.setArea(areaid);
                pkMatch.setHostteamid(teamid);
                pkMatch.setInitiatorid(pkMembers.get(0).getId());
                pkMatch.setName("约战");
                pkMatch.setStatus(1);//匹配中
                pkMatch.setTime(timeid);
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
