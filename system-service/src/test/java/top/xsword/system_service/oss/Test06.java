package top.xsword.system_service.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.ObjectMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xsword.common_util.oss.OSSUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: ywx
 * Create Time: 2023/2/8
 * Description:
 */
@SpringBootTest
public class Test06 {

    @Resource
    OSSClient ossClient;

    @Test
    public void f2() {
//        List<String> keys = new ArrayList<>();
//        keys.add("asdas/单例模式.md");
//        keys.add("asdas/模板模式.md");
//        ObjectMetadata objectMetadata = ossClient.getObjectMetadata("personal-store-cloud", "avatar/");
//        System.out.println(objectMetadata.getRawMetadata());
//        System.out.println(objectMetadata.getUserMetadata());
    }

    @Test
    public void f1() {
        List<String> keys = new ArrayList<>();
        keys.add("asdas/单例模式.md");
        keys.add("asdas/模板模式.md");
        ossClient.deleteObjects(new DeleteObjectsRequest("personal-store-cloud")
                .withKeys(keys)
                .withEncodingType("url")
                .withQuiet(true));
    }
}
