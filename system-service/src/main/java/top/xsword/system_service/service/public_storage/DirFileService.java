package top.xsword.system_service.service.public_storage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.xsword.model.vo.DirFileVo;
import top.xsword.model.vo.FileQueryVo;

import java.util.Collection;
import java.util.List;

/**
 * Data:2022/11/23
 * Author:ywx
 * Description:提供文件列表的服务
 */
public interface DirFileService {

    List<DirFileVo> dirFileList(String userId, String dirId);

    Page<DirFileVo> dirFileListPageLike(Integer current, Integer size, String userId, FileQueryVo fileQuery);

    Page<DirFileVo> dirFileListPage(Integer current, Integer size, String userId, String dirId);

    boolean removeDirAndFile(String userId, Collection<String> dirIds);

}
