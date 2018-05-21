package com.singham.yuan.ws.test.common.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import java.io.IOException;

public class JsonMapperUtil {

    private static ObjectMapper mapper = getMapper();

    private JsonMapperUtil() {
        throw new IllegalAccessError("Utility Class");
    }

    public static String toJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static <T> T parseToObject(String jsonString, Class<T> objectType) {
        try {
            return mapper.readValue(jsonString, objectType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(new VisibilityChecker.Std(
                JsonAutoDetect.Visibility.NONE,
                JsonAutoDetect.Visibility.NONE,
                JsonAutoDetect.Visibility.NONE,
                JsonAutoDetect.Visibility.NONE,
                JsonAutoDetect.Visibility.ANY));
        return objectMapper;
    }

}
