package com.stylefeng.guns.rest.modular.football.controller;

import com.stylefeng.guns.rest.modular.football.transfer.PkMemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 会员控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@Controller
@RequestMapping("/member")
@Api(value = "SayController|一个用来测试swagger注解的控制器")
public class MemberController {

    /**
     * 队员注册接口
     *
     * @param pkMemberDto
     * @return
     */
    @RequestMapping(value= "/register",method= RequestMethod.POST)
    @ApiOperation(value="根据用户编号获取用户姓名", notes="test: 仅1和2有正确返回")
    @ApiImplicitParam(paramType="body", name = "pkMemberDto", value = "队员实体", required = true, dataType = "PkMemberDto")
    public ResponseEntity hello(@RequestBody PkMemberDto pkMemberDto) {
        System.out.println(pkMemberDto.getAccount());
        return ResponseEntity.ok("请求成功!");
    }
}
