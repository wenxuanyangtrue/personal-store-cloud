package top.xsword.system_service.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.xsword.model.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.xsword.model.vo.FileQueryVo;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ywx
 * @since 2022-11-21
 */
@Repository
public interface FileMapper extends BaseMapper<File> {

    List<File> selectByDirId(@Param("userId") String userId, @Param("dirId") String dirId);

    List<File> selectByUserIdLike(Page<File> pageParam, @Param("userId") String userId, @Param("fileQuery") FileQueryVo fileQuery);

    File selectOneByDirId(@Param("userId") String userId, @Param("fileId") String fileId);
}
