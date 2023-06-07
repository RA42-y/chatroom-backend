package com.ra.chatapplication.utils;

import java.security.SecureRandom;

/**
 * Utility class for password operations.
 */
public class PasswordUtils {

    /**
     * Generates a random password with the specified length.
     * @param length The length of the password
     * @return The randomly generated password
     */
    public static String generateRandomPassword(int length) {
        final String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(index));
        }

        return password.toString();
    }

}
