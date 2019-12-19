package com.borntocode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class FlowControlTest {

    private FlowControl flowControl;
    private Scanner scanner;
    private PrintStream printStream;

    @BeforeEach
    public void setUp() {
        flowControl = new FlowControl();
    }

    @Test
    public void expectedResourceBundleExists() {
        Locale currentLocale = new Locale("en", "US");
        assertSame(ResourceBundle.getBundle("Messages", currentLocale),
                   ResourceBundle.getBundle("Messages", currentLocale)
                  );
    }

    @Test
    public void expectedFakeResourceBundleLocaleDontExists() {
        Locale currentLocale = new Locale("test", "test");
        assertThrows(MissingResourceException.class,
                     () -> ResourceBundle.getBundle("Messages", currentLocale)
                    );
    }

    @Test
    public void expectedResourceBundleBaseNameDontExist() {
        assertThrows(MissingResourceException.class,
                     () -> ResourceBundle.getBundle("test")
                    );
    }

    @Test
    public void expectedScannerExists() {
        assertDoesNotThrow(() -> scanner = new Scanner(System.in));
        assertNotNull(scanner);
    }

    @Test
    public void expectedPrintStreamExists() {
        assertDoesNotThrow(() -> printStream = new PrintStream(System.out));
        assertNotNull(printStream);
    }
}