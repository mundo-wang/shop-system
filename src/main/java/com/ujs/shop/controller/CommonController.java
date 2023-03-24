package com.ujs.shop.controller;

import com.ujs.shop.common.base.BaseController;
import com.ujs.shop.common.global.ConstantBean;
import com.ujs.shop.common.global.ResponseBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author mundo.wang
 * @date 2023/3/22 14:18
 */

@RestController
@RequestMapping("/shop/common")
public class CommonController extends BaseController {


    @Value("${image.path}")
    private String imagePath;


    /**
     * 页面上传文件，存到硬盘，返回文件名
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseBean<String> upload(MultipartFile file) {
//        获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffer = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = ConstantBean.IMAGE_PREFIX + ConstantBean.getUUID().substring(0, 8) + suffer;

        File dir = new File(imagePath);
        if (! dir.exists()) {
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(imagePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseBean.success(fileName);
    }


    /**
     * 文件回显到页面
     * @param fileName
     * @return
     */
    @GetMapping("/download")
    public ResponseBean<?> download(String fileName) {
        FileInputStream fis = null;
        ServletOutputStream sos = null;
        try {
            File file = new File(imagePath + fileName);
            fis = new FileInputStream(file);
            sos = response.getOutputStream();
            if (fileName.contains(".jpg") || fileName.contains(".jpeg")) {
                response.setContentType("image/jpeg");
            } else {
                response.setContentType("image/png");
            }
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) != -1) {
                sos.write(bytes, 0, len);
                sos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (sos != null) {
                    sos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseBean.success();
    }

}
