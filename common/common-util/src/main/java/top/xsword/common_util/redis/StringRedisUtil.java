package top.xsword.common_util.redis;

import com.google.gson.Gson;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.xsword.common_util.jwt.JwtUtil;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Data:2022/12/10
 * Author:ywx
 * Description:
 */
@Component
public class StringRedisUtil {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    public String toJson(Object val) {
        return JwtUtil.toJson(val);
    }

    public <T> T toObj(String json, Class<T> cla) {
        return JwtUtil.toObj(json, cla);
    }

    /**
     * @param key      键
     * @param timeUnit 时间单位
     * @return 过期时间
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        try {
            return stringRedisTemplate.getExpire(key, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param key 键
     * @return 过期时间，单位为秒
     */
    public Long getExpire(String key) {
        try {
            return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param key  键
     * @param time 过期时间，默认为秒
     * @return 是否设置成功
     */
    public Boolean expire(String key, long time) {
        try {
            if (time > 0) {
                stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * @param key      键
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return 是否设置成功
     */
    public Boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                stringRedisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key 键
     * @return 是否有该key
     */
    public Boolean hasKey(String key) {
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key 键
     * @return 值
     */
    public Boolean del(String key) {
        try {
            return stringRedisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param key     键
     * @param val     值
     * @param timeout 过期时间
     * @return 是否成功set
     */
    public Boolean set(String key, String val, Duration timeout) {
        try {
            stringRedisTemplate.opsForValue().set(key, val, timeout);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key     键
     * @param val     值
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 是否成功set
     */
    public Boolean set(String key, String val, long timeout, TimeUnit unit) {
        try {
            stringRedisTemplate.opsForValue().set(key, val, timeout, unit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key     键
     * @param val     值
     * @param timeout 过期时间，默认为秒
     * @return 是否成功set
     */
    public Boolean set(String key, String val, long timeout) {
        try {
            stringRedisTemplate.opsForValue().set(key, val, timeout, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key 键
     * @param val 值
     * @return 是否成功set
     */
    public Boolean set(String key, String val) {
        try {
            stringRedisTemplate.opsForValue().set(key, val);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
