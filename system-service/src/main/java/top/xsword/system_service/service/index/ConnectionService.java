package top.xsword.system_service.service.index;

import com.baomidou.mybatisplus.extension.service.IService;
import top.xsword.model.entity.Connection;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ywx
 * @since 2023-01-31
 */
public interface ConnectionService extends IService<Connection> {

    void testConnection(Connection connection);

    List<Connection> connectionListByUserId(String userId, Integer storageMode);

    Boolean removeConnectionByIdAndUserId(String id, String userId, Integer storageMode);

}
