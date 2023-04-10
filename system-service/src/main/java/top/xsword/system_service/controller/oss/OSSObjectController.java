package top.xsword.system_service.controller.oss;

import com.aliyun.oss.model.ListObjectsV2Request;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.web.bind.annotation.*;
import top.xsword.common_util.result.Result;
import top.xsword.model.object_storage.oss.Connection;
import top.xsword.model.object_storage.oss.OSSObjectResponse;
import top.xsword.system_service.service.oss.ObjectService;
import top.xsword.system_service.service.oss.impl.OSSObjectServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Author: ywx
 * Create Time: 2023/2/8
 * Description:
 */
@RestController
@RequestMapping("/oss-object")
public class OSSObjectController {

    @Resource(type = OSSObjectServiceImpl.class)
    ObjectService objectService;

    /**
     * 查询object列表
     */
    @PostMapping("/{id}")
    public Result listObjectsV2(HttpServletRequest request, @PathVariable String id,
                                @RequestParam String region,
                                @RequestParam String bucketName,
                                @RequestBody ListObjectsV2Request listObjectsV2Request) {
        String token = (String) request.getAttribute("token");
        Connection connectionVo = new Connection(token, region, objectService.getById(id));
        OSSObjectResponse ossObjectResponse = objectService.listObjectsV2(connectionVo, listObjectsV2Request);
        return Result.ok(ossObjectResponse);
    }

    /**
     * 查询object元数据
     */
    @GetMapping("/{id}/metadata")
    public Result objectMetadata(HttpServletRequest request, @PathVariable String id,
                                 @RequestParam String region, @RequestParam String bucketName,
                                 @RequestParam String key) {
        String token = (String) request.getAttribute("token");
        Connection connectionVo = new Connection(token, region, objectService.getById(id));

        ObjectMetadata objectMetadata = objectService.objectMetadata(connectionVo, bucketName, key);
        HashMap<Object, Object> objectMetadataMap = new HashMap<>();
        objectMetadataMap.put("userMetadata", objectMetadata.getUserMetadata());
        objectMetadataMap.put("rawMetadata", objectMetadata.getRawMetadata());
        return Result.ok(objectMetadataMap);
    }

    /**
     * 删除指定的object
     */
    @DeleteMapping("/{id}")
    public Result deleteObjects(HttpServletRequest request, @PathVariable String id,
                                @RequestParam String region, @RequestParam String bucketName,
                                @RequestBody List<String> keys) {
        String token = (String) request.getAttribute("token");
        Connection connectionVo = new Connection(token, region, objectService.getById(id));
        objectService.deleteObjects(connectionVo, bucketName, keys);
        return Result.ok();
    }

    /**
     * 删除指定的目录
     */
    @DeleteMapping("/{id}/folder")
    public Result deleteFolders(HttpServletRequest request, @PathVariable String id,
                                @RequestParam String region, @RequestParam String bucketName,
                                @RequestBody List<String> keys) {
        String token = (String) request.getAttribute("token");
        Connection connectionVo = new Connection(token, region, objectService.getById(id));
        objectService.deleteFolders(connectionVo, bucketName, keys);
        return Result.ok();
    }

    /**
     * 创建目录
     */
    @PutMapping("/{id}/folder")
    public Result createFolders(HttpServletRequest request, @PathVariable String id,
                                @RequestParam String region, @RequestParam String bucketName,
                                @RequestBody List<String> folders) {
        String token = (String) request.getAttribute("token");
        Connection connectionVo = new Connection(token, region, objectService.getById(id));
        objectService.createFolders(connectionVo, bucketName, folders);
        return Result.ok();
    }
}
