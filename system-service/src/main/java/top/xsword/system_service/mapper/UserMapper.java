package top.xsword.system_service.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.xsword.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ywx
 * @since 2022-11-21
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User selectUserByEmail(@Param("email") String email);

}
