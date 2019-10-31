package com.borntocode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

class OutBuffer implements Util {

    //fields 'txt' and 'out' are implemented from interface

    Generator generator = new Generator();
    private final Path FILE_PRV = Paths.get("privateKey.key");
    private final Path FILE_PUB = Paths.get("publicKey.pub");
    private Base64.Encoder encoder = Base64.getEncoder();

    void displayInfoAboutKeys() {
        out.println();
        out.println(txt[2].toLowerCase() + ": " + generator.getPrivateKey().getFormat());
        out.println(txt[3].toLowerCase() + ": " + generator.getPrivateKey().getAlgorithm());
        out.println();
        out.println(txt[6].toLowerCase() + ": " + generator.getPublicKey().getFormat());
        out.println(txt[7].toLowerCase() + ": " + generator.getPublicKey().getAlgorithm());
        out.print(txt[8]);
    }

    void printKeysToConsole() {
        out.print(txt[0]);
        out.print(encoder.encodeToString(generator.getPrivateKey().getEncoded()));
        out.print(txt[4]);

        out.println();
        out.println();

        out.print(txt[1]);
        out.print(encoder.encodeToString(generator.getPublicKey().getEncoded()));
        out.print(txt[5]);
        out.println();
        out.print(txt[8]);
    }

    void saveKeysToFiles() {

        try (FileOutputStream fOutPrv = new FileOutputStream(FILE_PRV.toFile());
             FileOutputStream fOutPub = new FileOutputStream(FILE_PUB.toFile())
        ) {

            fOutPrv.write(txt[0].getBytes());
            fOutPrv.write(encoder.encode(generator.getPrivateKey().getEncoded()));
            fOutPrv.write(txt[4].getBytes());

            fOutPub.write(txt[1].getBytes());
            fOutPub.write(encoder.encode(generator.getPublicKey().getEncoded()));
            fOutPub.write(txt[5].getBytes());

            out.println("Keys are saved in program directory.");
            out.println();
            out.println();
        } catch (IOException e) {
            out.println("Input/output error");
            out.println(e.getMessage());
        }
    }

    @Override
    public void close()
    {

    }
}
