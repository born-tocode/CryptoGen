package com.borntocode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

class OutBuffer implements Util {

    private Base64.Encoder encoder = Base64.getEncoder();
    private final Path FILE_PRV = Paths.get("privateKey.key");
    private final Path FILE_PUB = Paths.get("publicKey.pub");

    private void saveKeysToFiles() {

        try (FileOutputStream fOutPrv = new FileOutputStream(FILE_PRV.toFile());
             FileOutputStream fOutPub = new FileOutputStream(FILE_PUB.toFile())) {

            fOutPrv.write(txt[0].getBytes());
            fOutPrv.write(encoder.encode(privateKey.getEncoded()));
            fOutPrv.write(txt[4].getBytes());

            out.println();
            out.println();

            fOutPub.write(txt[1].getBytes());
            fOutPub.write(encoder.encode(publicKey.getEncoded()));
            fOutPub.write(txt[5].getBytes());

            out.println("Keys are saved in program directory.");
            out.println();
            out.println();
        } catch (IOException e) {
            out.println("Input/output error");
            out.println(e.getMessage());
        }
    }
}
