package top.xsword.system_service.controller.index;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import top.xsword.common_util.result.Result;
import top.xsword.model.entity.User;
import top.xsword.model.vo.UserInfoVo;
import top.xsword.system_service.service.index.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ywx
 * @since 2022-11-21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PutMapping("/info")
    public Result updateUserInfo(HttpServletRequest request,
                                 @RequestBody UserInfoVo userInfoVo) {
        if (userInfoVo == null) {
            return Result.fail();
        }
        System.out.println(userInfoVo);
        String userId = ((User) request.getAttribute("user")).getId();
        User user = new User();
        user.setId(userId);
        if (StringUtils.hasLength(userInfoVo.getUsername())) {
            user.setUsername(userInfoVo.getUsername());
        } else if (StringUtils.hasLength(userInfoVo.getEmail())) {
            user.setEmail(userInfoVo.getEmail());
        } else if (StringUtils.hasLength(userInfoVo.getPhone())) {
            user.setPhone(userInfoVo.getPhone());
        } else {
            return Result.fail();
        }
        return userService.saveOrUpdate(user) ? Result.ok() : Result.fail();
    }
}
