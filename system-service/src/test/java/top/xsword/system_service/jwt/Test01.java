package top.xsword.system_service.jwt;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import top.xsword.common_util.jwt.JwtUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Author: ywx
 * Create Time: 2023/2/26
 * Description:eyJhbGciOiJIUzUxMiJ9.eyJkYXRhIjoiMSJ9.IrHPVv381j8p6jqRhQ_BrGn_YTKcXM9R0RxRdSUCusoHSxtKzylePYF_X54vn3BEq39V_j2NNC69gMs3dZDexg
 */
public class Test01 {

    @Test
    public void t3() throws ParseException {
        System.out.println(JwtUtil.generateToken(1));
    }

    @Test
    public void t2() throws ParseException {
        String dateStr = "2021-01-20 10:20:53";
        DateFormat instance = DateFormat.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = simpleDateFormat.parse(dateStr);
        System.out.println(instance.getClass());
    }

    @Test
    public void t1() {
        Map<String, Object> body = new HashMap<>();
        body.put("userId", "123213");
        body.put("fileId", "222222");
        String token = JwtUtil.generateToken(body, TimeUnit.MINUTES, 10);
        System.out.println(token);
    }
}
