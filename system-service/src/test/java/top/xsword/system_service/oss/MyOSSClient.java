package top.xsword.system_service.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;

import java.io.Serializable;

/**
 * Author: ywx
 * Create Time: 2023/2/8
 * Description:
 */
public class MyOSSClient extends OSSClient implements Serializable {

    public MyOSSClient(String endpoint, CredentialsProvider credsProvider, ClientConfiguration config) {
        super(endpoint, credsProvider, config);
    }
}
