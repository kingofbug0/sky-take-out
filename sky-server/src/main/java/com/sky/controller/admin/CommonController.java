package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@Api(tags="通用接口")
@RequestMapping("/admin/common")

public class CommonController
{
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    @ApiOperation("功能描述--文件上传")
    public Result<String> upload(MultipartFile file)
    {
        try {
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;
            //文件请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {

        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
