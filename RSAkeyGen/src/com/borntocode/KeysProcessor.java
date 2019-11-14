package com.borntocode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

class KeysProcessor {

    private List<ByteArrayOutputStream> keysBuffer = new ArrayList<>();

    void processKeys(KeyPair keyPair) throws IOException {

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

    private byte[] splitKeyAlgorithm(byte[] key) {
        var splitStream = 55;
        for (int i = 0; i < key.length; i++) {
            var x = 0;
            x++;

            if (x == splitStream) {
                key[i] = '\n';
                x = 0;
            }
        }
        return key;
    }

    void buildViewOfKeys(int whichKey) throws IOException {
        if (whichKey == 0) keysBuffer.get(whichKey).writeTo(new PrintStream(System.out));
        else if (whichKey == 1) keysBuffer.get(whichKey).writeTo(new PrintStream(System.out));
    }
}
