package com.borntocode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OutBufferTest {

    private OutBuffer outBuffer;
    private Path filePrv;
    private Path filePub;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        outBuffer = new OutBuffer();
        filePrv = Paths.get("privateKey.key");
        filePub = Paths.get("publicKey.pub");
    }

    @Test
    public void expectedFilesExists() throws IOException {
        outBuffer.saveKeysToFiles("RSA", 512);
        assertTrue(filePrv.toFile().exists());
        assertTrue(filePub.toFile().exists());
    }
}