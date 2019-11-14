package com.borntocode;

import java.io.IOException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

class Generator {

    void generateKeysAndProcess(int keyLength) throws NoSuchAlgorithmException, IOException {
        var keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keyLength);
        new KeysProcessor().processKeys(keyPairGen.generateKeyPair());
    }
}
