package top.xsword.system_service.digest;

import org.junit.jupiter.api.Test;
import top.xsword.common_util.digest.SHA512;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Author: ywx
 * Create Time: 2022/12/14
 * Description:
 */
public class Test01 {

    @Test
    public void t1(){
        System.out.println(d1());
    }

    public String d1(){
        String s = "16019200954852147211825590898@qq.com123456";
        return SHA512.digest(s);
    }

    public String d2(){
        String id = "1601920095485214721";
        String email = "1825590898@qq.com";
        String password = "123456";
        return SHA512.digest(id,email,password);
    }
}
