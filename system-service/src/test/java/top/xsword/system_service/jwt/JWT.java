package top.xsword.system_service.jwt;

import com.google.gson.Gson;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: ywx
 * Create Time: 2022/12/19
 * Description:
 */
public class JWT {
    private static SecretKey secretKey;
    private static Mac hmacSHA256;

    private Map<String, Object> header = new HashMap<>();
    private Map<String, Object> payload = new HashMap<>();
    private String signature;

    static {
        try {
            //生成HmacSHA256签名算法的密钥
            secretKey = KeyGenerator.getInstance("HmacSHA256").generateKey();
            //实例化HmacSHA256签名算法的对象
            hmacSHA256 = Mac.getInstance(secretKey.getAlgorithm());
            //使用密钥初始化该算法对象
            hmacSHA256.init(secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    //设置header
    private void header() {
        header.put("alg", "HS256");
        header.put("type", "JWT");
    }

    //设置payload的标题
    public JWT setSub(String sub) {
        payload.put("sub", sub);
        return this;
    }

    //设置token过期时间
    public JWT setExpire(long time) {
        payload.put("exp", time);
        return this;
    }

    //设置token携带的用户数据
    public JWT body(Map<String, Object> body) {
        payload.put("body", body);
        return this;
    }

    public String generateToken() {
        header(); //初始化头部
        Gson gson = new Gson();
        Base64.Encoder urlEncoder = Base64.getUrlEncoder();
        //生成header的base64字符串
        String base64Header = new String(urlEncoder.encode(gson.toJson(this.header).getBytes(StandardCharsets.UTF_8)));
        //生成payload的base64字符串
        String base64Payload = new String(urlEncoder.encode(gson.toJson(this.payload).getBytes(StandardCharsets.UTF_8)));
        //将两个base64字符串用.拼接到一起
        String headerPayload = base64Header + '.' + base64Payload;
        //对header和payload进行签名
        byte[] data = hmacSHA256.doFinal(headerPayload.getBytes(StandardCharsets.UTF_8));
        //将签名转为十六进制
        this.signature = new BigInteger(1, data).toString(16);
        String token = headerPayload + '.' + this.signature;
        //最后将token进行base64编码后返回
        return new String(urlEncoder.encode(token.getBytes(StandardCharsets.UTF_8)));
    }

    public JWT parseToken(String token) {
        Gson gson = new Gson();
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        token = new String(urlDecoder.decode(token.getBytes(StandardCharsets.UTF_8)));

        String[] split = token.split("\\.");
        String h = split[0];
        String p = split[1];
        String signature = split[2];
        String headerPayload = h + '.' + p;
        byte[] data = hmacSHA256.doFinal(headerPayload.getBytes(StandardCharsets.UTF_8));
        String sign = new BigInteger(1, data).toString(16);
        if (!signature.equals(sign)) {
            System.err.println("验证签名失败，token被非法篡改");
            return this;
        }
        String header = new String(urlDecoder.decode(h.getBytes(StandardCharsets.UTF_8)));
        String payload = new String(urlDecoder.decode(p.getBytes(StandardCharsets.UTF_8)));

        this.header = gson.fromJson(header, Map.class);
        this.payload = gson.fromJson(payload, Map.class);
        return this;
    }

    public static void main(String[] args) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("username", "admin");
        String token = new JWT()
                .setSub("user")
                .setExpire(1800000)
                .body(body)
                .generateToken();
        System.out.println(token);

        byte[] decode = Base64.getUrlDecoder().decode(token);
        String sign = new String(decode).split("\\.")[2];

        String header = new String(Base64.getUrlEncoder().encode("{\"type\":\"JWT\",\"alg\":\"HS256\"}".getBytes(StandardCharsets.UTF_8)));
        String payload = new String(Base64.getUrlEncoder().encode("{\"sub\":\"user\",\"exp\":1800000,\"body\":{\"username\":\"us\"}}".getBytes(StandardCharsets.UTF_8)));
        byte[] encode = Base64.getUrlEncoder().encode((header + "." + payload + "." + sign).getBytes(StandardCharsets.UTF_8));

        Object body1 = new JWT()
                .parseToken(new String(encode))
                .getPayload()
                .get("body");
//        System.out.println(body1);
    }
}
