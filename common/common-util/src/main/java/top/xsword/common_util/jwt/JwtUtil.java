package top.xsword.common_util.jwt;

import com.aliyuncs.utils.StringUtils;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import top.xsword.common_util.io.IOUtil;
import top.xsword.model.entity.User;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Data:2022/11/22
 * Author:ywx
 * Description:
 */
public class JwtUtil {

    //加密与解密的密钥
    private static final SecretKey SECRET_KEY;
    private static final String BODY = "data";
    private static final Gson gson = new Gson();

    static {
        InputStream is = JwtUtil.class.getClassLoader().getResourceAsStream("secret/key");
        String key = null;
        try {
            key = IOUtil.getString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SECRET_KEY = new SecretKeySpec(Base64.getDecoder().decode(key), "HmacSHA256");
    }

    public static String toJson(Object val) {
        return gson.toJson(val);
    }

    public static <T> T toObj(String json, Class<T> cla) {
        return gson.fromJson(json, cla);
    }

    /**
     * 生成token
     */
    public static String generateToken(Object data) {
        return Jwts.builder()
                        .claim(BODY, toJson(data)) //存入数据
                        .signWith(SECRET_KEY) //使用密钥
                        .compact();
    }

    /**
     * 根据过期时间生成token
     */
    public static String generateToken(Object data, TimeUnit timeUnit, long expiration) {
        long l = System.currentTimeMillis() + timeUnit.toMillis(expiration);
        return Jwts.builder()
                        .claim(BODY, toJson(data))
                        .signWith(SECRET_KEY)
                        .setExpiration(new Date(l))
                        .compact();
    }

    /**
     * token内的body数据
     */
    public static <T> T parse(String token, Class<T> requiredType) {
        if (StringUtils.isEmpty(token)) return null;

        String data;
        try {
            data = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(BODY, String.class);
        } catch (MalformedJwtException e) {
            return null;
        }
        return toObj(data, requiredType);
    }

    /**
     * 获取token内的body
     */
    public static Claims getBody(String token) {
        if (StringUtils.isEmpty(token)) return null;

        Claims data;
        try {
            data = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            return null;
        }
        return data;
    }
}
