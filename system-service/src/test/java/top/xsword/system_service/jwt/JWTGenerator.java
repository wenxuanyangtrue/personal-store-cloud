package top.xsword.system_service.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import top.xsword.common_util.jwt.JwtUtil;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Data:2022/11/22
 * Author:ywx
 * Description:
 */
//@SpringBootTest
public class JWTGenerator {

    //加密与解密的密钥
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Test
    public void t1() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        String content = "你好啊实打实的";
//        String password = "123456";
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//
//        SecureRandom secureRandom = new SecureRandom();
//        secureRandom.setSeed(password.getBytes());
//
//        keyGenerator.init(128, secureRandom);
//
//        SecretKey secretKey = keyGenerator.generateKey();
//
//        Cipher aesCipher = Cipher.getInstance("AES");
//        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
//
//        byte[] bytes = aesCipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
//
//        System.out.println(new BigInteger(1,bytes).toString(16));

        byte[] bytes = SecureRandom.getInstanceStrong().generateSeed(128);
        String sec = Base64.getEncoder().encodeToString(bytes);
        System.out.println(sec);
//        SecretKeySpec hmacSHA256 = new SecretKeySpec(Base64.getDecoder().decode(sec), "HmacSHA256");
//        String compact = Jwts.builder()
//                .signWith(hmacSHA256)
//                .claim("A", "assss")
//                .compact();
//        System.out.println(compact);
//        Object a = Jwts.parserBuilder()
//                .setSigningKey(hmacSHA256).build().parseClaimsJws(compact).getBody().get("A");
//        System.out.println(a);

    }

    @Test
    public void t() {
        byte[] bytes = Jwts.builder()
                .setSubject("USER")
                .claim("userId", "111111111")
                .signWith(SECRET_KEY)
                //.compressWith(CompressionCodecs.GZIP)
                .compact()
                .getBytes(StandardCharsets.UTF_8);

        System.out.println(new String(bytes));
    }

    @Test
    public void test() {
//        String token = JwtUtil.generateToken("1589618290420105217");
//        System.out.println(token);
//        String userId = JwtUtil.parseId(token);
//        System.out.println(userId);
//        Integer role = JwtUtil.parseRole("ZXlKaGJHY2lPaUpJVXpVeE1pSjkuZXlKemRXSWlPaUpWVTBWU0lpd2lkWE5sY2tsa0lqb2lNVFl3TVRreU1EQTVOVFE0TlRJeE5EY3lNU0lzSW5KdmJHVWlPakI5LllzaHFTQ0tVVEVXTDhINWd6SEZZaXI3VHptWEZLVlZkOXBsdHdEOGQyNWc3Y3o3aWFfdUJYNmJsUEpJamxncXRHUVVUMkR4QUFscWFzLWF0NE9XblNR");
//        System.out.println(role);
    }
}
