package com.borntocode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.borntocode.Generator.keyPair;

class KeysProcessor {

    private List<ByteArrayOutputStream> keysBuffer;

    KeysProcessor() throws IOException {
        this.keysBuffer = new ArrayList<>();
        processKeys();
    }

    private void processKeys() throws IOException {
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
        var rangeFrom = 0;
        var rangeTo = 54;
        var finalSegment = 54;
        var splitStream = 55;
        var distance = oldKey.length / splitStream;
        var newByteLength = oldKey.length + oldKey.length / splitStream;
        byte[] newKey = new byte[newByteLength];
        byte[] range;
























        //        for (int i = 0; i < distance + 1; i++) {
//            range = Arrays.copyOfRange(oldKey, rangeFrom, rangeTo);
//            newKey = Arrays.copyOf(range, newByteLength);
//            newKey[rangeTo + 1] = '\n';
//            newKey[rangeTo + 2] = oldKey[rangeTo + 1];
//            rangeFrom = rangeTo + 2;
//            rangeTo += finalSegment;
//            System.out.println("i = " + i);
//            System.out.println("newKey.length = " + newKey.length);
//            System.out.println("oldKey.length = " + oldKey.length);
//            System.out.println("rangeTo = " + rangeTo);
//            System.out.println("rangeFrom = " + rangeFrom);
//
//            if (rangeTo >= oldKey.length) break;
//        }

        return newKey;
    }

    void buildViewOfKeys(int whichKey) throws IOException {
        if (whichKey == 0) keysBuffer.get(whichKey).writeTo(new PrintStream(System.out));
        else if (whichKey == 1) keysBuffer.get(whichKey).writeTo(new PrintStream(System.out));
    }
}
