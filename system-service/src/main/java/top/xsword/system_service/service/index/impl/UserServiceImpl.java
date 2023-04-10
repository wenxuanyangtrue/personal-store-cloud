package top.xsword.system_service.service.index.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import top.xsword.model.entity.User;
import top.xsword.system_service.mapper.UserMapper;
import top.xsword.system_service.service.index.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ywx
 * @since 2022-11-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User emailLogin(String email, String password) {
        LambdaQueryWrapper<User> queryWrapper =
                new LambdaQueryWrapper<User>()
                        .eq(User::getEmail, email)
                        .and(userLambdaQueryWrapper -> userLambdaQueryWrapper.eq(User::getPassword, password));
        User user = baseMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User selectUserByEmail(String email) {
        return baseMapper.selectUserByEmail(email);
    }

    @Override
    public Boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper =
                new LambdaQueryWrapper<User>()
                        .eq(User::getEmail, email);
        return baseMapper.exists(queryWrapper);
    }
}
