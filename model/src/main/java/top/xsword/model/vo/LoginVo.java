package top.xsword.model.vo;

import lombok.Data;

/**
 * Data:2022/11/22
 * Author:ywx
 * Description:
 */
@Data
public class LoginVo {
    private String username;
    private String email;
    private String password;
    private String phone;
    private String verifyCode;
    private String uuid;
}
