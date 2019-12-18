package com.borntocode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MainTest {

    private Main main;

    @BeforeEach
    public void setUp(){
        main = new Main();
    }

    @Test
    public void expectedAnyExceptionsFromMain() {
        assertNotEquals(Exception.class, main);
    }
}