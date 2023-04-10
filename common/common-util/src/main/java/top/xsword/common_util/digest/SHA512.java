package top.xsword.common_util.digest;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author: ywx
 * Create Time: 2022/12/14
 * Description:
 */
public class SHA512 {
    private static MessageDigest sha512 = null;

    static {
        try {
            sha512 = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建签名
     * @param input
     * @return
     */
    public static String digest(String... input) {
        for (String in : input) {
            sha512.update(in.getBytes(StandardCharsets.UTF_8));
        }
        byte[] digest = sha512.digest();
        sha512.reset();
        return new BigInteger(1, digest).toString(16);
    }

}
