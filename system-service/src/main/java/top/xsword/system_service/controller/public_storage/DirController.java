package top.xsword.system_service.controller.public_storage;


import org.springframework.web.bind.annotation.*;

import top.xsword.common_util.result.Result;
import top.xsword.model.base.BaseEntity;
import top.xsword.model.entity.Dir;
import top.xsword.model.entity.User;
import top.xsword.model.vo.DirVo;
import top.xsword.system_service.service.public_storage.DirFileService;
import top.xsword.system_service.service.public_storage.DirService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 目录操作
 * </p>
 *
 * @author ywx
 * @since 2022-11-21
 */
@RestController
@RequestMapping("/dir")
public class DirController {

    @Resource
    DirService dirService;

    @Resource
    DirFileService dirFileService;

    @PostMapping
    public Result<Object> saveDir(HttpServletRequest request,
                                  @RequestParam(required = false) String currentDirId,
                                  @RequestParam String dirs) {
        boolean success = false;
        User user = (User) request.getAttribute("user");

        List<Dir> dirList = Arrays.stream(dirs.split("/"))
                .map((dirname) -> {
                    Dir dir = new Dir();
                    dir.setDirname(dirname);
                    dir.setUserId(user.getId());
                    return dir;
                }).collect(Collectors.toList());

        if (dirList.size() > 0) {
            dirList.get(0).setParentId(currentDirId);
            success = dirService.saveDirs(dirList);
        }
        return success ? Result.ok() : Result.fail();
    }

    @DeleteMapping
    public Result<Object> removeDir(HttpServletRequest request, @RequestBody List<String> dirIds) {
        User user = (User) request.getAttribute("user");

        //将本地数据库的目录删除
        boolean success = dirFileService.removeDirAndFile(user.getId(), dirIds);
        return success ? Result.ok() : Result.fail();
    }

    @PutMapping("/updateDirname")
    public Result updateDirname(HttpServletRequest request,
                                @RequestParam String dirId, @RequestParam String dirname) {
        User user = (User) request.getAttribute("user");
        return dirService.updateDirname(user.getId(), dirId, dirname) ? Result.ok() : Result.fail();
    }
}
