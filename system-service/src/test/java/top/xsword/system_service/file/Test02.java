package top.xsword.system_service.file;

import org.junit.jupiter.api.Test;
import top.xsword.common_util.file.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Author: ywx
 * Create Time: 2023/3/24
 * Description:
 */
public class Test02 {

    @Test
    public void t1() throws IOException {
        String path = "D:\\MyStorageSpace\\bilibili\\download\\CentOS7安装MySQL8.0.28";
        BasicFileAttributeView fileAttributeView = Files.getFileAttributeView(Paths.get(path), BasicFileAttributeView.class);
        BasicFileAttributes basicFileAttributes = fileAttributeView.readAttributes();
        System.out.println(FileUtil.formattedSize(basicFileAttributes.size()));
        System.out.println(basicFileAttributes.isDirectory());
    }

    @Test
    public void t2() {
        File file = new File("/");
        long totalSpace = file.getTotalSpace();
        long usableSpace = file.getUsableSpace();
        long freeSpace = file.getFreeSpace();
        System.out.println(file.getAbsolutePath());
        System.out.println(FileUtil.formattedSize(totalSpace));
        System.out.println(FileUtil.formattedSize(usableSpace));
        System.out.println(FileUtil.formattedSize(freeSpace));
        System.out.println(FileUtil.formattedSize(totalSpace - freeSpace));
    }
}
