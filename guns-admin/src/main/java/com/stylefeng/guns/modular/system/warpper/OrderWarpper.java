package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 比赛列表的包装类
 *
 * @author fengshuonan
 * @date 2017年2月19日10:59:02
 */
public class OrderWarpper extends BaseControllerWarpper {

    public OrderWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
//        map.put("challengepaystatusName", ConstantFactory.me().getPayStatusName((Integer) map.get("challengepaystatus")));
//        map.put("hostpaystatusName", ConstantFactory.me().getPayStatusName((Integer) map.get("hostpaystatus")));

    }

}
