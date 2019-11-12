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
        Path FILE_PRV = Paths.get("privateKey.key");
        Path FILE_PUB = Paths.get("publicKey.pub");
        this.PRV_PUB_TXT = new String[]{
                "-----BEGIN RSA PRIVATE KEY-----", "-----END RSA PRIVATE KEY-----",
                "-----BEGIN RSA PUBLIC KEY-----", "-----END RSA PUBLIC KEY-----"
        };
        this.listStreams = new ArrayList<>();
        listStreams.add(0, new FileOutputStream(FILE_PRV.toFile()));
        listStreams.add(1, new FileOutputStream(FILE_PUB.toFile()));
    }


    void saveKeysToFiles(List<ByteBuffer> keysBuffer) {

        var splitStream = 55;
        var nextString = 0;
        var nextStream = 0;

        for (ByteBuffer keys : keysBuffer) {
            try (var out = listStreams.get(nextStream)) {
                out.write(PRV_PUB_TXT[nextString].getBytes());
                nextString++;
                out.write('\n');

                for (int i = 0, x = 0; i < keys.array().length; i++, x++) {
                    if (x == splitStream) {
                        out.write('\n');
                        x = 0;
                    }
                    out.write(keys.get(i));
                }
                out.write('\n');
                out.write(PRV_PUB_TXT[nextString].getBytes());

                if (nextString == 1) nextString++;

                if (nextStream == 0) nextStream++;
            } catch (FileNotFoundException e) {
                System.out.println("Can't save the keys");
            } catch (IOException e) {
                System.out.println("I/O exception");
            }
        }
    }
}