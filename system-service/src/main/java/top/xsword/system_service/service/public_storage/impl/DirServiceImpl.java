package top.xsword.system_service.service.public_storage.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.xsword.model.entity.Dir;
import top.xsword.system_service.mapper.DirMapper;
import top.xsword.system_service.service.public_storage.DirService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
public class DirServiceImpl extends ServiceImpl<DirMapper, Dir> implements DirService {

    /**
     * 添加多级目录
     */
    @Override
    public boolean saveDirs(Collection<Dir> dirs) {
        try {
            dirs.stream().reduce(null, (pre, current) -> {
                if (pre != null) {
                    current.setParentId(pre.getId());
                }
                if (!StringUtils.hasLength(current.getDirname())) {//如果没有名字为空则跳过不保存，返回上一次的结果
                    return pre;
                }
                saveDir(current);
                return current;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取该用户的根目录
     */
    public Dir getRootDir(String userId) {
        LambdaQueryWrapper<Dir> qw = new LambdaQueryWrapper<>();
        qw.eq(Dir::getDirname, "/").eq(Dir::getUserId, userId);
        return getOne(qw);
    }

    /**
     * 更新目录名
     */
    @Override
    public boolean updateDirname(String userId, String dirId, String dirname) {
        if (dirname == null || dirname.length() == 0) { //目录名简单校验
            return false;
        }
        Dir dir = baseMapper.selectById(dirId);
        if (dir == null) { //为空说明目录不存在
            return false;
        } else if (!dir.getUserId().equals(userId)) { //目录的userId与形参userId不一样则不予更新
            return false;
        } else {
            dir.setDirname(dirname);
            //更新目录名
            return baseMapper.updateById(dir) > 0;
        }
    }

    /**
     * 保存目录，如果有父级目录则更新目录关系表
     */
    public boolean saveDir(Dir dir) {
        if (dir.getParentId() == null) {
            dir.setParentId(getRootDir(dir.getUserId()).getId());
        }
        return save(dir);
    }

    /**
     * 查找该目录的子目录
     */
    @Override
    public List<Dir> ChildrenList(String userId, String dirId) {
        return baseMapper.selectChildren(userId, dirId);
    }

}
