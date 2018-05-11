package org.web3j.crypto;

import org.web3j.utils.Numeric;
import org.web3j.utils.Strings;

import java.math.BigInteger;

public class Keys {
    static final int PRIVATE_KEY_SIZE = 32;
    static final int PUBLIC_KEY_SIZE = 64;

    public static final int ADDRESS_LENGTH_IN_HEX = 40;
    static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;
    public static final int PRIVATE_KEY_LENGTH_IN_HEX = PRIVATE_KEY_SIZE << 1;

    public static String getAddressFromPrivateKey(BigInteger privateKey) {
        final BigInteger publicKey = Sign.publicKeyFromPrivate(privateKey);
        return getAddress(publicKey);
    }

    public static String getAddress(BigInteger publicKey) {
        return getAddress(
            Numeric.toHexStringWithPrefixZeroPadded(publicKey, PUBLIC_KEY_LENGTH_IN_HEX));
    }

    public static String getAddress(String publicKey) {
        String publicKeyNoPrefix = Numeric.cleanHexPrefix(publicKey);

        if (publicKeyNoPrefix.length() < PUBLIC_KEY_LENGTH_IN_HEX) {
            publicKeyNoPrefix = Strings.zeros(
                PUBLIC_KEY_LENGTH_IN_HEX - publicKeyNoPrefix.length())
                + publicKeyNoPrefix;
        }
        String hash = Hash.sha3(publicKeyNoPrefix);
        return hash.substring(hash.length() - ADDRESS_LENGTH_IN_HEX);  // right most 160 bits
    }
}
