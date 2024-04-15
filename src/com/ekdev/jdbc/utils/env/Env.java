package com.ekdev.jdbc.utils.env;

import java.io.IOException;
import java.util.Properties;

public class Env {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private Env() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (var inputStream = Env.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
