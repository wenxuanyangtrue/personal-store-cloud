package top.xsword.system_service.sts;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ResponseHeaderOverrides;
import com.aliyun.tea.TeaException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xsword.common_service.properties.OSSProperties;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Data:2022/11/19
 * Author:ywx
 * Description:
 */
@SpringBootTest
public class Test01 {

    @Resource
    private OSSProperties OSSProperties;

    @Test
    public void stsToken(){
        //构建一个阿里云客户端，用于发起请求。
        //设置调用者（RAM用户或RAM角色）的AccessKey ID和AccessKey Secret。
        String regionId = OSSProperties.getRegionId();
        String accessKey = OSSProperties.getAccessKey();
        String accessKeySecret = OSSProperties.getAccessKeySecret();
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        //构造请求，设置参数。
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setRoleArn("acs:ram::1875285312214172:role/aliyunosstokengeneratorrole");
        request.setRoleSessionName("user");

        //发起请求，并得到响应。
        try {
            AssumeRoleResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    @Test
    public void stsToken2() throws Exception {
        // 工程代码泄露可能会导致AccessKey泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        com.aliyun.sts20150401.Client client = createClient(OSSProperties.getAccessKey(), OSSProperties.getAccessKeySecret());
        com.aliyun.sts20150401.models.AssumeRoleRequest assumeRoleRequest = new com.aliyun.sts20150401.models.AssumeRoleRequest();
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        assumeRoleRequest.setRoleArn("acs:ram::1875285312214172:role/aliyunosstokengeneratorrole");
        assumeRoleRequest.setRoleSessionName("user");
        try {
            // 复制代码运行请自行打印 API 的返回值
            com.aliyun.sts20150401.models.AssumeRoleResponse assumeRoleResponse = client.assumeRoleWithOptions(assumeRoleRequest, runtime);
            System.out.println(new Gson().toJson(assumeRoleResponse));
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }

    public static com.aliyun.sts20150401.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "sts.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.sts20150401.Client(config);
    }

    @Test
    public void t2(){
        Date date = new Date(new Date().getTime() + OSSProperties.getDownloadExpireTime() * 1000 * 60);
        System.out.println(date.toLocaleString());
    }

    @Test
    public void test03() throws UnsupportedEncodingException {
        String key = "avatar/hp.jpg";
        OSS ossClient = new OSSClientBuilder()
                .build(OSSProperties.getEndpoint(), OSSProperties.getAccessKey(), OSSProperties.getAccessKeySecret());

        Date expiration = new Date(new Date().getTime() + OSSProperties.getDownloadExpireTime() * 1000 * 60);
        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest(OSSProperties.getBucket(), key, HttpMethod.GET);

        ResponseHeaderOverrides responseHeader = new ResponseHeaderOverrides();
        String[] split = key.split("/");
        responseHeader.setContentDisposition("attachment;filename=" + URLEncoder.encode(split[split.length - 1],"utf-8"));
        responseHeader.setContentType("application/octet-stream");

        request.setExpiration(expiration);
        request.setResponseHeaders(responseHeader);

        URL url = ossClient.generatePresignedUrl(request);
        System.out.println(url.toString());
    }
}
