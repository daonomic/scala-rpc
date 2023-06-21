package org.web3j.utils;

import java.util.List;

/**
 * String utility functions.
 */
public class Strings {

    private Strings() {}

    public static String toCsv(List<String> src) {
        return join(src, ", ");
    }

    public static String join(List<String> src, String delimiter) {
        String result = "";
        for (int i = 0; i < src.size(); i++) {
            result += src.get(i);
            if (i + 1 < src.size()) {
                result += delimiter;
            }
        }
        return result;
    }

    public static String capitaliseFirstLetter(String string) {
        if (string == null || string.length() == 0) {
            return string;
        } else {
            return string.substring(0, 1).toUpperCase() + string.substring(1);
        }
    }

    public static String lowercaseFirstLetter(String string) {
        if (string == null || string.length() == 0) {
            return string;
        } else {
            return string.substring(0, 1).toLowerCase() + string.substring(1);
        }
    }

    public static String zeros(int n) {
        return repeat('0', n);
    }

    public static String repeat(char value, int n) {
        return new String(new char[n]).replace("\0", String.valueOf(value));
    }

    /**
     * Returns true if the string is empty, otherwise false.
     *
     * @param s String value
     * @return is given string is Empty or not
     */
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Returns true if the string is empty or contains only white space codepoints, otherwise false.
     *
     * @param s String value
     * @return is given string is Blank or not
     */
    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
}
