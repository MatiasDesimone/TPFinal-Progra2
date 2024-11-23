package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    public static String toJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T fromJsonFile(File file, Class<T> clazz) throws IOException {
        return objectMapper.readValue(file, clazz);
    }

    public static void toJsonFile(File file, Object obj) throws IOException {
        objectMapper.writeValue(file, obj);
    }
}
