package com.borntocode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Locale;
import java.util.ResourceBundle;

class OutBuffer {

    private Generator generator = new Generator();
    private final Path FILE_PRV = Paths.get("privateKey.key");
    private final Path FILE_PUB = Paths.get("publicKey.pub");
    private Base64.Encoder encoder = Base64.getEncoder();
    private Locale currentLocale = new Locale("en", "US");
    private ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

    void saveKeysToFiles() {

        try (FileOutputStream fOutPrv = new FileOutputStream(FILE_PRV.toFile());
             FileOutputStream fOutPub = new FileOutputStream(FILE_PUB.toFile())
        ) {

            fOutPrv.write(messages.getString("begin.rsa.private.key").getBytes());
            fOutPrv.write(encoder.encode(generator.getPrivateKey().getEncoded()));
            fOutPrv.write(messages.getString("end.rsa.private.key").getBytes());

            fOutPub.write(messages.getString("begin.rsa.public.key").getBytes());
            fOutPub.write(encoder.encode(generator.getPublicKey().getEncoded()));
            fOutPub.write(messages.getString("end.rsa.public.key").getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
