package com.trello.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ApiCredentials {
    
    private static final String PROPERTIES_FILE = "api.properties";
    private static String apiKey;
    private static String apiToken;
    
    static {
        loadCredentials();
    }
    

    private static void loadCredentials() {
        Properties properties = new Properties();
        try (InputStream input = ApiCredentials.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                System.err.println("Unable to find " + PROPERTIES_FILE + " file. Please create it in src/test/resources/");
                return;
            }
            
            properties.load(input);
            apiKey = properties.getProperty("trello.api.key");
            apiToken = properties.getProperty("trello.api.token");
            
            if (apiKey == null || apiToken == null) {
                System.err.println("API key or token not found in properties file");
            }
            
        } catch (IOException e) {
            System.err.println("Error loading API credentials: " + e.getMessage());
        }
    }
    

    public static String getApiKey() {
        return apiKey;
    }

    public static String getApiToken() {
        return apiToken;
    }

    public static boolean areCredentialsLoaded() {
        return apiKey != null && !apiKey.isEmpty() && 
               apiToken != null && !apiToken.isEmpty();
    }
}
