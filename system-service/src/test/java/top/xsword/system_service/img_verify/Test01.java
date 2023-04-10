package top.xsword.system_service.img_verify;

import org.junit.jupiter.api.Test;
import top.xsword.common_util.verify.ImgVerifyUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Data:2022/12/9
 * Author:ywx
 * Description:
 */
public class Test01 {

    @Test
    public void t() throws IOException {
        ImgVerifyUtil imgVerifyUtil = new ImgVerifyUtil();
        BufferedImage image = imgVerifyUtil.generateImage();
        System.out.println(imgVerifyUtil.getText());
//        ImageIO.write(image,"JPEG", new File("C:\\Users\\ywx\\Desktop\\code.jpeg"));
    }
}
