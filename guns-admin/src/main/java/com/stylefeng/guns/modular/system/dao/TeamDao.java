package com.stylefeng.guns.modular.system.dao;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 广告Dao
 *
 * @author fengshuonan
 * @Date 2018-10-08 22:51:10
 */
public interface TeamDao {

    /**
     * 根据条件查询球队列表
     *
     * @return
     * @date 2018年10月12日 下午11:14:34
     */
    List<Map<String, Object>> selectTeams(@Param("name") String name);

}
