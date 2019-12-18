package com.borntocode;
/*
 * REDD KeyGenerator @2019 v1.1.0
 * by born to code
 */

public class Main {

    public static String main(String[] args) {
        if (args == null) {
            FlowControl flowControl = new FlowControl();
            flowControl.startMainLoop();
        }
        return "Run program without additional args";
    }
}
