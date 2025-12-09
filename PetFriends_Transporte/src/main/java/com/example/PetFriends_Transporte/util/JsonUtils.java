package com.example.PetFriends_Transporte.util;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desserializar JSON para " + clazz.getSimpleName(), e);
        }
    }
}