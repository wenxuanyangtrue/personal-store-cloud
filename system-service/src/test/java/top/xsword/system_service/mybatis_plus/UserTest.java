package top.xsword.system_service.mybatis_plus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xsword.model.entity.User;
import top.xsword.system_service.service.index.UserService;

import javax.annotation.Resource;

/**
 * Data:2022/11/22
 * Author:ywx
 * Description:
 */
@SpringBootTest
public class UserTest {

    @Resource
    UserService userService;

    @Test
    public void testAdd1(){
        userService.save(new User("轩1","123456","17674797415","1825590898@qq.com","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",0));
    }

    @Test
    public void testAdd(){
        userService.save(new User("轩","123456","17674797415","1825590898@qq.com","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",0));
    }

}
