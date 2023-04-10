package top.xsword.system_service.mybatis_plus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xsword.model.entity.Dir;
import top.xsword.system_service.service.public_storage.DirService;

import javax.annotation.Resource;

/**
 * Data:2022/11/23
 * Author:ywx
 * Description:
 */
@SpringBootTest
public class DirTest {

    @Resource
    DirService dirService;

    @Test
    public void mkdir5(){
        Dir dir = new Dir();
        dir.setDirname("/");
        dir.setUserId("1630911612362993666");
        dirService.save(dir);
    }

    @Test
    public void mkdir4(){
        Dir dir = new Dir();
        dir.setDirname("001");
        dir.setParentId("1595692123372470273");
        dir.setUserId("1595024548841193474");
        dirService.saveDir(dir);
    }

    @Test
    public void mkdir3(){
        Dir dir = new Dir();
        dir.setDirname("game");
        dir.setParentId(null);
        dir.setUserId("1595024548841193474");
        dirService.saveDir(dir);
    }

    @Test
    public void mkdir2(){
        Dir dir = new Dir();
        dir.setDirname("novel");
        dir.setParentId("1595376085228703745");
        dir.setUserId("1595024548841193474");
        dirService.saveDir(dir);
    }

    @Test
    public void mkdir(){
        Dir dir = new Dir();
        dir.setDirname("我的资源");
        dir.setParentId(null);
        dir.setUserId("1595024548841193474");
        dirService.saveDir(dir);
    }
}
