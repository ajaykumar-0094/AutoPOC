package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ApiUtil {

    public static String getRandomUser() {
        Map<String, Object> user = new HashMap<>();
        String randomName = "TestUser_" + UUID.randomUUID().toString().substring(0, 5);
        String randomEmail = randomName.toLowerCase() + "@example.com";

        user.put("name", randomName);
        user.put("email", randomEmail);
        user.put("gender", "male");
        user.put("status", "active");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert user map to JSON", e);
        }
    }
    
    public static Map<String, Object> toMap(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
    }
}
