package com.qutopia.blog.gateway.web.admin;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 文件资源的控制器, 主要是七牛云存储的对接
 *
 * @author choaklin
 * @date 2018.12.23
 *
 * @see <code>https://developer.qiniu.com/kodo/sdk/1239/java#upload-config</code>
 */
@Slf4j
@RestController
@RequestMapping("resource")
public class ResourceController {

    @Value("${qiniu.access.key}")
    private String qiniu_access_key;
    @Value("${qiniu.secret.key}")
    private String qiniu_secret_key;
    @Value("${qiniu.bucket}")
    private String qiniu_bucket;
    @Value("${qiniu.resource.domain}")
    private String qiniu_resource_domain;

    // 构造一个 华东地区的文件上传管理器
    private final UploadManager uploadManager = new UploadManager(
            new Configuration(Zone.zone0())
    );

    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, "multipart/mixed"})
    public String upload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return null;
        }

        String filename;
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(originalFilename)) {
            filename = originalFilename.split("\\.")[0];
        } else {
            filename = UUID.randomUUID().toString().replaceAll("-", "");
        }

        Auth auth = Auth.create(qiniu_access_key, qiniu_secret_key);
        String token = auth.uploadToken(qiniu_bucket);

        Response response;
        try {
            // todo 图片的高保真压缩
            response = uploadManager.put(file.getBytes(), filename, token);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            // {"hash":"FhSbX--kNPI_mt730Dy_I1zLUbi0","key":"file"}
            return qiniu_resource_domain + putRet.key;
        } catch (QiniuException e) {
            response = e.response;
            try {
                log.error(response.bodyString());
            } catch (QiniuException e1) {
                log.error(ExceptionUtils.getStackTrace(e1));
            }
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }


    /**
     * 文件的删除
     *
     * 文章新增的时候，记录文章使用的图片，如果文章引用的图片不喜欢，在删除的同时也同步删除七牛云空间的资源
     */
    public void delete() {

    }
}
