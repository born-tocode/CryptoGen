package com.borntocode;

import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

class KeysProcessor {

    private List<ByteBuffer> listWithKeys = new ArrayList<>();

    List<ByteBuffer> processKeys(KeyPair keyPair) {
        var encoder = Base64.getEncoder();
        var privateKey = keyPair.getPrivate();
        var publicKey = keyPair.getPublic();

        var privateKeyEncoded = encoder.encode(privateKey.getEncoded());
        var publicKeyEncoded = encoder.encode(publicKey.getEncoded());
        var privateKeyStream = ByteBuffer.wrap(privateKeyEncoded);
        var publicKeyStream = ByteBuffer.wrap(publicKeyEncoded);

        privateKeyStream.rewind();
        publicKeyStream.rewind();

        listWithKeys.add(0, privateKeyStream);
        listWithKeys.add(1, publicKeyStream);

        return listWithKeys;
    }
}
