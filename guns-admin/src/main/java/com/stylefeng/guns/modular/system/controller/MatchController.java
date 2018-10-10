package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.PkMatchMapper;
import com.stylefeng.guns.common.persistence.model.PkMatch;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.transfer.PkMatchDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
    public String matchUpdate(@PathVariable Integer matchId, Model model) {
        PkMatch pkMatch = pkMatchMapper.selectById(matchId);
        model.addAttribute("match", pkMatch);
        LogObjectHolder.me().set(pkMatch);
        return PREFIX + "pkMatch_edit.html";
    }

    /**
     * 跳转到查询比赛
     */
    @RequestMapping("/pkMatch_view/{matchId}")
    public String pkMatchView(@PathVariable Integer matchId, Model model) {
        PkMatch pkMatch = pkMatchMapper.selectById(matchId);
        model.addAttribute("match", pkMatch);
        LogObjectHolder.me().set(pkMatch);
        return PREFIX + "member_view.html";
    }


    /**
     * 获取所有比赛列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String account, Integer page, Integer pageSize) {
        RowBounds rowBounds = new RowBounds();
        Wrapper<PkMatch> wrapper = new EntityWrapper<>();
        if (StringUtils.isNoneBlank(account)) {
            wrapper = wrapper.like("account", account);
        }
        List<PkMatch> list = this.pkMatchMapper.selectPage(rowBounds, wrapper);
        return list;
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{matchId}")
    @ResponseBody
    public Object detail(@PathVariable("matchId") Integer matchId) {
        return pkMatchMapper.selectById(matchId);
    }

    /**
     * 修改比赛
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PkMatchDto pkMatchDto) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (ToolUtil.isEmpty(pkMatchDto) || pkMatchDto.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        PkMatch record = new PkMatch();
        PropertyUtils.copyProperties(record, pkMatchDto);
        pkMatchMapper.updateById(record);

        return super.SUCCESS_TIP;
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
