package top.xsword.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import top.xsword.model.base.BaseEntity;

/**
 * <p>
 *
 * </p>
 *
 * @author ywx
 * @since 2023-01-31
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("connection")
public class Connection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    private String name;

    @TableField("access_key")
    private String accessKey;

    @TableField("access_key_secret")
    private String accessKeySecret;

    @TableField("endpoint")
    private String endpoint;

    @TableField("storage_mode")
    private Integer storageMode;

    @TableField("user_id")
    private String userId;

}
