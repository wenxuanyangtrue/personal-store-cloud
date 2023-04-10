package top.xsword.common_service.filter;

import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.InvalidCredentialsException;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.util.NestedServletException;
import top.xsword.common_util.exception.*;
import top.xsword.common_util.result.Result;
import top.xsword.common_util.result.ResultCode;

import javax.servlet.*;
import java.io.IOException;

/**
 * Data:2022/11/24
 * Author:ywx
 * Description:
 */
public class ExceptionHandlerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (SignatureException e) {
            responseResult(response, Result.build(null, ResultCode.SIGNATURE_NOT_TRUSTED));
        } catch (NestedServletException e) {
            handleNestedException(e, response);//处理AOP切面抛出的嵌套异常
        } catch (Exception e) {
            e.printStackTrace();
            responseResult(response, Result.build(null, ResultCode.OTHER));
        }
    }

    /**
     * 处理嵌套异常
     *
     * @param e
     * @param response
     * @throws IOException
     */
    private void handleNestedException(NestedServletException e, ServletResponse response) throws IOException {
        Throwable cause = e.getCause();
        if (cause instanceof NotLoginException) {
            responseResult(response, Result.build(null, ResultCode.NONE_LOGIN));
        } else if (cause instanceof TokenExpireException) {
            responseResult(response, Result.build(null, ResultCode.USER_TOKEN_EXPIRATION));
        } else if (cause instanceof ExpiredJwtException) {
            responseResult(response, Result.build(null, ResultCode.FILE_TOKEN_EXPIRATION));
        } else if (cause instanceof VerifyCodeException) {
            responseResult(response, Result.build(null, ResultCode.VERIFY_CODE_ERROR));
        } else if (cause instanceof EmailExistException) {
            responseResult(response, Result.build(null, ResultCode.EMAIL_EXIST));
        } else if (cause instanceof EmailRegisterExpireException) {
            responseResult(response, Result.build(null, ResultCode.EMAIL_REGISTER_EXPIRE));
        } else if (cause instanceof OSSException) {
            String message = "ErrorMessage:" + ((OSSException) cause).getErrorMessage()
                    + ",ErrorCode:" + ((OSSException) cause).getErrorCode();
            responseResult(response, Result.build(null, ResultCode.OSS_EXCEPTION).message(message));
        } else if (cause instanceof InvalidCredentialsException) {
            responseResult(response, Result.build(null, ResultCode.OSS_EXCEPTION).message(cause.getMessage()));
        } else if (cause instanceof FileNotFoundException) {
            responseResult(response, Result.build(null, ResultCode.FILE_NOT_FOUND));
        } else {
            cause.printStackTrace();
        }
    }

    /**
     * 返回错误结果
     *
     * @param response
     * @param result
     * @throws IOException
     */
    public static void responseResult(ServletResponse response, Result result) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(new Gson().toJson(result));
    }
}
