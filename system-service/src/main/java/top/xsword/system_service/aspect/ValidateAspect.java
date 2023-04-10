package top.xsword.system_service.aspect;

import io.jsonwebtoken.ExpiredJwtException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.xsword.common_util.exception.NotLoginException;
import top.xsword.common_util.exception.TokenExpireException;
import top.xsword.common_util.redis.StringRedisUtil;
import top.xsword.model.entity.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Data:2022/12/9
 * Author:ywx
 * Description:
 */
@Aspect
@Component
@Order(0)
public class ValidateAspect {

    @Resource
    StringRedisUtil stringRedisUtil;

    //类上有@ValidateToken
    @Pointcut("@within(top.xsword.system_service.annotation.ValidateToken)")
    public void validateTokenClass() {}

    //controller下除了private的所有方法
    @Pointcut("execution(* top.xsword.system_service.controller.*.*.*(..))")
    public void validateTokenClass2() {}

    //方法上加@ValidateToken
    @Pointcut("@annotation(top.xsword.system_service.annotation.ValidateToken)")
    public void validateTokenMethod() {}

    //方法上加@ExcludeValidate
    @Pointcut("@annotation(top.xsword.system_service.annotation.ExcludeValidate)")
    public void excludeValidateMethod() {}

    //类上加@ExcludeValidate
    @Pointcut("@within(top.xsword.system_service.annotation.ExcludeValidate)")
    public void excludeValidateClass() {}

    //前置方法，代理controller包下除了标识@ExcludeValidate的方法和类以外的所有方法
    @Before("validateTokenClass2()&&!excludeValidateMethod()&&!excludeValidateClass()")
    public void validateToken(JoinPoint jp) {
        HttpServletRequest request = (HttpServletRequest) jp.getArgs()[0];
        User user = (User) request.getAttribute("user");
        if (user == null) { //user为null说明没有登录
            throw new NotLoginException();
        } else if (!stringRedisUtil.hasKey(user.getId())) {
            //redis中没有该用户id对应的token则说明token已过期
            throw new TokenExpireException();
        }
        //token延期
        overtime(user.getId());
    }

    //token延期，如果不大于30分钟，则延期至1小时
    private void overtime(String userId) {
        Long expire = stringRedisUtil.getExpire(userId, TimeUnit.MINUTES);
        if (expire <= 30) {
            stringRedisUtil.expire(userId, 1, TimeUnit.HOURS);
        }
    }
}
