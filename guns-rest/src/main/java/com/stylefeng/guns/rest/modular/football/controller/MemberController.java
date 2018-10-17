package com.stylefeng.guns.rest.modular.football.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.common.enums.AttachCategoryEnum;
import com.stylefeng.guns.rest.common.enums.AttachTypeEnum;
import com.stylefeng.guns.rest.common.persistence.dao.PkAttachmentMapper;
import com.stylefeng.guns.rest.common.persistence.dao.PkMemberMapper;
import com.stylefeng.guns.rest.common.persistence.model.PkAttachment;
import com.stylefeng.guns.rest.common.persistence.model.PkMember;
import com.stylefeng.guns.rest.common.util.response.CommonResp;
import com.stylefeng.guns.rest.common.util.response.ResponseCode;
import com.stylefeng.guns.rest.modular.football.transfer.PkMemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PkMemberMapper pkMemberMapper;
    @Resource
    private PkAttachmentMapper pkAttachmentMapper;

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
        log.info("注册队员请求参数{}", JSONObject.toJSONString(pkMemberDto));
        try {
            Assert.notNull(pkMemberDto.getMobile(), "手机号不能为空");
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("account", pkMemberDto.getMobile());
            Integer count = pkMemberMapper.selectCount(wrapper);
            if (count>0) {
                return ResponseEntity.ok(new CommonResp<PkMember>(ResponseCode.SYSTEM_ERROR.getCode(), "手机号已注册过"));
            }
            PkMember pkMember = new PkMember();
            pkMember.setAccount(pkMemberDto.getMobile());//以手机号作为account
            pkMember.setName(pkMemberDto.getName());
            pkMember.setMobile(pkMemberDto.getMobile());
            pkMember.setType("1");//普通队员
            pkMemberMapper.insert(pkMember);
            //        保存头像
            if (StringUtils.isNoneBlank(pkMemberDto.getAvatar())) {
                PkAttachment pkAttachment = new PkAttachment();
                pkAttachment.setCategory(AttachCategoryEnum.MEMBER.getCode());
                pkAttachment.setType(AttachTypeEnum.HEAD.getCode());
                pkAttachment.setLinkid(pkMember.getId());
                pkAttachment.setName(pkMemberDto.getAvatar());
                pkAttachment.setSuffix(pkMemberDto.getAvatar().substring(pkMemberDto.getAvatar().lastIndexOf(".") + 1));
                pkAttachment.setUrl(pkMemberDto.getAvatar());
                pkAttachmentMapper.insert(pkAttachment);
            }
            //        保存身份证
            if (StringUtils.isNoneBlank(pkMemberDto.getIdcard())) {
                PkAttachment pkAttachment = new PkAttachment();
                pkAttachment.setCategory(AttachCategoryEnum.MEMBER.getCode());
                pkAttachment.setType(AttachTypeEnum.IDCARD.getCode());
                pkAttachment.setLinkid(pkMember.getId());
                pkAttachment.setName(pkMemberDto.getIdcard());
                pkAttachment.setSuffix(pkMemberDto.getIdcard().substring(pkMemberDto.getIdcard().lastIndexOf(".") + 1));
                pkAttachment.setUrl(pkMemberDto.getIdcard());
                pkAttachmentMapper.insert(pkAttachment);
            }
            return ResponseEntity.ok(new CommonResp<PkMemberDto>(pkMemberDto));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<PkMemberDto>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }

    }

    /**
     * 队员信息查询接口
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "查询队员", notes = "返回码:20000成功;")
    @ApiImplicitParam(paramType = "query", name = "mobile", value = "队员实体", required = true, dataType = "String")
    public ResponseEntity search(@RequestParam String mobile) {
        log.info("查询队员请求参数{}", JSONObject.toJSONString(mobile));
        try {
            Assert.notNull(mobile, "手机号不能为空");
            Wrapper<PkMember> wrapper = new EntityWrapper<PkMember>();
            wrapper = wrapper.eq("mobile", mobile);
            List<PkMember> list = pkMemberMapper.selectList(wrapper);
            if (CollectionUtils.isEmpty(list)) {
                return ResponseEntity.ok(new CommonResp<PkMember>(ResponseCode.SYSTEM_ERROR.getCode(), "未获取到队员信息"));
            }

            PkMemberDto pkMemberDto = new PkMemberDto();
            PropertyUtils.copyProperties(pkMemberDto, list.get(0));

            Wrapper<PkAttachment> pkAttachmentWrapper = new EntityWrapper<>();
            pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkMemberDto.getId()).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.HEAD.getCode());
            List<PkAttachment> attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
            if (!org.springframework.util.CollectionUtils.isEmpty(attachmentList)) {
                pkMemberDto.setAvatar(attachmentList.get(0).getUrl());
            }
            pkAttachmentWrapper = new EntityWrapper<>();
            pkAttachmentWrapper = pkAttachmentWrapper.eq("linkid", pkMemberDto.getId()).eq("category", AttachCategoryEnum.MEMBER.getCode()).eq("type", AttachTypeEnum.IDCARD.getCode());
            attachmentList = pkAttachmentMapper.selectList(pkAttachmentWrapper);
            if (!org.springframework.util.CollectionUtils.isEmpty(attachmentList)) {
                pkMemberDto.setIdcard(attachmentList.get(0).getUrl());
            }
            return ResponseEntity.ok(new CommonResp<PkMemberDto>(pkMemberDto));
        } catch (Exception e) {
            return ResponseEntity.ok(new CommonResp<PkMemberDto>(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage()));
        }
    }

}
