package top.xsword.model.object_storage.oss;

import lombok.Data;

/**
 * Author: ywx
 * Create Time: 2023/2/8
 * Description:
 */
@Data
public class Connection {
    private String userToken;
    private String endpoint;
    private top.xsword.model.entity.Connection connection;

    public Connection(String userToken, String region, top.xsword.model.entity.Connection connection) {
        this.userToken = userToken;
        this.endpoint = region + ".aliyuncs.com";
        this.connection = connection;
    }

    public Connection(String userToken, top.xsword.model.entity.Connection connection) {
        this.userToken = userToken;
        this.endpoint = connection.getEndpoint();
        this.connection = connection;
    }
}
