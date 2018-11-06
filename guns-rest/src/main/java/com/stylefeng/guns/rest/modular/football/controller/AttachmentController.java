package com.stylefeng.guns.rest.modular.football.controller;

import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.common.exception.BussinessException;
import com.stylefeng.guns.rest.common.util.response.CommonResp;
import com.stylefeng.guns.rest.config.properties.GunsProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 附件控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@RestController
@RequestMapping("/attach")
@Api(value = "AttachmentController|一个用来测试swagger注解的控制器")
public class AttachmentController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Resource
    private GunsProperties gunsProperties;

    /**
     * 上传图片(上传到项目的webapp/static/img)
     */

    @ApiOperation(value = "上传附件", notes = "返回码:1成功;")
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public ResponseEntity upload(@RequestPart("file") MultipartFile file) {
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        try {
            String fileSavePath = gunsProperties.getFileUploadPath();
            file.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return ResponseEntity.ok(new CommonResp<String>(pictureName));
    }


    /**
     *@description 返回图片
     *@author sh00859
     *@date 2018/11/6
     */
    @ApiOperation(value = "获取图片", notes = "返回码:1成功;")
    @GetMapping(value = "/detail/{pictureId}")
    public void renderPicture(@PathVariable("pictureId") String pictureId, HttpServletResponse response) {
        String path = gunsProperties.getFileUploadPath() + pictureId + ".jpg";
        try {
            byte[] bytes = FileUtil.toByteArray(path);
            response.getOutputStream().write(bytes);
        } catch (Exception e) {
            //如果找不到图片就返回一个默认图片
            try {
                response.sendRedirect("/static/img/girl.gif");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
