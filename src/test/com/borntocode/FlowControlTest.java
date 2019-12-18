package com.borntocode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.PrintStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class FlowControlTest {

    private FlowControl flowControl;

    @BeforeEach
    public void setUp() {
        flowControl = new FlowControl();
    }

    @Test
    public void expectedResourceBundleExists() {
        Locale currentLocale = new Locale("en", "US");
        assertSame(ResourceBundle.getBundle("Messages", currentLocale),
                ResourceBundle.getBundle("Messages", currentLocale));
    }

    @Test
    public void expectedResourceBundleLocaleDontExists() {
        Locale currentLocale = new Locale("test", "test");
        assertThrows(MissingResourceException.class,
                () -> ResourceBundle.getBundle("Messages", currentLocale));
    }

    @Test
    public void expectedResourceBundleBaseNameDontExist() {
        assertThrows(MissingResourceException.class,
                () -> ResourceBundle.getBundle("test"));
    }

    @Test
    public void expectedScannerExists() {
        assertDoesNotThrow(() -> new Scanner(System.in));
    }

    @Test
    public void expectedPrintStreamExists() {
        assertDoesNotThrow(() -> new PrintStream(System.out).println());
    }
}