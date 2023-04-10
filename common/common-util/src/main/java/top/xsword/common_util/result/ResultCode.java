package top.xsword.common_util.result;

/**
 * Data:2022/11/8
 * Author:ywx
 * Description:
 */

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    NO_SUCH_USER(202, "用户不存在或密码错误"),
    VERIFY_CODE_ERROR(204, "验证码错误或已过期"),
    EMAIL_EXIST(205, "邮箱已被注册"),
    EMAIL_REGISTER_EXPIRE(206, "邮箱激活超时"),
    OSS_EXCEPTION(250, null),
    FILE_NOT_FOUND(300, "没有找到该文件"),
    FILE_TOKEN_EXPIRATION(301, "文件token过期，请尝试重新获取"),
    NONE_LOGIN(400, "请先登录"),
    USER_TOKEN_EXPIRATION(401, "用户token过期，请重新登录"),
    SIGNATURE_NOT_TRUSTED(402, "token无效，请重新登录"),
    TOKEN_PARSE_FAIL(403, "token解析失败，请尝试重新获取"),
    OTHER(666, "不知道什么鬼异常"),
    ;

    private final Integer code;

    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
