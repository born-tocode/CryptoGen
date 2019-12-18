package com.borntocode;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

class OutBuffer {

    private KeysProcessor keysProcessor;
    private final String[] PRV_PUB_TXT;
    private List<FileOutputStream> listStreams;

    OutBuffer() throws FileNotFoundException {
        Path filePrv = Paths.get("privateKey.key");
        Path filePub = Paths.get("publicKey.pub");
        this.PRV_PUB_TXT = new String[]{
                "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----",
                "-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----"
        };
        this.listStreams = new ArrayList<>();
        listStreams.add(0, new FileOutputStream(filePrv.toFile()));
        listStreams.add(1, new FileOutputStream(filePub.toFile()));
    }

    public void saveKeysToFiles(String algorithmName, int keySize) throws IOException {

        var nextString = 0;
        var numberOfFiles = 2;

        for (int i = 0; i < numberOfFiles; i++) {
            try (var out = listStreams.get(i)) {
                out.write(PRV_PUB_TXT[nextString].getBytes());
                nextString++;
                out.write('\n');

                keysProcessor = new KeysProcessor();
                keysProcessor.generateKeyPair(algorithmName, keySize);
                keysProcessor.processKeys();
                out.write(keysProcessor.buildViewOfKeysToFile(i));

                out.write('\n');
                out.write(PRV_PUB_TXT[nextString].getBytes());

                if (i == 1) nextString++;

            } catch (FileNotFoundException e) {
                System.out.println("Can't find files");
            } catch (IOException | NoSuchAlgorithmException e) {
                System.out.println("I/O exception");
            } finally {
                listStreams.get(i).close();
            }
        }
    }
}