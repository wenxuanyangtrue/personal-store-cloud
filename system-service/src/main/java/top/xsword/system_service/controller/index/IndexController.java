package top.xsword.system_service.controller.index;

import org.springframework.web.bind.annotation.*;
import top.xsword.common_util.jwt.JwtUtil;
import top.xsword.common_util.redis.StringRedisUtil;
import top.xsword.common_util.result.Result;
import top.xsword.common_util.result.ResultCode;
import top.xsword.common_util.verify.ImgVerifyUtil;
import top.xsword.model.entity.User;
import top.xsword.model.vo.LoginVo;
import top.xsword.system_service.annotation.ExcludeValidate;
import top.xsword.system_service.service.index.UserService;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Data:2022/11/19
 * Author:ywx
 * Description:
 */
@RestController
public class IndexController {

    @Resource
    UserService userService;

    @Resource
    StringRedisUtil stringRedisUtil;

    @GetMapping("/verifycode")
    @ExcludeValidate
    public Result verifyCode() throws IOException {
        ImgVerifyUtil imgVerifyUtil = new ImgVerifyUtil();
        BufferedImage image = imgVerifyUtil.generateImage();
        String text = imgVerifyUtil.getText();
        String uuid = UUID.randomUUID().toString(); //生成验证码的UUID
        stringRedisUtil.set(uuid, text, 300); //验证码在Redis中缓存5分钟

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", baos);
        byte[] bytes = baos.toByteArray();
        String imageBase64 = Base64.getEncoder().encodeToString(bytes);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", uuid);
        map.put("image", imageBase64);
        return Result.ok(map); //将id与base64格式的验证码图片以json格式返回给前端
    }

    @PostMapping("/login")
    @ExcludeValidate
    public Result<Map<Object, Object>> login(@RequestBody LoginVo loginVo) {
        System.out.println(loginVo);
        User user = null;
        //如果邮箱不为空，就说明前端用邮箱登录，否则用手机号登录
        if (loginVo.getEmail() != null) {
            user = userService.emailLogin(loginVo.getEmail(), loginVo.getPassword());
        } else {

        }

        if (user == null) {
            return Result.build(null, ResultCode.NO_SUCH_USER);
        }
        HashMap<Object, Object> map = new HashMap<>();

        String token = JwtUtil.generateToken(user);
        //设置用户Token过期时间为1小时
        stringRedisUtil.set(user.getId(), token, 1, TimeUnit.HOURS);
        map.put("token", token);
        return Result.ok(map);
    }

    @GetMapping("/info")
    public Result<Map<Object, Object>> info(HttpServletRequest request) {
        User user = userService.getById(
                ((User) request.getAttribute("user")).getId());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("avatar", user.getAvatar());
        map.put("username", user.getUsername());
        map.put("email",user.getEmail());
        map.put("phone",user.getPhone());
        map.put("createTime",user.getCreateTime());
        return Result.ok(map);
    }

    @GetMapping("/logout")
    public Result<Map<Object, Object>> logout(HttpServletRequest request) {
        String userId = ((User) request.getAttribute("user")).getId();
        stringRedisUtil.del(userId);
        return Result.ok();
    }

}
