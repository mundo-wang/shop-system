package com.suye.controller;

import com.suye.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author sj.w
 */

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
//        file是一个临时文件，需要转存到指定位置，否则请求完成后自动删除

//        获取文件原始文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

//        使用UUID获得随机文件名（避免重名覆盖）
        String fileName = UUID.randomUUID().toString() + suffix;

//        创建一个目录对象
        File dir = new File(basePath);
        if (!dir.exists()) {
//            目录不存在，将其创建出来
            dir.mkdirs();
        }

        try {
//            将临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("接收到上传的文件：" + file.toString());
        return R.success(fileName);
    }


    /**
     * 文件下载（回显）
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        FileInputStream fis = null;
        ServletOutputStream sos = null;
        try {
            File file = new File(basePath + name);
//            输入流，通过输入流读取文件内容
            fis = new FileInputStream(file);
//            输出流，通过输出流将文件写回浏览器，在浏览器展示
            sos = response.getOutputStream();

            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) != -1) {
                sos.write(bytes, 0, len);
                sos.flush();
            }

        } catch (Exception e) {
            String message = e.getMessage();
            log.info("文件访问失败：" + message);
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
    }
}
