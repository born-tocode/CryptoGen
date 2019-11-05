package com.borntocode;

import java.security.*;
import java.util.SortedMap;
import java.util.TreeMap;

class Generator {

    private SortedMap<Integer, Integer> keysize = new TreeMap<>();
    private KeyPairGenerator keyPairGen;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private KeyPair keyPair;
    private int keyLength;


    void setKeyLength(int keyLength) {
        keyLength = keysize.get(keyLength);
        this.keyLength = keyLength;
    }

    SortedMap<Integer, Integer> getKeysize() {
        return keysize;
    }

    PrivateKey getPrivateKey() {
        return privateKey;
    }

    PublicKey getPublicKey() {
        return publicKey;
    }

    void generateKeys() throws NoSuchAlgorithmException {
        keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keyLength);
        keyPair = keyPairGen.generateKeyPair();
    }

    void extractKeys() {
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }
}
