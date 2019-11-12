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

    private final Path FILE_PRV = Paths.get("privateKey.key");
    private final Path FILE_PUB = Paths.get("publicKey.pub");

    void saveKeysToFiles(List<ByteBuffer> keysBuffer) throws IOException {

        final String[] prvPub = {
                "-----BEGIN RSA PRIVATE KEY-----", "-----END RSA PRIVATE KEY-----",
                "-----BEGIN RSA PUBLIC KEY-----", "-----END RSA PUBLIC KEY-----"
        };
        var listStreams = new ArrayList<FileOutputStream>();
        listStreams.add(0, new FileOutputStream(FILE_PRV.toFile()));
        listStreams.add(1, new FileOutputStream(FILE_PUB.toFile()));
        var splitStream = 50;
        var indexForString = 0;
        var indexForStream = 0;
        var out = listStreams.get(indexForStream);




        try {
            for (ByteBuffer keys : keysBuffer) {
                out.write(prvPub[indexForString].getBytes());
                indexForString++;
                out.write('\n');

                for (int i = 0, x = 0; i < keys.array().length; i++, x++) {
                    if (x == splitStream) {
                        out.write('\n');
                        x = 0;
                    }
                    out.write(keys.get(i));
                }
                out.write('\n');
                out.write(prvPub[indexForString].getBytes());

                if (indexForString == 1) indexForString++;

                if (indexForStream == 0) indexForStream++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Can't save the keys");
        } catch (IOException e) {
            System.out.println("I/O exception");
        } finally {
            out.close();
            out.close();
        }
    }
}
