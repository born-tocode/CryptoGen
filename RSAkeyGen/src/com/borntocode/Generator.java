package com.borntocode;

import java.security.*;

class Generator {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private KeyPair keyPair;


    PrivateKey getPrivateKey() {
        return privateKey;
    }

    PublicKey getPublicKey() {
        return publicKey;
    }

    KeyPair generateKeys(int keyLength) throws NoSuchAlgorithmException {
        var keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keyLength);
        keyPair = keyPairGen.generateKeyPair();
        return keyPair;
    }

    void extractKeys() {
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }
}
