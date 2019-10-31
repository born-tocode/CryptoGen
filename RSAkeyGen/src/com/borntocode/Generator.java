package com.borntocode;

import java.security.*;
import java.util.SortedMap;
import java.util.TreeMap;

class Generator {

    private SortedMap<Integer, Integer> KEYSIZE = new TreeMap<>();
    private KeyPairGenerator keyPairGen;
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;


    private void prepareToGenerate() {
        KEYSIZE.put(0, 1024);
        KEYSIZE.put(1, 2048);
        KEYSIZE.put(2, 4096);
        KEYSIZE.put(3, 8192);
        KEYSIZE.put(4, 12288);
        KEYSIZE.put(5, 16384);
    }

    private void generator(int keyLenght) {
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No such algorithm");
        }

        keyPairGen.initialize(keyLenght);

        keyPair = keyPairGen.generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }



}
