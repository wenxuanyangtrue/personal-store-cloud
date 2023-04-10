package top.xsword.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import top.xsword.model.base.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author ywx
 * @since 2022-11-21
 */
@Data
@ToString(callSuper = true)
@TableName("file")
public class File extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("filename")
    private String filename;

    @TableField("dir_id")
    private String dirId;

    @TableField("size")
    private Long size;

    @TableField("suffix")
    private String suffix;

    @TableField("type")
    private String type;

}
