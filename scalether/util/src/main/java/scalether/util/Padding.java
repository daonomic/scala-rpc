package scalether.util;

public class Padding {
    private Padding() {
    }

    private static final int SIZE = 32;

    public static int getLength(int originalLength) {
        final int modulo = originalLength % SIZE;
        if (modulo == 0) {
            return originalLength;
        } else {
            return (1 + originalLength / SIZE) * SIZE;
        }
    }

    public static byte[] padRight(byte[] bytes, byte fill) {
        final int length = getLength(bytes.length);
        if (bytes.length == length) {
            return bytes;
        } else {
            final byte[] result = new byte[length];
            System.arraycopy(bytes, 0, result, 0, bytes.length);
            if (fill != Bytes.ZERO) {
                Bytes.fill(result, fill, bytes.length, length);
            }
            return result;
        }
    }

    public static byte[] padLeft(byte[] bytes, byte fill) {
        final int length = getLength(bytes.length);
        if (bytes.length == length) {
            return bytes;
        } else {
            final byte[] result = new byte[length];
            System.arraycopy(bytes, 0, result, length - bytes.length, bytes.length);
            if (fill != Bytes.ZERO) {
                Bytes.fill(result, fill, 0, length - bytes.length);
            }
            return result;
        }
    }
}
