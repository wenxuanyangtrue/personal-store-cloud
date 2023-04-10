package top.xsword.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @TableField(value = "avatar",fill = FieldFill.INSERT)
    private String avatar;

    @TableField(value = "role",fill = FieldFill.INSERT)
    private Integer role;
}
