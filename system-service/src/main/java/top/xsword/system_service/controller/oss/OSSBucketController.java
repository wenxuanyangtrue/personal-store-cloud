package top.xsword.system_service.controller.oss;

import com.aliyun.oss.model.Bucket;
import org.springframework.web.bind.annotation.*;
import top.xsword.common_util.result.Result;
import top.xsword.model.object_storage.oss.Connection;
import top.xsword.system_service.service.oss.BucketService;
import top.xsword.system_service.service.oss.impl.OSSBucketServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Author: ywx
 * Create Time: 2023/2/4
 * Description:
 */
@RestController
@RequestMapping("/oss-bucket")
public class OSSBucketController {

    @Resource(type = OSSBucketServiceImpl.class)
    BucketService bucketService;

    /**
     * 查询bucket列表
     *
     */
    @GetMapping("/{id}")
    public Result bucketList(HttpServletRequest request, @PathVariable String id) {

        String token = (String) request.getAttribute("token");
        top.xsword.model.entity.Connection connection = bucketService.getById(id);
        Connection connectionVo = new Connection(token, connection);
        System.out.println(connectionVo);
        List<Bucket> buckets = bucketService.listBuckets(connectionVo);
        return Result.ok(buckets);
    }

}
