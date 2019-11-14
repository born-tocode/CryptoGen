package com.borntocode;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class OutBuffer {

    private final String[] PRV_PUB_TXT;
    private List<FileOutputStream> listStreams;

    OutBuffer() throws FileNotFoundException {
        Path filePrv = Paths.get("privateKey.key");
        Path filePub = Paths.get("publicKey.pub");
        this.PRV_PUB_TXT = new String[]{
                "-----BEGIN RSA PRIVATE KEY-----", "-----END RSA PRIVATE KEY-----",
                "-----BEGIN RSA PUBLIC KEY-----", "-----END RSA PUBLIC KEY-----"
        };
        this.listStreams = new ArrayList<>();
        listStreams.add(0, new FileOutputStream(filePrv.toFile()));
        listStreams.add(1, new FileOutputStream(filePub.toFile()));
    }


    void saveKeysToFiles(List<ByteBuffer> keysBuffer) {

        var nextString = 0;

        for (int i = 0; i < 2; i++) {
            try (var out = listStreams.get(i)) {
                out.write(PRV_PUB_TXT[nextString].getBytes());
                nextString++;
                out.write('\n');


                //todo put your code with keys

                out.write('\n');
                out.write(PRV_PUB_TXT[nextString].getBytes());

                if (i == 1) nextString++;

            } catch (FileNotFoundException e) {
                System.out.println("Can't find files");
            } catch (IOException e) {
                System.out.println("I/O exception");
            }
        }
    }
}