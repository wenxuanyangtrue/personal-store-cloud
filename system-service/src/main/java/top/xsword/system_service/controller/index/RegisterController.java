package top.xsword.system_service.controller.index;

import org.apache.commons.mail.EmailException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.xsword.common_util.exception.EmailExistException;
import top.xsword.common_util.exception.EmailRegisterExpireException;
import top.xsword.common_util.redis.StringRedisUtil;
import top.xsword.common_util.result.Result;
import top.xsword.model.entity.Dir;
import top.xsword.model.entity.User;
import top.xsword.model.vo.RegisterVo;
import top.xsword.system_service.annotation.ExcludeValidate;
import top.xsword.system_service.service.index.EmailService;
import top.xsword.system_service.service.index.UserService;
import top.xsword.system_service.service.public_storage.DirService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Author: ywx
 * Create Time: 2023/1/13
 * Description:
 */
@RestController
@ExcludeValidate
@RequestMapping
public class RegisterController {

    @Resource
    UserService userService;

    @Resource
    DirService dirService;

    @Resource
    StringRedisUtil stringRedisUtil;

    @Resource
    EmailService emailService;

    Map<String, String> activeMap = new HashMap<>();

    @GetMapping("/register/active/{uuid}")
    public Result active(@PathVariable String uuid) {
        String userJson = stringRedisUtil.get(uuid); //根据uuid获取redis中的用户信息
        if (userJson == null) { //如果为null则激活失败，返回提示信息
            throw new EmailRegisterExpireException();
        }
        //将用户信息json转为对象实例
        User user = stringRedisUtil.toObj(userJson, User.class);
        if (userService.save(user)) { //将用户保存到数据库中
            Dir dir = new Dir();
            dir.setUserId(user.getId());
            dir.setDirname("/");
            stringRedisUtil.del(uuid);
            activeMap.remove(user.getEmail());
            //用户激活后，创建根目录
            return dirService.saveDir(dir) ? Result.ok() : Result.fail();
        } else {
            return Result.fail();
        }
    }

    @GetMapping("/exist")
    public Result exist(@RequestParam(required = false) String email, @RequestParam(required = false) String phone) {
        if (StringUtils.hasLength(email) && userService.emailExist(email)) {
            throw new EmailExistException();
        }
        return Result.ok();
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo) throws EmailException {
        System.out.println(registerVo);
        //邮箱注册
        if (StringUtils.hasLength(registerVo.getEmail()) && StringUtils.hasLength(registerVo.getPassword())) {
            if (userService.emailExist(registerVo.getEmail())) { //判断邮箱是否已注册
                throw new EmailExistException();
            }

            User user = new User();
            user.setUsername(registerVo.getEmail());
            user.setEmail(registerVo.getEmail());
            user.setPassword(registerVo.getPassword());

            //查看是否为正在激活的邮箱，如果是则用之前的uuid，不是则新创建一个uuid
            String uuid = activeMap.get(registerVo.getEmail());
            uuid = uuid != null ? uuid : UUID.randomUUID().toString();

            //将用户提交的信息转json，生成uuid为key存入redis中，1天后过期
            stringRedisUtil.set(uuid, stringRedisUtil.toJson(user), 1, TimeUnit.DAYS);

            activeMap.put(registerVo.getEmail(), uuid);

            //发送邮件
            emailService.sendHTML(registerVo.getEmail(),
                    "http://127.0.0.1:9527/#/register?uuid=" + uuid);

        } else if (StringUtils.hasLength(registerVo.getPhone()) &&
                StringUtils.hasLength(registerVo.getPassword())) {//手机号注册

        } else {//不符合条件，抛出异常

        }
        return Result.ok();
    }
}
