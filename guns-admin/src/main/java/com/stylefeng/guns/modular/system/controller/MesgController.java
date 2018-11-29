package com.stylefeng.guns.modular.system.controller;


import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.PkMesgMapper;
import com.stylefeng.guns.common.persistence.model.PkMember;
import com.stylefeng.guns.common.persistence.model.PkMesg;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.core.util.httpclient.HttpClientUtil;
import com.stylefeng.guns.modular.system.dao.MemberDao;
import com.stylefeng.guns.modular.system.dao.MesgDao;
import com.stylefeng.guns.modular.system.warpper.MesgWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告控制器
 *
 * @author fengshuonan
 * @Date 2018-10-08 22:51:10
 */
@Controller
@RequestMapping("/mesg")
public class MesgController extends BaseController {

    private String PREFIX = "/football/mesg/";
    @Resource
    MesgDao mesgDao;
    @Resource
    MemberDao memberDao;
    @Resource
    PkMesgMapper pkMesgMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Value("${sms.url}")
    private String smsUrl;
    @Value("${sms.charset}")
    private String charset;

    /**
     * 跳转到消息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "mesg.html";
    }

    /**
     * 获取球场列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String mesgname) {
        List<Map<String, Object>> mesgs = this.mesgDao.selectMesgs(mesgname);
        return super.warpObject(new MesgWarpper(mesgs));
    }

    /**
     * 跳转到添加消息
     */
    @RequestMapping("/mesg_add")
    public String parkAdd(Model model) {
        return PREFIX + "mesg_add.html";
    }

    /**
     * 新增消息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PkMesg mesg) {
        this.pkMesgMapper.insert(mesg);
        String type = null;
        if (!"0".equals(mesg.getObjtype())) {
            type = mesg.getObjtype();
        }
        List<PkMember> members = memberDao.selectAllMemberByType(type);
        //开关开启才发送
        Object switchFlag = redisTemplate.opsForValue().get("sms:switch");
        if ((switchFlag == null) ? false : (Boolean.parseBoolean(switchFlag.toString()))) {
            members.forEach(member -> {
                new HttpClientUtil().doPost(smsUrl + "smsMob=" + member.getAccount() + "&smsText=【球王决】" + mesg.getContent(), new HashMap<>(), charset);
            });
        }
        return super.SUCCESS_TIP;
    }


    /**
     * 跳转到修改消息
     */
    @RequestMapping("mesg_edit/{mesgId}")
    public String bannerUpdate(@PathVariable Long mesgId, Model model) {
        if (ToolUtil.isEmpty(mesgId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PkMesg pkMesg = pkMesgMapper.selectById(mesgId);
        model.addAttribute(pkMesg);
        model.addAttribute("creatTime", ss.format(pkMesg.getCreatedate()));
        LogObjectHolder.me().set(pkMesg);
        return PREFIX + "mesg_edit.html";
    }


    /**
     * 删除消息
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public Object delete(@RequestParam Long mesgId) {
        this.pkMesgMapper.deleteById(mesgId);
        return SUCCESS_TIP;
    }


    /**
     * 修改消息
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object update(@Valid PkMesg mesg, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        this.pkMesgMapper.updateById(mesg);
        String type = null;
        if (!"0".equals(mesg.getObjtype())) {
            type = mesg.getObjtype();
        }
        List<PkMember> members = memberDao.selectAllMemberByType(type);
        //开关开启才发送
        Object switchFlag = redisTemplate.opsForValue().get("sms:switch");
        if ((switchFlag == null) ? false : (Boolean.parseBoolean(switchFlag.toString()))) {
            members.forEach(member -> {
                new HttpClientUtil().doPost(smsUrl + "smsMob=" + member.getAccount() + "&smsText=【球王决】" + mesg.getContent(), new HashMap<>(), charset);
            });
        }
        return SUCCESS_TIP;

    }

    /**
     * 消息详情
     */
    @RequestMapping(value = "/detail/{mesgId}")
    public String detail(@PathVariable("mesgId") Long mesgId, Model model) {
        if (ToolUtil.isEmpty(mesgId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PkMesg pkMesg = pkMesgMapper.selectById(mesgId);
        model.addAttribute("creatTime", ss.format(pkMesg.getCreatedate()));
        model.addAttribute(pkMesg);
        LogObjectHolder.me().set(pkMesg);
        return PREFIX + "mesg_view.html";
    }
}
