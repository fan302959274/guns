package com.stylefeng.guns.rest.modular.football.controller;

import com.stylefeng.guns.rest.common.util.response.CommonResp;
import com.stylefeng.guns.rest.modular.football.transfer.PkMemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@RestController
@RequestMapping("/member")
@Api(value = "MemberController|一个用来测试swagger注解的控制器")
public class MemberController {

    /**
     * 队员注册接口
     *
     * @param pkMemberDto
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "注册队员", notes = "返回码:20000成功;")
    @ApiImplicitParam(paramType = "body", name = "pkMemberDto", value = "队员实体", required = true, dataType = "PkMemberDto")
    public ResponseEntity register(@RequestBody PkMemberDto pkMemberDto) {
        return ResponseEntity.ok(new CommonResp<PkMemberDto>(pkMemberDto));
    }

    /**
     * 队员信息查询接口
     *
     * @param pkMemberDto
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "查询队员", notes = "返回码:20000成功;")
    @ApiImplicitParam(paramType = "body", name = "pkMemberDto", value = "队员实体", required = true, dataType = "PkMemberDto")
    public ResponseEntity search(@RequestBody PkMemberDto pkMemberDto) {
        return ResponseEntity.ok(new CommonResp<PkMemberDto>(pkMemberDto));
    }

}
