package com.stylefeng.guns.modular.system.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.persistence.dao.PkMatchMapper;
import com.stylefeng.guns.common.persistence.model.PkMatch;
import com.stylefeng.guns.core.enums.MatchStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * @author sh00859
 * @description 修改比赛状态
 * 待比赛的比赛到达指定时间改成约战中
 * 约战结束的比赛
 * @date 2018/11/21
 */
@Component
public class MatchStatusTask {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    PkMatchMapper pkMatchMapper;

    /**
     * 时间间隔，每隔5秒执行一次
     */
    @Scheduled(fixedRate = 5000)
    public void task() {
        log.info("---------------比赛状态同步开始-------------");
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
        log.info("---------------比赛状态同步结束-------------");

    }
}