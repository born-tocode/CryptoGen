package com.borntocode;

import java.security.*;
import java.util.SortedMap;
import java.util.TreeMap;

class Generator {

    private SortedMap<Integer, Integer> keysize = new TreeMap<>();
    private KeyPairGenerator keyPairGen;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private int keyLength;

    Generator() {
        fillMapOfBits();
    }

    void setKeyLength(int keyLength) {
        keyLength = keysize.get(keyLength);
        this.keyLength = keyLength;
        generateKeys();
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

    private void fillMapOfBits() {
        keysize.put(0, 1024);
        keysize.put(1, 2048);
        keysize.put(2, 4096);
        keysize.put(3, 8192);
        keysize.put(4, 12288);
        keysize.put(5, 16384);
    }

    private void generateKeys() {
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm");
        }
        keyPairGen.initialize(keyLength);

        KeyPair keyPair = keyPairGen.generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }


}
