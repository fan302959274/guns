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
public interface MemberDao {

    /**
     * 根据条件查询队员/队长列表
     *
     * @return
     * @date 2018年10月18日 下午9:14:34
     */
    List<Map<String, Object>> selectMembers(@Param("account") String account,@Param("type") String type);

    /**
     * 修改球场状态
     *
     * @param
     * @date 2018年10月9日 下午9:42:31
     */
    int setStatus(@Param("parkId") Integer parkId, @Param("status") int status);


}
