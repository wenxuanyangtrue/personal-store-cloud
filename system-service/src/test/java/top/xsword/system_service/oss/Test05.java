package top.xsword.system_service.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.auth.DefaultCredentials;
import com.aliyun.oss.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import top.xsword.common_util.oss.OSSUtil;
import top.xsword.common_util.redis.StringRedisUtil;
import top.xsword.model.entity.Connection;
import top.xsword.model.entity.User;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Date;

/**
 * Author: ywx
 * Create Time: 2023/1/31
 * Description:
 */
@SpringBootTest
public class Test05 {

    @Resource
    StringRedisUtil stringRedisUtil;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    OSSClient ossClient;

    @Test
    public void t5() {

    }

    @Test
    public void t4() {
        RestoreObjectResult ror = ossClient.restoreObject("aaaaasdw", "开题答辩回答的主要问题.docx");
        System.out.println(ror.getStatusCode());
    }


    @Test
    public void t3() {
//        OSSObject aaaaasdw = ossClient.getObject("aaaaasdw", "2023年2月8日 13:34:48（最新版本）");
        URL aaaaasdw = ossClient.generatePresignedUrl("aaaaasdw", "开题答辩回答的主要问题.docx", new Date(System.currentTimeMillis() + 36000));
        System.out.println(aaaaasdw.toString());
    }

    @Test
    public void t2() {
//        ObjectMetadata objectMetadata = ossClient.getObjectMetadata("personal-store-cloud", "1月30日.mp3");
        ObjectMetadata objectMetadata = ossClient.getObjectMetadata("aaaaasdw", "(2-3)本科毕业论文（设计）开题报告答辩记录.docx");
        System.out.println(objectMetadata.getRawMetadata());
        System.out.println(objectMetadata.getUserMetadata());

        System.out.println(objectMetadata.getVersionId());
    }

    @Test
    public void t1() {

    }
}
