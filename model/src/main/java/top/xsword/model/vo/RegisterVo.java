package top.xsword.model.vo;

import lombok.Data;

/**
 * Author: ywx
 * Create Time: 2022/12/13
 * Description:
 */
@Data
public class RegisterVo {
    private String email;
    private String password;
    private String phone;
    private String verifyCode;
    private String uuid;
}
