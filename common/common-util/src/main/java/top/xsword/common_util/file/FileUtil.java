package top.xsword.common_util.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Data:2022/11/24
 * Author:ywx
 * Description:
 */
public class FileUtil {

    public static final int KILO_BYTE = (int) Math.pow(2, 10);
    public static final int MEGA_BYTE = (int) Math.pow(2, 20);
    public static final int GIGA_BYTE = (int) Math.pow(2, 30);

    public static String formattedSize(Long size) {
        if (size == 0) {
            return "0KB";
        } else if (size < MEGA_BYTE) {
            return String.format("%.3fKB", (double) size / KILO_BYTE);
        } else if (size < GIGA_BYTE) {
            return String.format("%.3fMB", (double) size / MEGA_BYTE);
        } else {
            return String.format("%.3fGB", (double) size / GIGA_BYTE);
        }
    }

    public static void makeDirIfNotExist(String location, String... more) {
        Path path = Paths.get(location, more);
        if (Files.notExists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void makeDirs(String location, String... more) {
        Path path = Paths.get(location, more);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSuffix(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.lastIndexOf('.');
        if (index > -1) {
            return filename.substring(index + 1);
        } else {
            return null;
        }
    }
}
