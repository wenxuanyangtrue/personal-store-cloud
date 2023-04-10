package top.xsword.system_service.mybatis_plus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xsword.model.entity.File;
import top.xsword.system_service.service.public_storage.FileService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Data:2022/11/23
 * Author:ywx
 * Description:
 */
@SpringBootTest
public class FileTest {
    @Resource
    FileService fileService;

    @Test
    public void fileByDirId(){
//        List<File> files = fileService.selectByDirId("1595376085228703745");
//        files.forEach(System.out::println);
    }

    @Test
    public void mkFile4(){
        File file = new File();
        file.setDirId("1595765594886627329");
        file.setFilename("凡人-all.json");
        file.setSize(23451045L);
        file.setSuffix("json");
        file.setType("application/json");
        fileService.saveFile(file);
    }

    @Test
    public void mkFile3(){
        File file = new File();
        file.setDirId("1595692123372470273");
        file.setFilename("凡人.json");
        file.setSize(254594L);
        file.setSuffix("json");
        file.setType("application/json");
        fileService.saveFile(file);
    }

    @Test
    public void mkFile2(){
        File file = new File();
        file.setDirId("1595380261086142466");
        file.setFilename("搜图网址.txt");
        file.setSize(423L);
        file.setSuffix("txt");
        file.setType("text/plain");
        fileService.saveFile(file);
    }

    @Test
    public void mkFile(){
        File file = new File();
        file.setDirId("1595376085228703745");
        file.setFilename("Aurora_Melusine.png");
        file.setSize(4298234L);
        file.setSuffix("png");
        file.setType("image/png");
        fileService.saveFile(file);
    }
}
