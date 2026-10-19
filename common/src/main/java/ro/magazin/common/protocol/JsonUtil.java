package ro.magazin.common.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

public final class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private JsonUtil() {
    }

    public static String toJson(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Obiectul nu poate fi convertit in JSON", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> tip) {
        try {
            return MAPPER.readValue(json, tip);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("JSON invalid: " + json, e);
        }
    }

    public static JsonNode toTree(Object o) {
        return o == null ? null : MAPPER.valueToTree(o);
    }

    public static <T> T convert(JsonNode node, Class<T> tip) {
        return node == null ? null : MAPPER.convertValue(node, tip);
    }

    public static <T> List<T> convertList(JsonNode node, Class<T> tipElement) {
        if (node == null) {
            return List.of();
        }
        return MAPPER.convertValue(node,
                MAPPER.getTypeFactory().constructCollectionType(List.class, tipElement));
    }
}
