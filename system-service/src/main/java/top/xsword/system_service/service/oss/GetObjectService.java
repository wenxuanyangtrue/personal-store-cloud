package top.xsword.system_service.service.oss;

import top.xsword.model.object_storage.oss.Connection;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Data:2022/12/1
 * Author:ywx
 * Description:
 */
public interface GetObjectService {
    /**
     * 生成get访问的url
     * @param key 文件的全名
     * @return 访问文件的url
     */
    String generateUrl(Connection connection, String bucketName, String key, Date expiration) throws UnsupportedEncodingException;

    /**
     * 生成get访问的url
     * @param keys 文件key list
     * @return 访问文件的url
     */
    List<String> generateUrlList(Connection connection, String bucketName, List<String> keys, Date expiration);
}
