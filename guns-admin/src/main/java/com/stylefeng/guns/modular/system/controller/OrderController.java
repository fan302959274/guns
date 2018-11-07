package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.persistence.dao.AreasMapper;
import com.stylefeng.guns.common.persistence.dao.PkOrderMapper;
import com.stylefeng.guns.common.persistence.dao.PkParkMapper;
import com.stylefeng.guns.common.persistence.model.Areas;
import com.stylefeng.guns.common.persistence.model.PkPark;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.system.dao.OrderDao;
import com.stylefeng.guns.modular.system.warpper.OrderWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 订单控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private String PREFIX = "/football/order/";


    @Resource
    PkOrderMapper pkOrderMapper;
    @Resource
    AreasMapper areasMapper;
    @Resource
    PkParkMapper pkParkMapper;
    @Resource
    OrderDao orderDao;


    /**
     * 跳转到比赛管理首页
     */
    @RequestMapping("")
    public String index(Model model) {
        Wrapper<Areas> wrapper = new EntityWrapper<Areas>();
        List<Areas> areasList = areasMapper.selectList(wrapper);
        Wrapper<PkPark> pkParkEntityWrapper = new EntityWrapper<PkPark>();
        List<PkPark> parkList = pkParkMapper.selectList(pkParkEntityWrapper);
        model.addAttribute("areas", areasList);
        model.addAttribute("parks", parkList);
        return PREFIX + "order.html";
    }


    /**
     * 跳转到查询比赛
     */
    @RequestMapping("/order_view/{orderId}")
    public String pkOrderView(@PathVariable Long orderId, Model model) {
        Map pkOrder = orderDao.selectById(orderId);
        model.addAttribute("order", pkOrder);
        LogObjectHolder.me().set(pkOrder);
        return PREFIX + "order_view.html";
    }


    /**
     * 获取所有比赛列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) Long areas,@RequestParam(required = false) Long parks,@RequestParam(required = false) Long pkstatus,@RequestParam(required = false) String hostname, @RequestParam(required = false) String no, Integer page, Integer pageSize) {

        Integer start = 0;
        Integer size = 10;
        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            start = (page - 1) * pageSize;
            size = pageSize;
        }
        List<Map<String, Object>> list = this.orderDao.selects(areas,parks,pkstatus,hostname,no, start, size);
        return new OrderWarpper(list).warp();
    }

    /**
     * 删除比赛
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long orderId) {
        pkOrderMapper.deleteById(orderId);

        return SUCCESS_TIP;
    }


}
