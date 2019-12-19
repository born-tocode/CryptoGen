package com.borntocode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

public class KeysProcessorTest {

    private KeysProcessor keysProcessor;

    @BeforeEach
    public void setUp() {
        keysProcessor = new KeysProcessor();
    }

    @Test
    public void expectedNoSuchAlgorithm() {
        assertThrows(NoSuchAlgorithmException.class,
                     () -> keysProcessor.generateKeyPair("fake algorithm", 0)
                    );
    }

    @Test
    public void expectedInvalidParameterException() {
        assertThrows(InvalidParameterException.class,
                     () -> keysProcessor.generateKeyPair("RSA", 0)
                    );
    }

    @Test
    public void expectedKeyPairNotNull() throws NoSuchAlgorithmException {
        var algorithmName = "RSA";
        var keySize = 512;
        var keyPairGen = KeyPairGenerator.getInstance(algorithmName);
        keyPairGen.initialize(keySize);
        var keyPair = keyPairGen.generateKeyPair();
        assertNotNull(keyPair);
    }

    @Test
    public void expectedTrueIfArrayIsLessThanFullLengthOfSegment() {
        var fullLengthOfSegment = 55;
        var bytesArray = keysProcessor.splitKeyAlgorithm("some string".getBytes());
        assertTrue(bytesArray.length < fullLengthOfSegment);
    }

    @Test
    public void expectedNullListOfBuffers() {
        assertThrows(IndexOutOfBoundsException.class,
                     () -> keysProcessor.buildViewOfKeysToConsole(0)
                    );
    }

    @Test
    public void expectedIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class,
                     () -> keysProcessor.buildViewOfKeysToFile(4)
                    );
    }
}