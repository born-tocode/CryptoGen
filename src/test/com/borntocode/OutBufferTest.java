package com.borntocode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OutBufferTest {

    private OutBuffer outBuffer;
    private File filePrv;
    private File filePub;

    @BeforeEach
    public void setUp() {
        filePrv = Paths.get("privateKey.key").toFile();
        filePub = Paths.get("publicKey.pub").toFile();
    }

    @Test
    public void setFilesWriteable() {
        assertTrue(filePrv.setWritable(true, false));
        assertTrue(filePub.setWritable(true, false));
    }

    @Test
    public void expectedSaveKeysToFilesWorksAndMakeFiles() throws IOException {
        outBuffer = new OutBuffer();
        outBuffer.saveKeysToFiles("RSA", 512);
        assertTrue(filePrv.exists());
        assertTrue(filePub.exists());
    }

    @Test
    public void expectedFileAreDeleted() {
        filePrv.delete();
        filePub.delete();
        assertFalse(filePrv.exists());
        assertFalse(filePub.exists());
    }

    @Test
    public void expectedFilesAreWriteable() {
        assertTrue(filePrv.canWrite());
        assertTrue(filePub.canWrite());
    }

    @Test
    public void expectedFilesAreReadable() {
        assertTrue(filePrv.canRead());
        assertTrue(filePub.canRead());
    }
}