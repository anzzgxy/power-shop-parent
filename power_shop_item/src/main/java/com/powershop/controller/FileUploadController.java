package com.powershop.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.powershop.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private FastFileStorageClient storageClient;
    //文件类型
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png","image/gif");

    @RequestMapping("/upload")
    public Result upload(MultipartFile file){
        try {
            //文件名
            String originalFilename = file.getOriginalFilename();
            //校验文件类型
            String contentType = file.getContentType();
            if (!CONTENT_TYPES.contains(contentType)){
                //文件类型不合法
                return Result.error("文件类型不合法:"+originalFilename);
            }

            //校验文件内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null){
                return Result.error("文件内容不合法：" + originalFilename);
            }

            //保存到服务器
            String extName = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extName, null);

            //生成URL地址,返回
            return Result.ok("http://image.powershop.com/"+storePath.getFullPath());

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器内部错误");
        }
    }
}
