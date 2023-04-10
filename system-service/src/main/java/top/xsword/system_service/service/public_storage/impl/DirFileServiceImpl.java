package top.xsword.system_service.service.public_storage.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xsword.common_util.file.FileUtil;
import top.xsword.model.entity.Dir;
import top.xsword.model.entity.File;
import top.xsword.model.vo.DirFileVo;
import top.xsword.model.vo.FileQueryVo;
import top.xsword.system_service.mapper.DirMapper;
import top.xsword.system_service.mapper.FileMapper;
import top.xsword.system_service.service.public_storage.DirFileService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data:2022/11/23
 * Author:ywx
 * Description:提供文件和目录共同的操作
 */
@Service
public class DirFileServiceImpl implements DirFileService {

    @Resource
    FileMapper fileMapper;

    @Resource
    DirMapper dirMapper;

    /**
     * 分页
     */
    @Override
    public Page<DirFileVo> dirFileListPage(Integer current, Integer size, String userId, String dirId) {
        List<DirFileVo> dirFileVoList = dirFileList(userId, dirId);
        return page(current, size, dirFileVoList);
    }

    /**
     * 分页模糊查询
     */
    @Override
    public Page<DirFileVo> dirFileListPageLike(Integer current, Integer size, String userId, FileQueryVo fileQuery) {
        Page<File> p = Page.of(current, size);
        //根据fileQuery来模糊查询
        List<File> files = fileMapper.selectByUserIdLike(p, userId, fileQuery);
        List<DirFileVo> dirFileVos = new ArrayList<>(files.size());
        //将File对象转为DirFileVo对象
        files.forEach((file) -> {
            DirFileVo dirFileVo = new DirFileVo();
            dirFileVo.setDir(false);
            dirFileVo.setName(file.getFilename());
            dirFileVo.setParentId(file.getDirId());
            dirFileVo.setId(file.getId());
            dirFileVo.setSize(file.getSize());
            dirFileVo.setFormattedSize(FileUtil.formattedSize(file.getSize()));
            dirFileVo.setSuffix(file.getSuffix());
            dirFileVo.setType(file.getType());
            dirFileVo.setUpdateTime(file.getUpdateTime());
            dirFileVos.add(dirFileVo);
        });
        //将结果分页
        return page(current, size, dirFileVos);
    }

    public Page<DirFileVo> page(Integer current, Integer size, List<DirFileVo> dirFileVoList) {
        Page<DirFileVo> page = Page.of(current, size, dirFileVoList.size());
        if (dirFileVoList.size() != 0) {
            dirFileVoList = dirFileVoList.stream()
                    //跳过之前的记录，当current大于0时才减一，否则直接为0
                    .skip((long) (current >= 1 ? current - 1 : 0) * size)
                    .limit(size) //将流限制为页面最大显示数
                    .collect(Collectors.toList());
        }
        page.setRecords(dirFileVoList); //保存结果
        return page;
    }

    /**
     * 递归删除目录
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeDirAndFile(String userId, Collection<String> dirIds) {
        try {
            for (String dirId : dirIds) {
                //查询该用户当前目录下的所有目录与文件
                List<DirFileVo> dirFileVos = dirFileList(userId, dirId);
                //过滤出目录id
                List<String> dirIdList = dirFileVos
                        .stream()
                        .filter(DirFileVo::getDir)
                        .map(DirFileVo::getId)
                        .collect(Collectors.toList());
                //目录不为空则递归遍历
                if (!dirIdList.isEmpty()) {
                    removeDirAndFile(userId, dirIdList);
                }

                //过滤出文件id
                List<String> fileIdList = dirFileVos
                        .stream()
                        .filter((file) -> !file.getDir())
                        .map(DirFileVo::getId)
                        .collect(Collectors.toList());
                //文件不为空则删除
                if (!fileIdList.isEmpty())
                    fileMapper.deleteBatchIds(fileIdList);
            }
            //方法结束删除目录
            dirMapper.deleteBatchIds(dirIds);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据用户id和目录id显示文件与目录
     */
    @Override
    public List<DirFileVo> dirFileList(String userId, String dirId) {
        //放目录和文件实例的集合
        List<DirFileVo> dirFileVos = new ArrayList<>();
        //加载目录
        loadDir(dirFileVos, userId, dirId);
        //加载文件
        loadFile(dirFileVos, userId, dirId);
        return dirFileVos;
    }

    /**
     * 查询目录中的文件并添加到返回的vo中
     */
    private void loadFile(List<DirFileVo> dirFileVos, String userId, String dirId) {
        List<File> files = fileMapper.selectByDirId(userId, dirId);
        files.forEach((file) -> {
            DirFileVo dirFileVo = new DirFileVo();
            dirFileVo.setDir(false);
            dirFileVo.setName(file.getFilename());
            dirFileVo.setParentId(dirId);
            dirFileVo.setId(file.getId());
            dirFileVo.setSize(file.getSize());
            dirFileVo.setFormattedSize(FileUtil.formattedSize(file.getSize()));
            dirFileVo.setSuffix(file.getSuffix());
            dirFileVo.setType(file.getType());
            dirFileVo.setUpdateTime(file.getUpdateTime());
            dirFileVos.add(dirFileVo);
        });
    }

    /**
     * 查询子目录并添加到返回的vo中
     */
    private void loadDir(List<DirFileVo> dirFileVos, String userId, String dirId) {
        List<Dir> dirs = dirMapper.selectChildren(userId, dirId);
        dirs.forEach((dir) -> {
            if (!dir.getDirname().equals("/")) {
                DirFileVo dirFileVo = new DirFileVo();
                dirFileVo.setDir(true);
                dirFileVo.setName(dir.getDirname());
                dirFileVo.setParentId(dirId);
                dirFileVo.setId(dir.getId());
                dirFileVos.add(dirFileVo);
            }
        });
    }
}
