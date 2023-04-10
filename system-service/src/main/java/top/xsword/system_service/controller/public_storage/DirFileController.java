package top.xsword.system_service.controller.public_storage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import top.xsword.common_util.result.Result;
import top.xsword.model.entity.User;
import top.xsword.model.vo.DirFileVo;
import top.xsword.model.vo.FileQueryVo;
import top.xsword.system_service.service.public_storage.DirFileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Data:2022/11/19
 * Author:ywx
 * Description: 显示当前文件列表
 */
@RestController
@RequestMapping("/dir-file")
public class DirFileController {

    @Resource
    DirFileService dirFileService;

    /**
     * 进入用户根目录
     */
    @GetMapping("/{current}/{size}")
    public Result<Page<DirFileVo>> dirFileList(HttpServletRequest request,
                                               @PathVariable Integer current,
                                               @PathVariable Integer size) {
        User user = (User) request.getAttribute("user");
        Page<DirFileVo> dirFileVoPage = dirFileService.dirFileListPage(current, size, user.getId(), null);
        return Result.ok(dirFileVoPage);
    }

    /**
     * 进入指定的目录
     */
    @GetMapping("/{current}/{size}/{dirId}")
    public Result<Page<DirFileVo>> dirFileList(HttpServletRequest request, @PathVariable Integer current,
                                               @PathVariable Integer size, @PathVariable String dirId) {
        User user = (User) request.getAttribute("user");
        //获取指定目录下的所有内容并分页
        Page<DirFileVo> dirFileVoPage = dirFileService.dirFileListPage(current, size, user.getId(), dirId);
        return Result.ok(dirFileVoPage);
    }

    /**
     * 返回用户查询的文件
     */
    @PostMapping("/like/{current}/{size}")
    public Result<Page<DirFileVo>> fileList(HttpServletRequest request, @PathVariable Integer current,
                                               @PathVariable Integer size, @RequestBody FileQueryVo fileQuery) {
        User user = (User) request.getAttribute("user");
        fileQuery.setFilename(fileQuery.getFilename().trim());
        //模糊查询
        Page<DirFileVo> dirFileVoPage = dirFileService.dirFileListPageLike(current, size, user.getId(), fileQuery);
        return Result.ok(dirFileVoPage);
    }

}
