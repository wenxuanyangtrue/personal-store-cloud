package top.xsword.system_service.service.public_storage.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import top.xsword.model.entity.Dir;
import top.xsword.model.entity.File;
import top.xsword.system_service.mapper.DirMapper;
import top.xsword.system_service.mapper.FileMapper;
import top.xsword.system_service.service.public_storage.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ywx
 * @since 2022-11-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Resource
    private DirMapper dirMapper;

    /**
     * 保存文件
     *
     * @param file
     * @return
     */
    public boolean saveFile(File file) {
        return save(file);
    }

    @Override
    public List<File> selectByDirId(String userId, String dirId) {
        return baseMapper.selectByDirId(userId, dirId);
    }

    @Override
    public File selectFileById(String fileId) {
        return baseMapper.selectById(fileId);
    }

    @Override
    public File selectOneByDirId(String userId, String fileId) {
        return baseMapper.selectOneByDirId(userId, fileId);
    }

    @Override
    public boolean updateFilename(String userId, String fileId, String filename) {
        boolean flag = false;
        File file = baseMapper.selectById(fileId);
        //file不为空才检查该file是否为该user的合法文件
        if (file != null) {
            Dir dir = dirMapper.selectOne(new LambdaQueryWrapper<Dir>()
                    .eq(Dir::getUserId, userId)
                    .eq(Dir::getId, file.getDirId()));
            //如果dir不为空再改文件名
            if (dir != null) {
                file.setFilename(filename);
                //修改文件名
                flag = baseMapper.updateById(file) > 0;
            }
        }
        return flag;
    }


}
