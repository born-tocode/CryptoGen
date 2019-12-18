package com.borntocode;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static com.borntocode.Main.main;
import static org.junit.Assert.*;

public class MainTest {

    private static Main main;

    @BeforeClass
    public static void setUp(){
        main = new Main();
    }

    @Test
    public void expectedAnyExceptionsFromMain() {
        assertNotEquals(Exception.class, main);
    }

    @Test
    public void expectedAnyExceptionsFromMainMethod(){
        String[] args = {"test", "test1", "test;"};
        assertEquals("Run program without additional args", main(args));
    }
}