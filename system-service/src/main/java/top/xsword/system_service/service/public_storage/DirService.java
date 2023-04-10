package top.xsword.system_service.service.public_storage;

import top.xsword.model.entity.Dir;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ywx
 * @since 2022-11-21
 */
public interface DirService extends IService<Dir> {

    boolean saveDir(Dir dir);

    boolean saveDirs(Collection<Dir> dirs);

    List<Dir> ChildrenList(String userId, String dirId);

    Dir getRootDir(String userId);

    boolean updateDirname(String userId, String dirId, String dirname);
}
