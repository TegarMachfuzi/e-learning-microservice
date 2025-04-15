package com.learning.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.sql.rowset.serial.SerialClob;
import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Pattern;

public class AppUtils {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime !=null ? localDateTime.format(DATE_TIME_FORMATTER) : null;
    }

    public static LocalDateTime parseLocalDateTime(String localDateTimeStr) {
        return localDateTimeStr !=null ? LocalDateTime.parse(localDateTimeStr, DATE_TIME_FORMATTER) : null;
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Asia/Jakarta"));
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return localDateTime !=null ? localDateTime.atZone(ZoneId.of("Asia/Jakarta")).toInstant().toEpochMilli() : 0;
    }

    public static LocalDateTime formatEpochMilli(long epochMilli) {
        return Instant.ofEpochMilli(epochMilli).atZone(ZoneId.of("Asia/Jakarta")).toLocalDateTime();
    }


    // =======String / Validation ========

    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isNumeric(String input) {
        return input != null && input.matches("\\d+");
    }

    public static boolean isValidEmail(String email){
        if (email == null || email.isEmpty()) return false;
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    // ===== JSON Utils =====

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        }catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        }catch (Exception e) {
            return null;
        }
    }

    // ===== CLOB HANDLER =====

    public static String clobToString(Clob clob) {
        if (clob == null) return null;
        try(Reader reader = clob.getCharacterStream(); BufferedReader br = new BufferedReader(reader)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            return sb.toString();
        }catch (Exception e) {
            return null;
        }
    }

    public static Clob stringToClob(String clob) {
        try {
            return new SerialClob(clob.toCharArray());
        }catch (SQLException e) {
            return null;
        }
    }

    // ===== CORELATION ID =====

    public static String generateCorelationID() {
        return "X-CORR-" + UUID.randomUUID().toString();
    }

    // ===== RETRY DELAY =====

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ===== ENV / PROFILE =====

    public static boolean isDevProfile(String activeProfile) {
        return "dev".equals(activeProfile);
    }

    public static boolean isProdProfile(String activeProfile) {
        return "prod".equals(activeProfile);
    }

    public static void logInfo(String title, Object data) {
        System.out.println("[INFO] " + title + ": " + toJson(data));
    }

    public static void logError(String title, Object data) {
        System.out.println("[ERROR] " + title + ": " + toJson(data));
    }
}
