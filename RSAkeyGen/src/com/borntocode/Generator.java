package com.borntocode;

import java.security.*;

class Generator {

    private KeyPairGenerator keyPairGen;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private KeyPair keyPair;

    PrivateKey getPrivateKey() {
        return privateKey;
    }

    PublicKey getPublicKey() {
        return publicKey;
    }

    void generateKeys(int keyLength) throws NoSuchAlgorithmException {
        keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keyLength);
        keyPair = keyPairGen.generateKeyPair();
    }

    void extractKeys() {
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }
}
