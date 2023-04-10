package top.xsword.common_util.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.auth.DefaultCredentials;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.google.gson.Gson;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.util.StringUtils;
import top.xsword.model.object_storage.oss.Connection;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: ywx
 * Create Time: 2023/1/31
 * Description:
 */
public class OSSUtil {

    //OSSClient缓存
    public static Map<String, OSSClient> ossClientCache = new ConcurrentHashMap<>();

    /**
     * 创建一个OSSClient对象
     */
    public static OSSClient createOSSClient(top.xsword.model.entity.Connection connection) {
        //创建默认凭证对象，传入accessKey和accessKeySercret
        DefaultCredentials credentials = new DefaultCredentials(
                connection.getAccessKey(),
                connection.getAccessKeySecret());
        //创建默认凭证行为，传入凭证
        DefaultCredentialProvider credentialProvider = new DefaultCredentialProvider(credentials);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTPS);
        //返回创建的OSSClient对象，传入endpoint和凭证行为
        return new OSSClient(connection.getEndpoint(), credentialProvider, clientConfiguration);
    }

    /**
     * 获取一个OSSClient对象
     */
    public static OSSClient getOSSClient(Connection connectionVo) {
        OSSClient ossClient = ossClientCache.get(connectionVo.getUserToken());
        if (ossClient != null) {
            ossClient.setEndpoint(connectionVo.getEndpoint());
            return ossClient;
        }
        ossClient = createOSSClient(connectionVo.getConnection());
        ossClient.setEndpoint(connectionVo.getEndpoint());
        ossClientCache.put(connectionVo.getUserToken(), ossClient);
        return ossClient;
    }

    /**
     * 测试连接
     */
    public static void testConnection(top.xsword.model.entity.Connection connection) {
        OSSClient ossClient = createOSSClient(connection);
        ossClient.listBuckets();
    }

    /**
     * 获取bucket列表
     */
    public static List<Bucket> listBucket(Connection connection) {
        OSSClient ossClient = getOSSClient(connection);
        return ossClient.listBuckets();
    }

    /**
     * 获取object列表
     */
    public static ListObjectsV2Result listObjectsV2(Connection connection, ListObjectsV2Request listObjectsV2Request) {
        OSSClient ossClient = getOSSClient(connection);
        return ossClient.listObjectsV2(listObjectsV2Request);
    }

    /**
     * 获取object元数据
     */
    public static ObjectMetadata objectMetadata(Connection connection, String bucketName, String key) {
        OSSClient ossClient = getOSSClient(connection);
        return ossClient.getObjectMetadata(bucketName, key);
    }

    /**
     * 删除单个指定的object
     */
    public static void deleteObject(Connection connection, GenericRequest genericRequest) {
        OSSClient ossClient = getOSSClient(connection);
        ossClient.deleteObject(genericRequest);
    }

    /**
     * 删除单个指定的object
     */
    public static void deleteObject(Connection connection, String bucketName, String key) {
        OSSClient ossClient = getOSSClient(connection);
        ossClient.deleteObject(bucketName, key);
    }

    /**
     * 删除多个指定的object
     */
    public static void deleteObjects(Connection connection, String bucketName, List<String> keys) {
        OSSClient ossClient = getOSSClient(connection);
        ossClient.deleteObjects(new DeleteObjectsRequest(bucketName)
                .withKeys(keys)
                .withEncodingType("url")
                .withQuiet(true));
    }

    /**
     * 删除指定目录
     */
    public static void deleteFolder(Connection connection, String bucketName, String prefix) {
        //列举所有包含指定前缀的文件删除
        String continuationToken = null;
        ListObjectsV2Result listObjectsV2Result;
        do {
            //构造请求参数
            ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request(bucketName)
                    .withPrefix(prefix)
                    .withContinuationToken(continuationToken);
            //查询出结果后放到自己的vo里转换一下
            listObjectsV2Result = listObjectsV2(connection, listObjectsV2Request);

            //如果有文件或目录才删
            if (listObjectsV2Result.getObjectSummaries().size() > 0) {
                List<String> keys = new ArrayList<>();
                //循环删除
                listObjectsV2Result.getObjectSummaries().forEach((objectSummary) -> {
                    keys.add(objectSummary.getKey());
                });
                //调用删除方法，删除该目录下所有文件以及该目录
                deleteObjects(connection, bucketName, keys);
            }
            //如果后面还有，就继续获取
            continuationToken = listObjectsV2Result.getNextContinuationToken();
        } while (listObjectsV2Result.isTruncated());
    }

    /**
     * 创建目录
     */
    public static void createFolder(Connection connection, String bucketName, String folder) {
        OSSClient ossClient = getOSSClient(connection);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folder, new ByteArrayInputStream("".getBytes()));
        ossClient.putObject(putObjectRequest);
    }

    /**
     * 生成文件的访问链接
     */
    public static String generatePresignedUrl(Connection connection, String bucketName, String key, Date expiration) throws UnsupportedEncodingException {
        OSS ossClient = getOSSClient(connection);
        ObjectMetadata objectMetadata = objectMetadata(connection, bucketName, key);

        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);

        ResponseHeaderOverrides responseHeader = new ResponseHeaderOverrides();
        String[] split = key.split("/");
        responseHeader.setContentDisposition("attachment;filename=" + URLEncoder.encode(split[split.length - 1], "utf-8"));
        responseHeader.setContentType(objectMetadata.getContentType());

        request.setExpiration(expiration);
        request.setResponseHeaders(responseHeader);

        URL url = ossClient.generatePresignedUrl(request);
        return url.toString();
    }
}
