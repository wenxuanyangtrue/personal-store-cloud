package top.xsword.system_service.service.oss.impl;

import com.aliyun.oss.model.ListObjectsV2Request;
import com.aliyun.oss.model.ListObjectsV2Result;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.xsword.common_util.file.FileUtil;
import top.xsword.common_util.oss.OSSUtil;
import top.xsword.model.object_storage.oss.Connection;
import top.xsword.model.object_storage.oss.OSSObjectResponse;
import top.xsword.system_service.mapper.ConnectionMapper;
import top.xsword.system_service.service.oss.ObjectService;

import java.util.List;

/**
 * Author: ywx
 * Create Time: 2023/2/8
 * Description:
 */
@Service
public class OSSObjectServiceImpl extends ServiceImpl<ConnectionMapper, top.xsword.model.entity.Connection> implements ObjectService {

    @Override
    public OSSObjectResponse listObjectsV2(Connection connection, ListObjectsV2Request listObjectsV2Request) {
        ListObjectsV2Result listObjectsV2Result = OSSUtil.listObjectsV2(connection, listObjectsV2Request);
        OSSObjectResponse ossObjectResponse = new OSSObjectResponse(listObjectsV2Result);

        ossObjectResponse.getObjectSummaries().forEach((objectSummary) -> {
            OSSObjectResponse.OSSObjectSummaryWrap ossObjectSummaryWrap = (OSSObjectResponse.OSSObjectSummaryWrap) objectSummary;
            //将文件的size格式化
            if (!ossObjectSummaryWrap.getDir()) {
                ossObjectSummaryWrap.setFormattedSize(FileUtil.formattedSize(objectSummary.getSize()));
            }

            //将文件和目录的key值前缀去掉
            if (StringUtils.hasLength(ossObjectResponse.getPrefix())) {
                ossObjectSummaryWrap.setKey(ossObjectSummaryWrap.getKey().replaceFirst(ossObjectResponse.getPrefix(), ""));
            }

        });
        return ossObjectResponse;
    }

    @Override
    public ObjectMetadata objectMetadata(Connection connection, String bucketName, String key) {
        return OSSUtil.objectMetadata(connection, bucketName, key);
    }

    @Override
    public void deleteObject(Connection connection, String bucketName, String key) {
        OSSUtil.deleteObject(connection, bucketName, key);
    }

    @Override
    public void deleteObjects(Connection connection, String bucketName, List<String> keys) {
        OSSUtil.deleteObjects(connection, bucketName, keys);
    }

    @Override
    public void deleteFolder(Connection connection, String bucketName, String prefix) {
        OSSUtil.deleteFolder(connection, bucketName, prefix);
    }

    @Override
    public void deleteFolders(Connection connection, String bucketName, List<String> keys) {
        keys.forEach((key) -> deleteFolder(connection, bucketName, key));
    }

    @Override
    public void createFolder(Connection connection, String bucketName, String folder) {
        OSSUtil.createFolder(connection, bucketName, folder);
    }

    @Override
    public void createFolders(Connection connection, String bucketName, List<String> folders) {
        folders.forEach((folder) -> {
            folder = folder.endsWith("/") ? folder : folder + "/";
            createFolder(connection, bucketName, folder);
        });
    }


}
