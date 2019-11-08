package com.borntocode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

class OutBuffer {

    private final Generator generator = new Generator();
    private final Path FILE_PRV = Paths.get("privateKey.key");
    private final Path FILE_PUB = Paths.get("publicKey.pub");
    private final Base64.Encoder encoder = Base64.getEncoder();

    void saveKeysToFiles() {

        try (FileOutputStream fOutPrv = new FileOutputStream(FILE_PRV.toFile());
             FileOutputStream fOutPub = new FileOutputStream(FILE_PUB.toFile())
        ) {

            fOutPrv.write("-----BEGIN RSA PRIVATE KEY-----".getBytes());
            fOutPrv.write(encoder.encode(generator.getPrivateKey().getEncoded()));
            fOutPrv.write("-----END RSA PRIVATE KEY-----".getBytes());

            fOutPub.write("-----BEGIN RSA PUBLIC KEY-----".getBytes());
            fOutPub.write(encoder.encode(generator.getPublicKey().getEncoded()));
            fOutPub.write("-----END RSA PUBLIC KEY-----".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
