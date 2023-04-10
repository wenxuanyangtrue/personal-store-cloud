package top.xsword.system_service.service.public_storage;

import top.xsword.model.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ywx
 * @since 2022-11-21
 */
public interface FileService extends IService<File> {

    boolean saveFile(File file);

    List<File> selectByDirId(String userId, String dirId);

    File selectFileById(String fileId);

    File selectOneByDirId(String userId, String fileId);

    boolean updateFilename(String userId, String fileId, String filename);
}
