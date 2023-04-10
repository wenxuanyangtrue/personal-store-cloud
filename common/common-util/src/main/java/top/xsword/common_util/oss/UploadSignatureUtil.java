package top.xsword.common_util.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.google.gson.Gson;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.util.StringUtils;
import top.xsword.common_util.file.FileUtil;
import top.xsword.model.object_storage.oss.Connection;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: ywx
 * Create Time: 2023/2/11
 * Description:
 */
public class UploadSignatureUtil {
    private Map<String, String> signatureParams = new HashMap<>();

    public UploadSignatureUtil(Connection connection, String bucketName, String key, int maxSize, Date expiration) {
        generate(connection, bucketName, key, maxSize, expiration);
    }

    /**
     * 生成上传文件的签名
     *
     * @param connection 连接对象，用于获取OSSClient
     * @param bucketName bucket name
     * @param key        文件key
     * @param maxSize    限制上传文件的大小，单位为MB。
     * @param expiration 限制参数的生效时间，单位为分钟
     */
    public void generate(Connection connection, String bucketName, String key, int maxSize, Date expiration) {
        OSSClient ossClient = OSSUtil.getOSSClient(connection);

        PolicyConditions policyConditions = new PolicyConditions();
        //定义文件大小区间
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, (long) maxSize * 1024 * 1024);
        //定义匹配的目录前缀
        policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, key);

        //生成policy字符串
        String policy = ossClient.generatePostPolicy(expiration, policyConditions);
        //对policy进行签名
        String signature = ossClient.calculatePostSignature(policy);
        //对policy进行base64转码
        String encodePolicy = BinaryUtil.toBase64String(policy.getBytes(StandardCharsets.UTF_8));

        signatureParams.put("ossAccessKeyId", connection.getConnection().getAccessKey());
        signatureParams.put("policy", encodePolicy);
        signatureParams.put("signature", signature);
        signatureParams.put("key", key);
        signatureParams.put("uploadUrl", "https://" + bucketName + "." + connection.getEndpoint());
    }

    public UploadSignatureUtil withCallback(String callbackUrl) {
        //创建回调json
        Map<String, String> callback = new HashMap<>();
        //写入回调地址
        callback.put("callbackUrl", callbackUrl);
        //写入回调接收的参数
        callback.put("callbackBody", "{\"name\":${object},\"size\":${size},\"type\":${mimeType}}");
        //写入回调接收的类型
        callback.put("callbackBodyType", "application/json");
        String callbackJson = BinaryUtil.toBase64String(new Gson().toJson(callback).getBytes(StandardCharsets.UTF_8));
        signatureParams.put("callback", callbackJson);
        return this;
    }

    public UploadSignatureUtil withContentType(String key) {
        //确定文件的ContentType，如果默认的mappings找不到，就找容器中的
        String suffix = FileUtil.getSuffix(key);
        if(suffix == null){
            return this;
        }
        String contentType = MimeMappings.DEFAULT.get(suffix);
        if (!StringUtils.hasLength(contentType)) {
            contentType = MyMimeMappings.mimeMappings.get(suffix);
        }
        signatureParams.put("x-oss-content-type", contentType);
        return this;
    }

    public Map<String, String> build() {
        return signatureParams;
    }
}
