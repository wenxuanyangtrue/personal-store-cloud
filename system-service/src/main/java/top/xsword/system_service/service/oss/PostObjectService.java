package top.xsword.system_service.service.oss;

import top.xsword.model.object_storage.oss.Connection;

import java.util.Date;
import java.util.Map;

/**
 * Data:2022/11/19
 * Author:ywx
 * Description:
 */
public interface PostObjectService {
    /**
     * 生成PostObject对象
     *
     * @param key 上传文件的全名
     * @return PostObject对象
     */
    Map<String, String> generateSignature(Connection connection, String bucketName, String key, Date expiration);
}
