package cn.lulucar.file.controller;

import cn.lulucar.common.domain.Result;
import cn.lulucar.file.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author wxl
 * @date 2025/5/8 22:31
 * @description 控制器类，用于处理文件上传和下载请求。
 */
@RestController
@RequestMapping("/file")
public class MinioController {
    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestHeader("X-User-Id") String userId) {
        return minioService.uploadFile(file, userId);
    }

    @GetMapping("/download")
    public Result<String> downloadFile(@RequestParam("filename") String filename, @RequestHeader("X-User-Id") String userId, HttpServletResponse response) {
        return minioService.downloadFile(filename, userId, response);
    }
}
