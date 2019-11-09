package com.borntocode;

import java.security.*;

class Generator {

    KeyPair generateKeys(int keyLength) throws NoSuchAlgorithmException {
        var keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keyLength);
        var keyPair = keyPairGen.generateKeyPair();
        return keyPair;
    }
}
