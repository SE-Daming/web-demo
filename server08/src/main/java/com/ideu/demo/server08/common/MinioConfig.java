package com.ideu.demo.server08.common;
import io.minio.MinioClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation. Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class MinioConfig
{
    @Value("${upload.minio.endpoint}")
    private String endpoint;

    @Value("${upload.minio.port}")
    private Integer port;

    @Value("${upload.minio.accessKey}")
    private String accessKey;

    @Value("${upload.minio.secretKey}")
    private String secretKey;

    @Value("${upload.minio.secure}")
    private Boolean secure;

    @Bean
    public MinioClient minioClient()
    {
        return MinioClient.builder()
                .endpoint(endpoint, port, secure)
                .credentials(accessKey, secretKey)
                .build();
    }
}
