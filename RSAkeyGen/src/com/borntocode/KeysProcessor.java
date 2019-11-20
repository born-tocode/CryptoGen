package com.borntocode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class KeysProcessor {

    private List<ByteArrayOutputStream> keysBuffer;
    private KeyPair keyPair;

    KeysProcessor() {
        this.keysBuffer = new ArrayList<>();
    }

    void generateKeys(int keyLength, String algorithm) throws NoSuchAlgorithmException {
        var keyPairGen = KeyPairGenerator.getInstance(algorithm);
        keyPairGen.initialize(keyLength);
        keyPair = keyPairGen.generateKeyPair();
    }

    void processKeys() throws IOException {
        var encoder = Base64.getEncoder();
        var privateKey = keyPair.getPrivate();
        var publicKey = keyPair.getPublic();

        var privateKeyEncoded = encoder.encode(privateKey.getEncoded());
        var publicKeyEncoded = encoder.encode(publicKey.getEncoded());
        keysBuffer.add(0, new ByteArrayOutputStream());
        keysBuffer.add(1, new ByteArrayOutputStream());

        keysBuffer.get(0).write(splitKeyAlgorithm(privateKeyEncoded));
        keysBuffer.get(1).write(splitKeyAlgorithm(publicKeyEncoded));
    }

    private byte[] splitKeyAlgorithm(byte[] oldKey) {
        var length = 54;
        var fullLengthOfSegment = length + 1;
        var newLine = 55;
        var position = 0;
        var count = 0;
        byte byteOfNewLine = 10;
        final int newKeyLength = oldKey.length + oldKey.length / fullLengthOfSegment;
        byte[] newKey = new byte[newKeyLength];

        for (int i = 0; i < oldKey.length; i++) {
            if (count == newLine) {
                count = 0;
                newKey[i] = byteOfNewLine;
            } else {
                count++;
                newKey[i] = oldKey[position];
                position++;
            }
        }
        return newKey;
    }

    void buildViewOfKeysToConsole(int whichKey) throws IOException {
        if (whichKey == 0) keysBuffer.get(whichKey).writeTo(new PrintStream(System.out));
        else if (whichKey == 1) keysBuffer.get(whichKey).writeTo(new PrintStream(System.out));
    }

    byte[] buildViewOfKeysToFile(int whichKey) {
        return keysBuffer.get(whichKey).toByteArray();
    }
}
