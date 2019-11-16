package com.borntocode;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

class Generator {

    static KeyPair keyPair;

    void generateKeys(int keyLength) throws NoSuchAlgorithmException {
        var keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keyLength);
        keyPair = keyPairGen.generateKeyPair();
    }
}
