package com.stylefeng.guns.modular.system.dao;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 比赛Dao
 *
 * @author fengshuonan
 * @Date 2018-10-08 22:51:10
 */
public interface MatchDao {

    /**
     * 根据条件分页查询比赛列表
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selects(@Param("areas") Long areas, @Param("pkstatus") Long pkstatus, @Param("hostname") String hostname, @Param("no") String no);


    /**
     * 根据id获取比赛详情
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    Map<String, Object> selectById(@Param("id") Long id);
}
