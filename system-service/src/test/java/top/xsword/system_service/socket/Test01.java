package top.xsword.system_service.socket;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Author: ywx
 * Create Time: 2023/2/22
 * Description:
 */
public class Test01 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        while (true) {
            Socket socket = serverSocket.accept();
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            byte[] data = new byte[128];
            int len;
            while ((len = bis.read(data)) != -1) {
                System.out.println(new String(data, 0, len));
            }
            socket.shutdownInput();
            socket.getOutputStream().write("ok".getBytes(StandardCharsets.UTF_8));
            socket.shutdownOutput();
            bis.close();
        }
    }
}

class Test02 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8081);
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write("Hello".getBytes());
        bos.flush();
        socket.shutdownOutput();

        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] data = new byte[128];
        int len;
        while ((len = bis.read(data)) != -1) {
            System.out.println(new String(data, 0, len));
        }
//        bos.close();
    }
}
