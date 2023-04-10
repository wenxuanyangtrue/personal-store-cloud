package top.xsword.system_service.controller.oss;

import org.springframework.web.bind.annotation.*;
import top.xsword.common_util.result.Result;
import top.xsword.model.object_storage.oss.Connection;
import top.xsword.system_service.service.oss.GetObjectService;
import top.xsword.system_service.service.oss.ObjectService;
import top.xsword.system_service.service.oss.impl.OSSObjectServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Data:2022/12/2
 * Author:ywx
 * Description:
 */
@RestController
@RequestMapping("/oss-generate-url")
public class OSSDownloadController {

    @Resource(type = OSSObjectServiceImpl.class)
    ObjectService objectService;

    @Resource
    GetObjectService getObjectService;

    @GetMapping("/{id}")
    public Result<String> generateUrl(HttpServletRequest request, @PathVariable String id,
                                          @RequestParam String region, @RequestParam String bucketName,
                                          @RequestParam String key) throws UnsupportedEncodingException {
        String token = (String) request.getAttribute("token");
        Connection connectionVo = new Connection(token, region, objectService.getById(id));
        int minute = 10;
        Date expiration = new Date(new Date().getTime() + minute * 1000 * 60);
        String url = getObjectService.generateUrl(connectionVo, bucketName, key, expiration);
        return Result.ok(url);
    }

    @PostMapping("/{id}")
    public Result<List<String>> generateUrlList(HttpServletRequest request, @PathVariable String id,
                                          @RequestParam String region, @RequestParam String bucketName,
                                          @RequestBody List<String> keys) {
        String token = (String) request.getAttribute("token");
        Connection connectionVo = new Connection(token, region, objectService.getById(id));
        int minute = 10;
        Date expiration = new Date(new Date().getTime() + minute * 1000 * 60);
        List<String> urls = getObjectService.generateUrlList(connectionVo, bucketName, keys, expiration);
        return Result.ok(urls);
    }


}

