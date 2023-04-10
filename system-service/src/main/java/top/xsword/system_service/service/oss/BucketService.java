package top.xsword.system_service.service.oss;

import com.aliyun.oss.model.Bucket;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xsword.model.object_storage.oss.Connection;

import java.util.List;

/**
 * Author: ywx
 * Create Time: 2023/2/3
 * Description:
 */
public interface BucketService extends IService<top.xsword.model.entity.Connection> {

    List<Bucket> listBuckets(Connection connection);

}
