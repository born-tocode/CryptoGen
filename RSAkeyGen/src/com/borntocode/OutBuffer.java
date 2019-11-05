package com.borntocode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

class OutBuffer {

    private Generator generator = new Generator();
    private final Path FILE_PRV = Paths.get("privateKey.key");
    private final Path FILE_PUB = Paths.get("publicKey.pub");
    private Base64.Encoder encoder = Base64.getEncoder();

//    void saveKeysToFiles() {
//
//        try (FileOutputStream fOutPrv = new FileOutputStream(FILE_PRV.toFile());
//             FileOutputStream fOutPub = new FileOutputStream(FILE_PUB.toFile())
//        ) {
//
//            fOutPrv.write(txt[0].getBytes());
//            fOutPrv.write(encoder.encode(generator.getPrivateKey().getEncoded()));
//            fOutPrv.write(txt[4].getBytes());
//
//            fOutPub.write(txt[1].getBytes());
//            fOutPub.write(encoder.encode(generator.getPublicKey().getEncoded()));
//            fOutPub.write(txt[5].getBytes());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
