package com.borntocode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

class OutBuffer {

    private RSAkeyGen rsaKeyGen = new RSAkeyGen();
    private Generator generator = new Generator();
    private final Path FILE_PRV = Paths.get("privateKey.key");
    private final Path FILE_PUB = Paths.get("publicKey.pub");
    private Base64.Encoder encoder = Base64.getEncoder();

    void saveKeysToFiles() {

        try (FileOutputStream fOutPrv = new FileOutputStream(FILE_PRV.toFile());
             FileOutputStream fOutPub = new FileOutputStream(FILE_PUB.toFile())
        ) {

            fOutPrv.write(rsaKeyGen.messages.getString("begin.rsa.private.key").getBytes());
            fOutPrv.write(encoder.encode(generator.getPrivateKey().getEncoded()));
            fOutPrv.write(rsaKeyGen.messages.getString("end.rsa.private.key").getBytes());

            fOutPub.write(rsaKeyGen.messages.getString("begin.rsa.public.key").getBytes());
            fOutPub.write(encoder.encode(generator.getPublicKey().getEncoded()));
            fOutPub.write(rsaKeyGen.messages.getString("end.rsa.public.key").getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
