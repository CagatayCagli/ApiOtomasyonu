package com.trello.api.utils;

import java.util.List;
import java.util.Random;

/**
 * Utility class for random operations
 * Provides methods for random selection and generation
 */
public class RandomUtils {
    
    private static final Random random = new Random();
    
    /**
     * Selects a random element from a list
     * @param list The list to select from
     * @param <T> The type of elements in the list
     * @return A random element from the list, or null if list is empty
     */
    public static <T> T getRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(random.nextInt(list.size()));
    }
    
    /**
     * Generates a random integer between min and max (inclusive)
     * @param min Minimum value
     * @param max Maximum value
     * @return Random integer between min and max
     */
    public static int getRandomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    
    /**
     * Generates a random boolean value
     * @return Random boolean
     */
    public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }
    
    /**
     * Generates a random string with specified length
     * @param length Length of the random string
     * @return Random string
     */
    public static String getRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
