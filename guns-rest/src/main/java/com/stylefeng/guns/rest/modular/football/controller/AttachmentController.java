package com.stylefeng.guns.rest.modular.football.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.util.response.CommonListResp;
import com.stylefeng.guns.core.util.response.CommonResp;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.common.exception.BussinessException;
import com.stylefeng.guns.rest.common.persistence.dao.PkAttachmentMapper;
import com.stylefeng.guns.rest.common.persistence.model.PkAttachment;
import com.stylefeng.guns.rest.config.properties.GunsProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
    @Resource
    private PkAttachmentMapper pkAttachmentMapper;

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
     * @description 返回图片
     * @author sh00859
     * @date 2018/11/6
     */
    @ApiOperation(value = "获取图片", notes = "返回码:1成功;")
    @GetMapping(value = "/detail/{pictureId}")
    public void renderPicture(@PathVariable("pictureId") String pictureId, HttpServletResponse response) {
        String path = gunsProperties.getFileUploadPath() + pictureId + ".jpg";
        try {
            File file = new File(path);
            log.info("图片大小:{}", file.length());
            //小于50KB不处理
            if (file.length() < 51200) {
                Thumbnails.of(file).scale(1f).toOutputStream(response.getOutputStream());
            } else {
                Thumbnails.of(file).width(200).toOutputStream(response.getOutputStream());
            }
        } catch (Exception e) {
            //如果找不到图片就返回一个默认图片
            try {
                response.sendRedirect("/static/img/girl.gif");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 获取所有的附件
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取所有的附件", notes = "返回码:1成功;")
    public ResponseEntity list(@RequestParam Integer page) {
        Wrapper<PkAttachment> wrapper = new EntityWrapper<PkAttachment>();
        Integer count = pkAttachmentMapper.selectCount(wrapper);
        if (page <= 0) {
            page = 1;
        }
        if (page > count / 20 + 1) {
            page = count / 20 + 1;
        }
        RowBounds rowBounds = new RowBounds((page - 1) * 20, 20);
        wrapper.orderBy("createdate", false);
        List<PkAttachment> list = pkAttachmentMapper.selectPage(rowBounds, wrapper);
        return ResponseEntity.ok(new CommonListResp<PkAttachment>(list));
    }


}
