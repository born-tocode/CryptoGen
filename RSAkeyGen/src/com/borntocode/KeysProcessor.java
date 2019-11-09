package com.borntocode;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.util.*;

class KeysProcessor {

    private List<ByteBuffer> listWithKeys = new ArrayList<>();

    void processKeys(KeyPair keyPair) throws IOException {
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
    }

    byte getPrivateKeyStream() {
        return listWithKeys.get(0).get();
    }

    byte getPublicKeyStream() {
        return listWithKeys.get(1).get();
    }


}
