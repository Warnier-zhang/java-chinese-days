package com.chinesedays.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class JSONUtils {
    public static String stringify(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parse(String text, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(text, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parse(File file, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(file, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parse(InputStream is, Class<T> clazz) {
        try {
            // 为了获得更好的性能，ObjectMapper 应该是一个静态 final 字段并被重用。
            return new ObjectMapper().readValue(is, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON from input stream", e);
        }
    }
}
