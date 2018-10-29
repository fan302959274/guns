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
    /**
     * 根据条件查询球队队员列表
     *
     * @return
     * @date 2018年10月12日 下午11:14:34
     */
    List<Map<String, Object>> selectTeamsMembers(@Param("teamId") Long teamId);

    /**
     * 通过账号获取用户
     *
     * @param memberId
     * @return
     * @date 2017年2月17日 下午11:07:46
     */
    Map<String, Object> getTeamInfo(@Param("memberId") Integer memberId,@Param("type") Integer type);
    /**
     * 查询所有区
     *
     * @param
     * @return
     * @date 2017年2月17日 下午11:07:46
     */
    List<Map<String, Object>> getAreaInfos();
    /**
     * 删除球队下队友的队友关系
     *
     * @param teamId
     * @return
     * @date 2017年2月17日 下午11:07:46
     */
    int deleteTeamMembers(Long teamId);
}
