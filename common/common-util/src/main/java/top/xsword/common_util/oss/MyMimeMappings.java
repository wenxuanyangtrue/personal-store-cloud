package top.xsword.common_util.oss;

import org.springframework.boot.web.server.MimeMappings;

/**
 * Author: ywx
 * Create Time: 2023/2/11
 * Description:
 */
public class MyMimeMappings {
    public static MimeMappings mimeMappings = new MimeMappings();

    static {
        mimeMappings.add("md","text/markdown");
    }

}
