package sba.project.tuvanluatgiaothong.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ------------------------------------------
    // GET VALUE FUNCTIONS
    // ------------------------------------------

    public boolean hasKey(String key) {
        Boolean result = redisTemplate.hasKey(key);
        return result != null && result;
    }

    public Object getValueByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object getValueIfExists(String key) {
        return hasKey(key) ? getValueByKey(key) : null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getTypedValue(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (clazz.isInstance(value)) {
            return (T) value;
        }
        return null;
    }

    // ------------------------------------------
    // SET VALUE FUNCTIONS
    // ------------------------------------------

    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public void setValue(String key, Object value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(timeoutSeconds));
    }

    public boolean setIfAbsent(String key, Object value) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
        return result != null && result;
    }

    public boolean setIfAbsent(String key, Object value, Duration ttl) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, ttl);
        return result != null && result;
    }

    public void setMultiple(Map<String, Object> keyValueMap) {
        redisTemplate.opsForValue().multiSet(keyValueMap);
    }

    // ------------------------------------------
    // DELETE VALUE FUNCTIONS
    // ------------------------------------------

    public boolean deleteKey(String key) {
        Boolean result = redisTemplate.delete(key);
        return result != null && result;
    }
}
