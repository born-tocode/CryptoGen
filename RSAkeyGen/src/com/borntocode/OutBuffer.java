package com.borntocode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

class OutBuffer {

    private final Path FILE_PRV = Paths.get("privateKey.key");
    private final Path FILE_PUB = Paths.get("publicKey.pub");

    void saveKeysToFiles(ByteBuffer privateKey, ByteBuffer publicKey) {

        try (FileOutputStream fOutPrv = new FileOutputStream(FILE_PRV.toFile());
             FileOutputStream fOutPub = new FileOutputStream(FILE_PUB.toFile())
        ) {

            fOutPrv.write("-----BEGIN RSA PRIVATE KEY-----".getBytes());
            fOutPrv.write(privateKey.get());
            fOutPrv.write("-----END RSA PRIVATE KEY-----".getBytes());

            fOutPub.write("-----BEGIN RSA PUBLIC KEY-----".getBytes());
            fOutPub.write(publicKey.get());
            fOutPub.write("-----END RSA PUBLIC KEY-----".getBytes());

        } catch (IOException e) {
            System.out.println("I/O exception");
        }
    }
}
