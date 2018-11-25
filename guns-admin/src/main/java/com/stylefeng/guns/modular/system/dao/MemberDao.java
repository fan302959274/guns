package com.stylefeng.guns.modular.system.dao;


import com.stylefeng.guns.common.persistence.model.PkMember;
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
     * 根据账户查询用户
     *
     * @param
     * @date 2018年10月9日 下午9:42:31
     */
    PkMember selectMemberByAcount(@Param("account") String account);

    List<PkMember> selectAllMemberByType(@Param("type") String type);

}
