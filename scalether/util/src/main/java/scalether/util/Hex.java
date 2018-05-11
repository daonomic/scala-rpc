package scalether.util;

import java.math.BigInteger;

public class Hex {
    private Hex() {
    }

    private final static char[] hexArray = "0123456789abcdef".toCharArray();

    public static String prefixed(BigInteger bigInteger) {
        return "0x" + bigInteger.toString(16);
    }

    public static String prefixed(byte[] bytes) {
        char[] hexChars = new char[2 + bytes.length * 2];
        hexChars[0] = '0';
        hexChars[1] = 'x';
        putHex(hexChars, bytes, 2);
        return new String(hexChars);
    }

    public static String to(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        putHex(hexChars, bytes, 0);
        return new String(hexChars);
    }

    private static void putHex(char[] hexChars, byte[] bytes, int start) {
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[start + j * 2] = hexArray[v >>> 4];
            hexChars[start + j * 2 + 1] = hexArray[v & 0x0F];
        }
    }

    public static byte[] toBytes(String s) {
        if (s == null) {
            return new byte[0];
        }
        if (s.startsWith("0x")) {
            s = s.substring(2);
        }
        if (s.equals("0")) {
            return new byte[0];
        }
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
