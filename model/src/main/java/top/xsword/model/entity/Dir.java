package top.xsword.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import top.xsword.model.base.BaseEntity;

import java.util.List;

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
@TableName("dir")
public class Dir extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("dirname")
    private String dirname;

    @TableField("parent_id")
    private String parentId;

    @TableField("user_id")
    private String userId;

//    @TableField(exist = false)
//    private List<File> files;

}
