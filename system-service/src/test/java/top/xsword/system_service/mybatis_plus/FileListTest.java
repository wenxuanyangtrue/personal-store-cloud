package top.xsword.system_service.mybatis_plus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xsword.model.vo.DirFileVo;
import top.xsword.system_service.service.public_storage.DirFileService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Data:2022/11/23
 * Author:ywx
 * Description:
 */
@SpringBootTest
public class FileListTest {

    @Resource
    DirFileService dirFileService;

    @Test
    public void listPage() {
        Page<DirFileVo> dirFileVoPage = dirFileService
                .dirFileListPage(3, 1, "1595024548841193474", null);
        System.out.println(dirFileVoPage.getRecords());
    }

    @Test
    public void remove() {
        dirFileService.removeDirAndFile("1595024548841193474", Arrays.asList("1598161914078511106"));
        //System.out.println(((DirFileServiceImpl) dirFileService).count);
    }

    @Test
    public void list() {
        List<DirFileVo> dirs = dirFileService.dirFileList("1595024548841193474", "1595376085228703745");
        dirs.forEach(System.out::println);
    }

}
