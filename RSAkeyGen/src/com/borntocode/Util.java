package com.borntocode;

import java.io.PrintWriter;
import java.util.Scanner;

interface Util {

    PrintWriter out = new PrintWriter(System.out,true);
    Scanner in = new Scanner(System.in);
    String[] txt = {
            "\n-----BEGIN RSA PRIVATE KEY-----\n", "-----BEGIN RSA PUBLIC KEY-----\n",
            "PRIVATE KEY FORMAT", "PRIVATE KEY ALGORITHM", "\n-----END RSA PRIVATE KEY-----",
            "\n-----END RSA PUBLIC KEY-----", "PUBLIC KEY FORMAT", "PUBLIC KEY ALGORITHM", "---------------"
    };

    void close();
}
