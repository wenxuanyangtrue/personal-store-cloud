package top.xsword.system_service.oss;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Data:2022/11/27
 * Author:ywx
 * Description:
 */
public class Test02 {
    public static void main(String[] args) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("https://4tx5057851.imdo.co/callback")
                .openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-type","application/json;charset=utf-8");
        conn.connect();
        conn.getOutputStream().write("{\"name\":\"aaa\"}".getBytes(StandardCharsets.UTF_8));
        InputStream inputStream = conn.getInputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            System.out.println(new String(buffer, 0, len));
        }
        conn.disconnect();
    }
}
