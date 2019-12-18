package com.borntocode;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class OutBufferTest {

    private static OutBuffer outBuffer;
    private static Path filePrv;
    private static Path filePub;

    @BeforeClass
    public static void setUp() throws FileNotFoundException {
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