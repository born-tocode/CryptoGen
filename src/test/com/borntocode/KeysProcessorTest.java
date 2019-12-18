package com.borntocode;

import org.junit.BeforeClass;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class KeysProcessorTest {

    private static KeysProcessor keysProcessor;

    @BeforeClass
    public static void setUp(){
        keysProcessor = new KeysProcessor();
    }

    @Test(expected = NoSuchAlgorithmException.class)
    public void expectedNoSuchAlgorithm() throws NoSuchAlgorithmException {
        keysProcessor.generateKeyPair("fake algorithm", 0);
    }

    @Test(expected = InvalidParameterException.class)
    public void expectedInvalidParameterException() throws NoSuchAlgorithmException{
        keysProcessor.generateKeyPair("RSA", 0);
    }

    @Test
    public void expectedKeyPairNotNull() throws NoSuchAlgorithmException {
        var algorithmName = "RSA";
        var keySize = 512;
        keysProcessor.generateKeyPair(algorithmName,keySize);
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

    @Test(expected = IndexOutOfBoundsException.class)
    public void expectedNullListOfBuffers() throws Exception {
        keysProcessor.buildViewOfKeysToConsole(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void expectedIndexOutOfBounds() {
        keysProcessor.buildViewOfKeysToFile(4);
    }
}