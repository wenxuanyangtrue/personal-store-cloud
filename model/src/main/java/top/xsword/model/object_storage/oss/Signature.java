package top.xsword.model.object_storage.oss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data:2022/11/23
 * Author:ywx
 * Description:返回前端的签名以及上传路径
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Signature {
    private String ossAccessKeyId;
    private String policy;
    private String signature;
    private String key;
    private String callback;
    private String uploadUrl;
    private String xOSSContentType;
}
