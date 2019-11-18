package com.borntocode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

class KeysProcessor {

    private List<ByteArrayOutputStream> keysBuffer;
    private KeyPair keyPair;

    KeysProcessor() {
        this.keysBuffer = new ArrayList<>();
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    void generateKeys(int keyLength) throws NoSuchAlgorithmException {
        var keyPairGen = KeyPairGenerator.getInstance("RSA");
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
        final int newLine = 55;
        final int length = 55;
        final int lastComplementaryLoop = 1;
        final int newKeyLength = oldKey.length + oldKey.length / newLine;
        final int numbOfLoops = newKeyLength / newLine + lastComplementaryLoop;
        var dstPos = 0;
        var srcPos = 0;
        var range = 55;
        byte[] newKey = new byte[newKeyLength];

        for (int i = 0; i < numbOfLoops; i++) {
            if (range < oldKey.length) {
                System.arraycopy(oldKey, srcPos, newKey, dstPos, length);
                newKey[range] = '\n';
                srcPos = range;
                dstPos = range + 1;
                range += newLine;
            } else {
                //lastComplementaryLoop
                System.arraycopy(oldKey, srcPos, newKey, dstPos, oldKey.length - srcPos);
            }
        }
        return newKey;
    }

    void buildViewOfKeys(int whichKey) throws IOException {
        if (whichKey == 0) keysBuffer.get(whichKey).writeTo(new PrintStream(System.out));
        else if (whichKey == 1) keysBuffer.get(whichKey).writeTo(new PrintStream(System.out));
    }
}
