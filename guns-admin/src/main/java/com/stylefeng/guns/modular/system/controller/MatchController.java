package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.PkMatchMapper;
import com.stylefeng.guns.common.persistence.model.PkMatch;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.MatchDao;
import com.stylefeng.guns.modular.system.transfer.PkMatchDto;
import com.stylefeng.guns.modular.system.warpper.AdWarpper;
import com.stylefeng.guns.modular.system.warpper.MatchWarpper;
import com.stylefeng.guns.modular.system.warpper.UserWarpper;
import org.apache.commons.beanutils.PropertyUtils;
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
import java.util.Objects;

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
    MatchDao matchDao;


    /**
     * 跳转到比赛管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "match.html";
    }


    /**
     * 跳转到修改比赛
     */
    @RequestMapping("/match_update/{matchId}")
    public String matchUpdate(@PathVariable Long matchId, Model model) {
        Map pkMatch = matchDao.selectById(matchId);
        model.addAttribute("match", pkMatch);
        LogObjectHolder.me().set(pkMatch);
        return PREFIX + "match_edit.html";
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
     * 获取所有比赛列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String status, @RequestParam(required = false) String paystatus, Integer page, Integer pageSize) {

        Integer start = 0;
        Integer size = 10;
        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            start = (page - 1) * pageSize;
            size = pageSize;
        }
        List<Map<String, Object>> list = this.matchDao.selects(status, paystatus, start, size);
        return new MatchWarpper(list).warp();
    }

    /**
     * 比赛详情
     */
    @RequestMapping(value = "/detail/{matchId}")
    @ResponseBody
    public Object detail(@PathVariable("matchId") Long matchId) {
        Map result = new HashMap();
        result.put("match",this.matchDao.selectById(matchId));
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


}
