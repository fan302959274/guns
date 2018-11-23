package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.AreasMapper;
import com.stylefeng.guns.common.persistence.dao.PkMatchMapper;
import com.stylefeng.guns.common.persistence.dao.PkTeamMapper;
import com.stylefeng.guns.common.persistence.model.Areas;
import com.stylefeng.guns.common.persistence.model.PkMatch;
import com.stylefeng.guns.common.persistence.model.PkTeam;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.MatchDao;
import com.stylefeng.guns.modular.system.transfer.PkMatchDto;
import com.stylefeng.guns.modular.system.warpper.MatchWarpper;
import org.apache.commons.beanutils.PropertyUtils;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 比赛控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/match")
public class MatchController extends BaseController {

    private String PREFIX = "/football/match/";


    @Resource
    PkMatchMapper pkMatchMapper;
    @Resource
    AreasMapper areasMapper;
    @Resource
    PkTeamMapper pkTeamMapper;
    @Resource
    MatchDao matchDao;


    /**
     * 跳转到比赛管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        Wrapper<Areas> wrapper = new EntityWrapper<Areas>();

        List<Areas> list = areasMapper.selectList(wrapper);
        model.addAttribute("areas", list);
        return PREFIX + "match.html";
    }


    /**
     * 跳转到查询比赛
     */
    @RequestMapping("/match_view/{matchId}")
    public String pkMatchView(@PathVariable Long matchId, Model model) {
        Map pkMatch = matchDao.selectById(matchId);
        model.addAttribute("match", pkMatch);
        LogObjectHolder.me().set(pkMatch);
        return PREFIX + "match_view.html";
    }

    /**
     * 跳转到查询比赛
     */
    @RequestMapping("/match_upload_result/{matchId}")
    public String pkMatchUploadResult(@PathVariable Long matchId, Model model) {
        Map pkMatch = matchDao.selectById(matchId);
        model.addAttribute("match", pkMatch);
        LogObjectHolder.me().set(pkMatch);
        return PREFIX + "match_upload_result.html";
    }


    /**
     * 上传比赛结果信息
     */
    @RequestMapping(value = "/uploadResult")
    @ResponseBody
    public Object uploadResult(PkMatchDto pkMatchDto) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (ToolUtil.isEmpty(pkMatchDto) || pkMatchDto.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        PkMatch pkMatch = pkMatchMapper.selectById(pkMatchDto.getId());
        PkTeam pkTeamHost = pkTeamMapper.selectById(pkMatch.getHostteamid());
        PkTeam pkTeamChallenge = pkTeamMapper.selectById(pkMatch.getChallengeteamid());

        PropertyUtils.copyProperties(pkMatch, pkMatchDto);
        //修改比赛结果
        pkMatchMapper.updateById(pkMatch);


        //东道主和挑战者积分修改
        pkTeamHost.setPoint(pkTeamHost.getPoint()+calcGoals(pkMatchDto.getHostgoals()-pkMatchDto.getChallengegoals()));//东道主积分修改
        pkTeamMapper.updateById(pkTeamHost);
        pkTeamChallenge.setPoint(pkTeamChallenge.getPoint()+calcGoals(pkMatchDto.getChallengegoals()-pkMatchDto.getHostgoals()));//挑战者积分修改
        pkTeamMapper.updateById(pkTeamChallenge);

        return super.SUCCESS_TIP;
    }


    /**
     * 获取所有比赛列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) Long areas, @RequestParam(required = false) Long pkstatus, @RequestParam(required = false) String hostname, @RequestParam(required = false) String no, Integer page, Integer pageSize) {
        List<Map<String, Object>> list = this.matchDao.selects(areas, pkstatus, hostname, no);
        return super.warpObject(new MatchWarpper(list));
    }

    /**
     * 比赛详情
     */
    @RequestMapping(value = "/detail/{matchId}")
    @ResponseBody
    public Object detail(@PathVariable("matchId") Long matchId) {
        Map result = new HashMap();
        result.put("match", this.matchDao.selectById(matchId));
        return result;
    }


    /**
     * 删除比赛
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long matchId) {
        pkMatchMapper.deleteById(matchId);

        return SUCCESS_TIP;
    }


    //计算积分points
    public Integer calcGoals(Integer finalgoals) {
        if (finalgoals > 6){
            return 100;
        }else if(3<finalgoals && finalgoals<=6){
            return 50;
        }else if(0<finalgoals && finalgoals<=3){
            return 25;
        }else if(finalgoals==0){
            return 10;
        }else if(-3<=finalgoals && finalgoals<0){
            return -25;
        }else if(-6<=finalgoals && finalgoals<-3){
            return -50;
        }else if(finalgoals<-6){
            return 100;
        }
        return 0;


    }


}
