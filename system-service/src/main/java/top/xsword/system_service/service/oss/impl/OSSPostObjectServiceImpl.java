package top.xsword.system_service.service.oss.impl;

import org.springframework.stereotype.Service;
import top.xsword.common_util.oss.UploadSignatureUtil;
import top.xsword.model.object_storage.oss.Connection;
import top.xsword.system_service.service.oss.PostObjectService;

import java.util.Date;
import java.util.Map;

/**
 * Data:2022/11/20
 * Author:ywx
 * Description:
 */
@Service
public class OSSPostObjectServiceImpl implements PostObjectService {

    public Map<String, String> generateSignature(Connection connection, String bucketName, String key, Date expiration) {
        return new UploadSignatureUtil(connection, bucketName, key, 5000, expiration)
                .withContentType(key).build();
    }
}
