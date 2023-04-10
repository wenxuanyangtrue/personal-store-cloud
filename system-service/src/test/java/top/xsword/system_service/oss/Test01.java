package top.xsword.system_service.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsV2Result;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Data:2022/10/9
 * Author:ywx
 * Description:
 */
public class Test01 {

    public static final String END_POINT = "https://oss-cn-hangzhou.aliyuncs.com";
    public static final String ACCESS_KEY_ID = "LTAI5tMptxyWweDazokHqmDN";
    public static final String ACCESS_KEY_SECRET = "MS41rTDEKk5OO4r4yMA757cccguXVR";
    public static final String BUCKET = "personal-store-cloud";

    @Test
    public void list() throws IOException {
        OSS ossClient = new OSSClientBuilder().build(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        /*FileInputStream in = new FileInputStream("C:\\Users\\ywx\\Pictures\\Saved Pictures\\Aurora_Melusine.png");
        String filePath = "image/Aurora_Melusine.png";
        ossClient.putObject(BUCKET, filePath, in);*/
        ListObjectsV2Result listObjectsV2Result = ossClient.listObjectsV2(BUCKET);
        listObjectsV2Result.getObjectSummaries().forEach((summary)->{
            System.out.println(summary.getKey());
        });

        ossClient.shutdown();
    }

}
