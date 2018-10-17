package com.stylefeng.guns.rest.modular.auth.validator.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.PkMemberMapper;
import com.stylefeng.guns.rest.common.persistence.model.PkMember;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.modular.auth.validator.dto.Credence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账号密码验证
 *
 * @author fengshuonan
 * @date 2017-08-23 12:34
 */
@Service
public class DbValidator implements IReqValidator {

    @Autowired
    PkMemberMapper pkMemberMapper;

    @Override
    public boolean validate(Credence credence) {
        List<PkMember> pkMembers = pkMemberMapper.selectList(new EntityWrapper<PkMember>().eq("account", credence.getCredenceName()));
        if (pkMembers != null && pkMembers.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
