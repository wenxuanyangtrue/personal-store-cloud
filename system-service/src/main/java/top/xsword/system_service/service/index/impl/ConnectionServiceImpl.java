package top.xsword.system_service.service.index.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xsword.common_util.oss.OSSUtil;
import top.xsword.model.entity.Connection;
import top.xsword.system_service.mapper.ConnectionMapper;
import top.xsword.system_service.service.index.ConnectionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ywx
 * @since 2023-01-31
 */
@Service
public class ConnectionServiceImpl extends ServiceImpl<ConnectionMapper, Connection> implements ConnectionService {

    @Override
    public void testConnection(Connection connection) {
        OSSUtil.testConnection(connection);
    }

    @Override
    public List<Connection> connectionListByUserId(String userId, Integer storageMode) {
        LambdaQueryWrapper<Connection> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Connection::getUserId, userId).eq(Connection::getStorageMode, storageMode);
        return list(objectLambdaQueryWrapper);
    }

    @Override
    public Boolean removeConnectionByIdAndUserId(String id, String userId, Integer storageMode) {
        LambdaQueryWrapper<Connection> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper
                .eq(Connection::getId, id)
                .eq(Connection::getUserId, userId)
                .eq(Connection::getStorageMode, storageMode);
        return remove(objectLambdaQueryWrapper);
    }
}
