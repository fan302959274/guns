package com.stylefeng.guns.modular.system.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.persistence.dao.*;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.enums.MatchStatusEnum;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.httpclient.HttpClientUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * @author sh00859
 * @description 修改比赛状态
 * 待比赛的比赛到达指定时间改成约战中
 * 约战结束的比赛
 * @date 2018/11/21
 */
@Component
public class PkBallTask {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    PkMatchMapper pkMatchMapper;
    @Autowired
    PkTeamMapper pkTeamMapper;
    @Autowired
    PkTeamReviewMapper pkTeamReviewMapper;
    @Autowired
    PkMemberMapper pkMemberMapper;
    @Autowired
    PkParkRelationMapper pkParkRelationMapper;
    @Autowired
    PkParkMapper pkParkMapper;

    @Value("${sms.url}")
    private String smsUrl;
    @Value("${sms.charset}")
    private String charset;

    /**
     * 比赛状态修改，每隔5秒执行一次
     */
    @Scheduled(fixedRate = 5000)
    public void task() {
        //所有待比赛的时间一到改成约战中  一过就改为约战完成
        Wrapper<PkMatch> wrapper = new EntityWrapper<PkMatch>();
        wrapper = wrapper.eq("status", MatchStatusEnum.WAITING.getCode());
        List<PkMatch> list = pkMatchMapper.selectList(wrapper);
        list.forEach(pkMatch -> {
            String status = MatchStatusEnum.WAITING.getCode();
            Date start = pkMatch.getStarttime();
            Date end = pkMatch.getEndtime();
            if (start.getTime() < System.currentTimeMillis() && end.getTime() > System.currentTimeMillis()) {
                status = MatchStatusEnum.MATCHING.getCode();//约战中
            }
            if (end.getTime() < System.currentTimeMillis()) {
                status = MatchStatusEnum.COMPLETE.getCode();//约战完成
            }
            pkMatch.setStatus(Integer.parseInt(status));
            pkMatchMapper.updateById(pkMatch);
        });

        //超过12小时为匹配成功的就标记匹配失败
        wrapper = new EntityWrapper<PkMatch>();
        wrapper = wrapper.eq("status", MatchStatusEnum.FINDING.getCode());
        list = pkMatchMapper.selectList(wrapper);
        list.forEach(pkMatch -> {
            Date createDate = pkMatch.getCreatedate();
            Date now = new Date();
            long times = (now.getTime() - createDate.getTime());
            if (times >= 12 * 60 * 60 * 1000) {
                pkMatch.setStatus(Integer.parseInt(MatchStatusEnum.FAIL.getCode()));
                pkMatchMapper.updateById(pkMatch);
                try {
                    sendFailMsg(pkMatch.getId());
                } catch (ParseException e) {
                    log.error("约战失败短信发送失败");
                }
            }
        });


    }

    /**
     * 球队评分统计，每隔60秒执行一次
     */
    @Scheduled(fixedRate = 60000)
    public void task2() {
        Wrapper<PkTeam> wrapper = new EntityWrapper<PkTeam>();
        List<PkTeam> teamList = pkTeamMapper.selectList(wrapper);
        for (PkTeam pkTeam : teamList) {
            Wrapper<PkTeamReview> pkTeamReviewWrapper = new EntityWrapper<PkTeamReview>();
            pkTeamReviewWrapper.eq("teamid", pkTeam.getId());
            List<PkTeamReview> reviews = pkTeamReviewMapper.selectList(pkTeamReviewWrapper);
            if (CollectionUtils.isNotEmpty(reviews)) {
                BigDecimal total = new BigDecimal("" + reviews.size());
                BigDecimal cultureSum = new BigDecimal("0");
                BigDecimal ontimeSum = new BigDecimal("0");
                BigDecimal friendlySum = new BigDecimal("0");
                for (PkTeamReview pkTeamReview : reviews) {
                    cultureSum = cultureSum.add(pkTeamReview.getCulture());
                    ontimeSum = ontimeSum.add(pkTeamReview.getOntime());
                    friendlySum = friendlySum.add(pkTeamReview.getFriendly());
                }
                BigDecimal cultureAver = cultureSum.divide(total, 2, RoundingMode.HALF_UP);
                BigDecimal ontimeAver = ontimeSum.divide(total, 2, RoundingMode.HALF_UP);
                BigDecimal friendlyAver = friendlySum.divide(total, 2, RoundingMode.HALF_UP);

                pkTeam.setCulture(cultureAver);
                pkTeam.setOntime(ontimeAver);
                pkTeam.setFriendly(friendlyAver);
                pkTeamMapper.updateById(pkTeam);
            }
        }

    }


    //发送约战失败短信
    public void sendFailMsg(Long matchid) throws ParseException {
        PkMatch pkMatch = pkMatchMapper.selectById(matchid);
        PkTeam pkTeamHost = pkTeamMapper.selectById(pkMatch.getHostteamid());
        PkMember pkMemberHost = pkMemberMapper.selectById(pkTeamHost.getOwnerid());

        //东道主短信发送
        String msgHost = "【球王决】尊敬的队长您好，您选择的" + DateUtil.formatDate(DateUtil.parse(pkMatch.getDate(),"yyyyMMdd"),"yyyy年MM月dd日") + pkMatch.getTime() + "约战由于没有实力匹配的对手/球场，因此本次约战订单已被自动取消。请您更换约赛时间或约赛区域，祝您约战成功。";
        String resultHost = new HttpClientUtil().doPost(smsUrl + "smsMob=" + pkMemberHost.getMobile() + "&smsText=" + msgHost, new HashMap<>(), charset);


    }


}