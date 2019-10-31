package com.borntocode;

import java.security.*;
import java.util.SortedMap;
import java.util.TreeMap;

class Generator {

    private SortedMap<Integer, Integer> keysize = new TreeMap<>();
    private KeyPairGenerator keyPairGen;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public SortedMap<Integer, Integer> getKeysize() {
        return keysize;
    }

    PrivateKey getPrivateKey() {
        return privateKey;
    }

    PublicKey getPublicKey() {
        return publicKey;
    }

    private void prepareToGenerate() {
        keysize.put(0, 1024);
        keysize.put(1, 2048);
        keysize.put(2, 4096);
        keysize.put(3, 8192);
        keysize.put(4, 12288);
        keysize.put(5, 16384);
    }

    private void generator(int keyLength) {
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No such algorithm");
        }

        keyPairGen.initialize(keyLength);

        KeyPair keyPair = keyPairGen.generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }



}
