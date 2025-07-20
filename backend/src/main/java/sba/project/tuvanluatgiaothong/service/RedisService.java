package sba.project.tuvanluatgiaothong.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    // ------------------------------------------
    // GET VALUE FUNCTIONS
    // ------------------------------------------

    public boolean hasKey(String key) {
        Boolean exists = redisTemplate.hasKey(key);
        return exists != null && exists;
    }

    public Object getValueByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object getValueIfExists(String key) {
        return hasKey(key) ? getValueByKey(key) : null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getTypedValue(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return (T) value;
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
        Boolean removed = redisTemplate.delete(key);
        return removed != null && removed;
    }
}
