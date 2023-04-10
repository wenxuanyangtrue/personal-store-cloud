package top.xsword.system_service.oss;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xsword.system_service.service.oss.GetObjectService;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * Data:2022/12/1
 * Author:ywx
 * Description:
 */
@SpringBootTest
public class GetObjectSample {

    @Resource
    GetObjectService getObjectService;

    @Test
    public void test() throws UnsupportedEncodingException {
//        String s = getObjectService.generateUrl("1595024548841193474", "1595024548841193474/凡人.json");
//        System.out.println(s);
    }
}
