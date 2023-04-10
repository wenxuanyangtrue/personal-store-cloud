package top.xsword.system_service.controller.public_storage;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xsword.common_service.properties.UploadProperties;
import top.xsword.common_util.file.FileUtil;
import top.xsword.common_util.oss.MyMimeMappings;
import top.xsword.common_util.result.Result;
import top.xsword.model.entity.Dir;
import top.xsword.model.entity.File;
import top.xsword.model.entity.User;
import top.xsword.system_service.service.public_storage.DirService;
import top.xsword.system_service.service.public_storage.FileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: ywx
 * Create Time: 2023/2/15
 * Description:
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Resource
    FileService fileService;

    @Resource
    DirService dirService;

    @Resource
    UploadProperties uploadProperties;

    @PostMapping
    public Result upload(HttpServletRequest request,
                         @RequestParam(required = false) String dirId,
                         @RequestParam Long size,
                         MultipartFile multipartFile) {
        User user = (User) request.getAttribute("user");

        String suffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        String contentType = MimeMappings.DEFAULT.get(suffix);
        if (!StringUtils.hasLength(contentType)) {
            contentType = MyMimeMappings.mimeMappings.get(suffix);
        }
        //创建文件上传的目录，如果已存在则不做任何操作
        FileUtil.makeDirs(uploadProperties.getLocation(), user.getId());

        System.out.println(user.getId());
        File file = new File();
        //如果前端没传目录id，那么需要找出这个用户的根目录，将根目录id设置为这个文件的目录id
        if (dirId == null) {
            Dir rootDir = dirService.getRootDir(user.getId());
            file.setDirId(rootDir.getId());
        }else{
            file.setDirId(dirId);
        }
        file.setFilename(multipartFile.getOriginalFilename());
        file.setSize(size);
        file.setType(contentType);
        file.setSuffix(suffix);

        //将文件保存至数据库中
        if (fileService.saveFile(file)) {
            String filename = file.getId();
            if (suffix != null) {
                filename += "." + suffix;
            }
            //获取文件存放的全路径
            Path path = Paths.get(uploadProperties.getLocation(), user.getId(), filename);
            System.out.println(path);
            try {
                //将文件写入本地
                Files.write(path, multipartFile.getBytes());
            } catch (IOException e) {
                fileService.removeById(file.getId());
                return Result.fail();
            }
            return Result.ok();
        } else {
            return Result.fail();
        }
    }
}
