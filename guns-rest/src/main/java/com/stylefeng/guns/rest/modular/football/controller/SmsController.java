package com.stylefeng.guns.rest.modular.football.controller;

import com.stylefeng.guns.rest.modular.football.transfer.PkMemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 会员控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@RestController
@RequestMapping("/sms")
@Api(value = "SmsController|一个用来测试swagger注解的控制器")
public class SmsController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

//    /**
//     * 短信发送接口
//     *
//     * @param mobile
//     * @return
//     */
//    @RequestMapping(value = "/send", method = RequestMethod.POST)
//    @ApiOperation(value = "发送短信验证码", notes = "返回码:200成功;")
//    @ApiImplicitParam(paramType = "body", name = "pkMemberDto", value = "队员实体", required = true, dataType = "String")
//    public ResponseEntity register(@RequestParam String mobile) {
//        log.info("请求的手机号为:{}",mobile);
//
//        try {
//
//        }
//        return new ResponseEntity(pkMemberDto,HttpStatus.OK);
//    }


}
