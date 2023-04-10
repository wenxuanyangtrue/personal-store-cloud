package top.xsword.system_service.controller.public_storage;

import org.springframework.web.bind.annotation.*;
import top.xsword.common_util.result.Result;
import top.xsword.model.base.BaseEntity;
import top.xsword.model.entity.File;
import top.xsword.model.entity.User;
import top.xsword.model.vo.FileVo;
import top.xsword.system_service.service.public_storage.FileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.CharArrayReader;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Data:2022/11/29
 * Author:ywx
 * Description:
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    FileService fileService;

    @DeleteMapping
    public Result removeFile(HttpServletRequest request, @RequestBody FileVo fileVo) {
        User user = (User) request.getAttribute("user");
        //获取需要删除的文件集合
        List<File> files = fileVo.getFiles();
        //提取每个文件的id
        List<String> fileIds = files.stream().map(BaseEntity::getId).collect(Collectors.toList());

        //根据文件id批量删除文件
        boolean success = fileService.removeBatchByIds(fileIds);
        return success ? Result.ok() : Result.fail();
    }

    @PutMapping("/updateFilename")
    public Result updateFilename(HttpServletRequest request,
                                 @RequestParam String fileId, @RequestParam String filename) {
        User user = (User) request.getAttribute("user");
        return fileService.updateFilename(user.getId(), fileId, filename) ? Result.ok() : Result.fail();
    }
}
