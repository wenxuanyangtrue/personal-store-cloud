package top.xsword.system_service.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * Author: ywx
 * Create Time: 2023/1/30
 * Description:
 */
@SpringBootTest
public class Test04 {

    @Resource
    OSSClient ossClient;

    @Test
    public void t2(){
        List<Bucket> buckets = ossClient.listBuckets();
        buckets.forEach((obj)->{
            System.out.println(obj);
            System.out.println(obj.getRegion());
            System.out.println(obj.getHnsStatus());
            System.out.println(obj.getExtranetEndpoint());
            System.out.println(obj.getIntranetEndpoint());
            System.out.println(obj.getLocation());
            System.out.println(obj);
        });
    }

    @Test
    public void t1(){
        ObjectListing objectListing = ossClient.listObjects("personal-store-cloud","asdas/");
        List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        objectSummaries.forEach((obj)->{
            System.out.println(obj.getKey());
            System.out.println(obj.getETag());
            System.out.println(obj.getOwner());
            System.out.println(obj.getLastModified());
            System.out.println(obj.getSize());
            System.out.println(obj.getType());
            System.out.println(obj.getStorageClass());
            System.out.println();
        });
    }
}
