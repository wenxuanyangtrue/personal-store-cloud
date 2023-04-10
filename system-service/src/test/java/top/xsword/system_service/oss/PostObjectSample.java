package top.xsword.system_service.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xsword.common_service.properties.OSSProperties;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Data:2022/11/19
 * Author:ywx
 * Description:
 */
@SpringBootTest
public class PostObjectSample {

    @Resource
    OSSProperties OSSProperties;

    /**
     * 获取表单上传的方式的PostObject参数
     *
     * @return
     */
    @Test
    public void getPostObjectParams() {
        Map<String, Object> respMap = new LinkedHashMap();
        // 限制参数的生效时间，单位为分钟，默认值为20。
        int expireTime = 20;
        // 限制上传文件的大小，单位为MB，默认值为100。
        int maxSize = 100;
        // 设置上传到OSS文件的前缀，可置空此项。置空后，文件将上传至Bucket的根目录下。
        // 如果值为"test"那么前端的key参数必须以"test"开头，如test/*、test1.jpg、test/comment/11.jpg
        String dir = "";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClient(
                OSSProperties.getEndpoint(),
                OSSProperties.getAccessKey(),
                OSSProperties.getAccessKeySecret()
        );
        try {
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000 * 60;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize * 1024 * 1024);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            System.out.println(postPolicy);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap.put("accessKeyId", OSSProperties.getAccessKey());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);

            respMap.put("expire", expireEndTime / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(respMap);
    }
}