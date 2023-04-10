package top.xsword.system_service.mapper;

import org.springframework.stereotype.Repository;
import top.xsword.model.entity.Dir;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
public interface DirMapper extends BaseMapper<Dir> {

    List<Dir> selectChildren(String userId, String dirId);
}
