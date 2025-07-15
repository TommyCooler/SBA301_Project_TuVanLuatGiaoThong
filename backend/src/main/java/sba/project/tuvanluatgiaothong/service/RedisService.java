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

    private final RedisTemplate<String, String> redisTemplate; // ✅ String template
    private final ObjectMapper objectMapper;

    // ------------------------------------------
    // GET VALUE FUNCTIONS
    // ------------------------------------------

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public String getValueByKey(String key) { // ✅ Return String, not Object
        return redisTemplate.opsForValue().get(key);
    }

    public String getValueIfExists(String key) { // ✅ Return String, not Object
        return hasKey(key) ? getValueByKey(key) : null;
    }

    // ✅ New method for JSON deserialization
    public <T> T getJsonValue(String key, Class<T> clazz) {
        try {
            String jsonValue = getValueByKey(key);
            if (jsonValue != null) {
                return objectMapper.readValue(jsonValue, clazz);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getTypedValue(String key, Class<T> clazz) {
        try {
            String jsonValue = getValueByKey(key);
            if (jsonValue != null) {
                if (clazz == String.class) {
                    return (T) jsonValue;
                }
                return objectMapper.readValue(jsonValue, clazz);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // ✅ Generic method with proper handling
    @SuppressWarnings("unchecked")
    public <T> T getTypedValue(String key) {
        String jsonValue = getValueByKey(key);
        try {
            return (T) jsonValue; // Return as String, let caller handle conversion
        } catch (ClassCastException e) {
            return null;
        }
    }

    // ------------------------------------------
    // SET VALUE FUNCTIONS
    // ------------------------------------------

    // ✅ String values only
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, String value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public void setValue(String key, String value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(timeoutSeconds));
    }

    // ✅ Object serialization methods
    public void setJsonValue(String key, Object value) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, jsonValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize value", e);
        }
    }

    public void setJsonValue(String key, Object value, Duration ttl) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, jsonValue, ttl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize value", e);
        }
    }

    public void setJsonValue(String key, Object value, long timeoutSeconds) {
        setJsonValue(key, value, Duration.ofSeconds(timeoutSeconds));
    }

    // ✅ String-based conditional methods
    public boolean setIfAbsent(String key, String value) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
        return result != null ? result : false;
    }

    public boolean setIfAbsent(String key, String value, Duration ttl) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, ttl);
        return result != null ? result : false;
    }

    // ✅ JSON-based conditional methods
    public boolean setJsonIfAbsent(String key, Object value) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            return setIfAbsent(key, jsonValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize value", e);
        }
    }

    public boolean setJsonIfAbsent(String key, Object value, Duration ttl) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            return setIfAbsent(key, jsonValue, ttl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize value", e);
        }
    }

    // ✅ Multiple set with JSON serialization
    public void setMultiple(Map<String, Object> keyValueMap) {
        try {
            Map<String, String> jsonMap = keyValueMap.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> {
                        try {
                            return objectMapper.writeValueAsString(entry.getValue());
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to serialize value for key: " + entry.getKey(), e);
                        }
                    }
                ));
            redisTemplate.opsForValue().multiSet(jsonMap);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize values", e);
        }
    }

    // ✅ String-based multiple set
    public void setMultipleStrings(Map<String, String> keyValueMap) {
        redisTemplate.opsForValue().multiSet(keyValueMap);
    }

    // ------------------------------------------
    // DELETE VALUE FUNCTIONS
    // ------------------------------------------

    public boolean deleteKey(String key) {
        Boolean result = redisTemplate.delete(key);
        return result != null ? result : false;
    }
}
