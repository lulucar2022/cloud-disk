package cn.lulucar.file.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

import java.io.InputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.IOUtils;

/**
 * @author wxl
 * @date 2025/5/9 11:30
 * @description 提供 MinIO 文件上传和下载的服务逻辑。
 */
@Service
@Slf4j
public class MinioService {
    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    /**
     * 上传文件到 MinIO 的用户目录下。
     * 
     * @param file   要上传的文件
     * @param userId 用户 ID
     * @return 操作结果信息
     */
    public String uploadFile(MultipartFile file, String userId) {
        try {
            InputStream inputStream = file.getInputStream();
            String objectName = userId + "/" + file.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return "File uploaded successfully!";
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error uploading file to MinIO", e);
            return "Error uploading file to MinIO: " + e.getMessage();
        }
    }

    /**
     * 从 MinIO 下载指定用户的文件。
     * 
     * @param filename    要下载的文件名
     * @param userId      用户 ID
     * @param response    HttpServletResponse 对象，用于输出文件流
     */
    public void downloadFile(String filename, String userId, HttpServletResponse response) {
        try {
            String objectName = userId + "/" + filename;
            InputStream stream = minioClient.getObject(io.minio.GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());

            // 设置响应头
            response.setContentType("application/octet-stream");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

            // 将文件流写入响应输出流
            IOUtils.copy(stream, response.getOutputStream());
            response.flushBuffer();
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error downloading file from MinIO", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("Error downloading file from MinIO: " + e.getMessage());
            } catch (IOException ex) {
                log.error("Error writing error message to response", ex);
            }
        }
    }
    
}