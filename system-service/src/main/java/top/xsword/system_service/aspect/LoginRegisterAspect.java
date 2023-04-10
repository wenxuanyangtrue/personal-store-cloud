package top.xsword.system_service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.xsword.common_util.exception.VerifyCodeException;
import top.xsword.common_util.redis.StringRedisUtil;
import top.xsword.model.vo.LoginVo;
import top.xsword.model.vo.RegisterVo;

import javax.annotation.Resource;

/**
 * Author: ywx
 * Create Time: 2022/12/17
 * Description:
 */
@Aspect
@Component
public class LoginRegisterAspect {

    @Resource
    StringRedisUtil stringRedisUtil;

    @Pointcut("execution(* top.xsword.system_service.controller.index.IndexController.login(..))")
    public void login() {
    }

    @Pointcut("execution(* top.xsword.system_service.controller.index.RegisterController.register(..))")
    public void register() {
    }

    //验证码校验
    @Before("login()||register()")
    public void validate(JoinPoint jp) {
        String uuid;
        String userVerifyCode;
        String verifyCode;
        if (jp.getArgs()[0] instanceof LoginVo) { //验证登录的验证码
            LoginVo loginVo = (LoginVo) jp.getArgs()[0];
            uuid = loginVo.getUuid(); //获取前端传来的uuid
            verifyCode = stringRedisUtil.get(uuid); //根据uuid去redis获取验证码的值
            userVerifyCode = loginVo.getVerifyCode(); //获取前端传来的验证码值
        } else { //验证注册的验证码
            RegisterVo registerVo = (RegisterVo) jp.getArgs()[0];
            uuid = registerVo.getUuid(); //获取前端传来的uuid
            verifyCode = stringRedisUtil.get(uuid); //根据uuid去redis获取验证码的值
            userVerifyCode = registerVo.getVerifyCode(); //获取前端传来的验证码值
        }

        System.out.println(uuid+ " " +userVerifyCode + " " + verifyCode);
        //验证码有误或超时则抛出异常，注册失败
        if (!StringUtils.hasLength(verifyCode) || !verifyCode.equalsIgnoreCase(userVerifyCode)) {
            throw new VerifyCodeException();
        }
        //验证码验证成功则继续注册或登录的操作，删除redis中该验证码的记录
        stringRedisUtil.del(uuid);
    }
}
