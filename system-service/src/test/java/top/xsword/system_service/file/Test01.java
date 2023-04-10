package top.xsword.system_service.file;

import org.junit.jupiter.api.Test;
import top.xsword.common_service.config.SecretKeyConfig;
import top.xsword.common_util.file.FileUtil;
import top.xsword.common_util.io.IOUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Author: ywx
 * Create Time: 2023/2/15
 * Description:
 */
public class Test01 {

    @Test
    public void t5() throws IOException {
        InputStream is = SecretKeyConfig.class.getClassLoader().getResourceAsStream("secret/key");
        String key = IOUtil.getString(is);

        System.out.println(key);
    }

    @Test
    public void t4() {
        String format = SimpleDateFormat.getDateInstance().format(new Date());
        System.out.println(format);
    }

    @Test
    public void t3() throws IOException {
        String m = "2023-02-18 20:59:09";
        String[] s = m.split(" ");
        System.out.println(Arrays.toString(s));
    }

    @Test
    public void t2() throws IOException {
        Path path = Paths.get("D:/MyStorageSpace/upload", "a.jpg");
        System.out.println(path);
    }

    @Test
    public void t1() throws IOException {
        Path path = Paths.get("D:/MyStorageSpace/upload");
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        } else {
            System.out.println("已经存在");
        }
    }
}
