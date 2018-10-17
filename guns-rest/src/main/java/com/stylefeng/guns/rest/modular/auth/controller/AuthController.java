package com.stylefeng.guns.rest.modular.auth.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.common.exception.BussinessException;
import com.stylefeng.guns.rest.common.persistence.dao.PkMemberMapper;
import com.stylefeng.guns.rest.common.persistence.model.PkMember;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "dbValidator")
    private IReqValidator reqValidator;

    @Autowired
    private PkMemberMapper pkMemberMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseEntity<?> createAuthenticationToken(AuthRequest authRequest) {

        boolean validate = reqValidator.validate(authRequest);

        if (validate) {
            List<PkMember> pkMembers = pkMemberMapper.selectList(new EntityWrapper<PkMember>().eq("account", authRequest.getCredenceName()));
            if (CollectionUtils.isEmpty(pkMembers)){
                throw new BussinessException(BizExceptionEnum.AUTH_REQUEST_ERROR);
            }
            PkMember pkMember = pkMembers.get(0);
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);
            redisTemplate.opsForValue().set(token, pkMember, 7, TimeUnit.DAYS);//7天过时
            return ResponseEntity.ok(new AuthResponse(token, randomKey));
        } else {
            throw new BussinessException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
    }
}
