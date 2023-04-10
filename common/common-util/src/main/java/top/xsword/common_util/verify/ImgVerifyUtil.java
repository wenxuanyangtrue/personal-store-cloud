package top.xsword.common_util.verify;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Data:2022/12/9
 * Author:ywx
 * Description:
 */
public class ImgVerifyUtil {
    //验证码图片的宽度
    private Integer width = 100;
    //长度
    private Integer height = 40;
    //验证码
    private String text;
    //随机数
    private final Random r = new Random();
    //字体名
    private final String[] fontNames = {"Georgia"};
    //验证码的范围
    private String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

    /**
     * 获取随机的颜色
     *
     * @return
     */
    private Color randomColor() {
        int r = this.r.nextInt(225); //225是为了让颜色深一点
        int g = this.r.nextInt(225);
        int b = this.r.nextInt(225);
        return new Color(r, g, b);
    }

    /**
     * 获取随机字体
     *
     * @return
     */
    private Font randomFont() {
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];
        int style = r.nextInt(4);       //随机获取字体样式
        int size = r.nextInt(10) + 24;  //随机获取字体大小，至少是24
        return new Font(fontName, style, size);
    }

    /**
     * 获取随机字符
     *
     * @return
     */
    private char randomChar() {
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }

    /**
     * 画干扰线
     *
     * @param image
     */
    private void drawLine(BufferedImage image) {
        int num = r.nextInt(10) + 1; //干扰线的数量1到10随机
        Graphics2D g = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = r.nextInt(width);
            int x2 = r.nextInt(width);
            int y1 = r.nextInt(height);
            int y2 = r.nextInt(height);
            g.setColor(randomColor());
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 创建验证码的图像
     *
     * @return
     */
    private BufferedImage createImage() {
        //创建图片缓冲区
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
//        g.setColor(randomColor());
        g.fillRect(0, 0, width, height);
        return image;
    }

    public BufferedImage generateImage() {
        BufferedImage image = createImage();
        Graphics2D g = (Graphics2D) image.getGraphics();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(randomChar());
            int x = i * width / 4;
            g.setFont(randomFont());
            g.setColor(randomColor());
            g.drawString(sb.substring(i), x, height - 10);
        }
        text = sb.toString();
        drawLine(image);
        return image;
    }

    public String getText() {
        return text;
    }
}
