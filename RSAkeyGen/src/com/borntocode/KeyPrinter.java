package com.borntocode;

import java.security.KeyPair;
import java.util.Base64;

class KeyPrinter {

    String showKeys(KeyPair keyPair) {
        var encoder = Base64.getEncoder();
        var privateKey = keyPair.getPrivate();
        var publicKey = keyPair.getPublic();

        var privateKeyEncoded = encoder.encodeToString(privateKey.getEncoded());
        var publicKeyEncoded = encoder.encodeToString(publicKey.getEncoded());
    }


}
