package top.xsword.system_service.service.oss.impl;

import com.aliyun.oss.model.Bucket;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xsword.common_util.oss.OSSUtil;
import top.xsword.model.object_storage.oss.Connection;
import top.xsword.system_service.mapper.ConnectionMapper;
import top.xsword.system_service.service.oss.BucketService;

import java.util.List;

/**
 * Author: ywx
 * Create Time: 2023/2/3
 * Description:
 */
@Service
public class OSSBucketServiceImpl extends ServiceImpl<ConnectionMapper, top.xsword.model.entity.Connection> implements BucketService {

    @Override
    public List<Bucket> listBuckets(Connection connection) {
        return OSSUtil.listBucket(connection);
    }

}
