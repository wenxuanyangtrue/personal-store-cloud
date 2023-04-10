package top.xsword.common_util.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Data:2022/10/9
 * Author:ywx
 * Description:
 */
public class IOUtil {

    /**
     * 将输入流的数据转为String返回
     * @param in
     * @return
     * @throws IOException
     */
    public static String getString(InputStream in) throws IOException {
        return new String(getBytes(in));
    }

    /**
     * 读取输入流的字节
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] getBytes(InputStream in) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] tmp = new byte[2048];
        int len;
        while ((len = bis.read(tmp)) != -1) {
            baos.write(tmp, 0, len);
        }
        return baos.toByteArray();
    }
}
