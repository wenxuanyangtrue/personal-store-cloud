package top.xsword.system_service.service.oss.impl;

import org.springframework.stereotype.Service;
import top.xsword.common_util.oss.OSSUtil;
import top.xsword.model.object_storage.oss.Connection;
import top.xsword.system_service.service.oss.GetObjectService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data:2022/12/1
 * Author:ywx
 * Description:
 */
@Service
public class OSSGetObjectServiceImpl implements GetObjectService {

    @Override
    public String generateUrl(Connection connection, String bucketName, String key, Date expiration) throws UnsupportedEncodingException {
        return OSSUtil.generatePresignedUrl(connection, bucketName, key, expiration);
    }

    @Override
    public List<String> generateUrlList(Connection connection, String bucketName, List<String> keys, Date expiration) {
        List<String> urls = new ArrayList<>(keys.size());
        keys.forEach((key) -> {
            String url = null;
            try {
                url = OSSUtil.generatePresignedUrl(connection, bucketName, key, expiration);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            urls.add(url);
        });
        return urls;
    }
}
