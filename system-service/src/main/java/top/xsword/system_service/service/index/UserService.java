package top.xsword.system_service.service.index;

import org.apache.ibatis.annotations.Param;
import top.xsword.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ywx
 * @since 2022-11-21
 */
public interface UserService extends IService<User> {

    User emailLogin(String email, String password);

    User selectUserByEmail(@Param("email") String email);

    Boolean emailExist(String email);



}
