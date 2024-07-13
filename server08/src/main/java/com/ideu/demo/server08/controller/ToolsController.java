package com.ideu.demo.server08.controller;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.json.JSONUtil;
import com.ideu.demo.server08.common.Tree;
import com.ideu.demo.server08.entity.City;
import com.ideu.demo.server08.service.ToolsService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tools")
public class ToolsController
{

    @Autowired
    private ToolsService toolsService;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("getProvinces")
    @Operation(summary = "获取所有省份信息")
    public SaResult getProvinces()
    {
        List<City> result = toolsService.getProvince();
        return SaResult.ok().data(result);

    }
    @GetMapping("getArea/{pid}")
    @Operation(summary = "根据PID获取区域信息")
    public SaResult getArea(@PathVariable("pid") Long pid)
    {
        List<City> result = toolsService.getArea(pid);
        return SaResult.ok().data(result);

    }

    @GetMapping("getTree")
    @Operation(summary = "构建城市树")
    public SaResult getTree()
    {
        if(stringRedisTemplate.opsForValue().get("region:")==null)
        {
            List<Tree>trees=toolsService.getTree();
            String jsonStr = JSONUtil.toJsonStr(trees);
            stringRedisTemplate.opsForValue().set("region:",jsonStr);

            return SaResult.data(trees);
        }
        return SaResult.data(JSONUtil.parse(stringRedisTemplate.opsForValue().get("region:")));

    }
    // 根据 id 获取城市信息
    @GetMapping("/{id}")
    public City getCityById(@PathVariable Long id) {
        return toolsService.getCityById(id);
    }
    // 文件上传方法保持不变
    @PostMapping("/upload")
    @Operation(summary ="文件上传")
    public SaResult filUpload(MultipartFile file) throws IOException , ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String filename = "pic/" + System.currentTimeMillis() + "-" +file.getOriginalFilename();

        PutObjectArgs args = PutObjectArgs.builder()
                .object(filename)
                .bucket("tjpu22s08")
                .contentType(file.getContentType())
                .stream(file.getInputStream(),file.getSize(),-1)
                .build();
        minioClient.putObject(args);

        return SaResult.ok().setMsg(filename);
    }

}
