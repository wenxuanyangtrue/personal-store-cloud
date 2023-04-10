package top.xsword.system_service.service.oss;

import com.aliyun.oss.model.ListObjectsV2Request;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xsword.model.object_storage.oss.Connection;
import top.xsword.model.object_storage.oss.OSSObjectResponse;

import java.util.List;

/**
 * Author: ywx
 * Create Time: 2023/2/8
 * Description:
 */
public interface ObjectService extends IService<top.xsword.model.entity.Connection> {

    /**
     * 根据条件列出object
     */
    OSSObjectResponse listObjectsV2(Connection connection, ListObjectsV2Request listObjectsV2Request);

    /**
     * 查询object的元数据
     */
    ObjectMetadata objectMetadata(Connection connection, String bucketName, String key);

    /**
     * 删除单个指定的object
     */
    void deleteObject(Connection connection, String bucketName, String key);

    /**
     * 删除多个指定的object
     */
    void deleteObjects(Connection connection, String bucketName, List<String> keys);

    /**
     * 创建单个目录以及该目录下的所有文件以及目录
     */
    void deleteFolder(Connection connection, String bucketName, String prefix);

    /**
     * 删除多个目录以及这些目录下的所有文件以及目录
     */
    void deleteFolders(Connection connection, String bucketName, List<String> keys);

    /**
     * 创建单个目录
     */
    void createFolder(Connection connection, String bucketName, String folder);

    /**
     * 创建多级目录
     */
    void createFolders(Connection connection, String bucketName, List<String> folders);

}
