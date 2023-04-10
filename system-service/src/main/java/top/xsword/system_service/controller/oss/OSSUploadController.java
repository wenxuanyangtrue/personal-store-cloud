package top.xsword.system_service.controller.oss;

import org.springframework.web.bind.annotation.*;
import top.xsword.common_util.result.Result;
import top.xsword.model.object_storage.oss.Connection;
import top.xsword.system_service.service.oss.ObjectService;
import top.xsword.system_service.service.oss.PostObjectService;
import top.xsword.system_service.service.oss.impl.OSSObjectServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Data:2022/11/19
 * Author:ywx
 * Description:签名控制
 */
@RestController
@RequestMapping("/oss-upload")
public class OSSUploadController {

    @Resource
    private PostObjectService postObjectService;

    @Resource(type = OSSObjectServiceImpl.class)
    ObjectService objectService;

//    @Resource
//    private FileService fileService;

    /**
     * 返回签名，key为文件的路径，十分钟内有效
     */
    @GetMapping("/{id}/signature")
    public Result<Map<String, String>> signature(HttpServletRequest request, @PathVariable String id,
                                                 @RequestParam String region, @RequestParam String bucketName,
                                                 @RequestParam String key) {
        String token = (String) request.getAttribute("token");
        Connection connectionVo = new Connection(token, region, objectService.getById(id));

        System.out.println(key);
        int minute = 10;
        long expireEndTime = System.currentTimeMillis() + (long) minute * 1000 * 60;
        //过期时间
        Date expiration = new Date(expireEndTime);
        Map<String, String> signature = postObjectService.generateSignature(connectionVo, bucketName, key, expiration);
        return Result.ok(signature);
    }

    /**
     * 上传回调，云存储供应商在接收文件成功后调用
     */
//    @PostMapping("/callback")
//    @ExcludeValidate
//    public Result<Object> callback(HttpServletResponse response, @RequestBody DirFileVo callback) throws JsonProcessingException {
//        callbackByFile(callback);
//
//        Result<Object> ok = Result.ok();
//        byte[] content = new ObjectMapper().writeValueAsBytes(ok);
//        response.setContentLength(content.length);
//        return ok;
//    }
//
//    private void callbackByFile(DirFileVo callback) {
//        String key = callback.getName();
//        String[] split = key.split("/");
//        String name = split[split.length - 1];
//        String suffix = name.substring(name.lastIndexOf('.') + 1);
//        //如果dirId与用户id相同，则为null
//        String dirId = split[split.length - 2].equals(split[0]) ? null : split[split.length - 2];
//
//        File file = new File();
//        file.setFilename(name);
//        file.setSize(callback.getSize());
//        file.setType(callback.getType());
//        file.setSuffix(suffix);
//        file.setDirId(dirId);
//        file.setLocation(1);
//
//        fileService.saveFile(file);
//    }

}
