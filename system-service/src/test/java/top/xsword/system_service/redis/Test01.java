package top.xsword.system_service.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Data:2022/12/10
 * Author:ywx
 * Description:
 */
@SpringBootTest
public class Test01 {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void del(){
        Boolean delete = stringRedisTemplate.delete("1595024548841193474");
        System.out.println(delete);
    }

    @Test
    public void get(){
        ValueOperations<String, String> ssvo = stringRedisTemplate.opsForValue();
        String s = ssvo.get("155024548841193474");
        System.out.println(StringUtils.hasLength(s));
    }

    @Test
    public void add(){
        ValueOperations<String, String> ssvo = stringRedisTemplate.opsForValue();
        ssvo.set("1595024548841193474","ZXlKaGJHY2lPaUpJVXpVeE1pSjkuZXlKemRXSWl" +
                "PaUpWVTBWU0lpd2laWGh3SWpveE5qY3dOamMwT1RRM0xDSjFjMlZ5U1dRaU9pS" +
                "XhOVGsxTURJME5UUTRPRFF4TVRrek5EYzBJbjAuWXRYemx0S3NfZm5nOEFnUEg" +
                "4bjJESUdNZkkzS1ljamoyd0J4VXNZOTJNR2JFV01ILXZJLVlmYkpuOVh5WVAwS" +
                "2FWTWtpTGxpNVdJTXR4OFRHNlo2aFE",10,TimeUnit.SECONDS);

    }
}
