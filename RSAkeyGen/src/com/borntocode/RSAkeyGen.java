package com.borntocode;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.*;

class RSAkeyGen {

    private final Base64.Encoder ENCODER = Base64.getEncoder();
    private final SortedMap<Integer, Integer> KEYSIZE = new TreeMap<>();
    private final Scanner IN = new Scanner(System.in);
    private final PrintWriter OUT = new PrintWriter(System.out, true);
    private final Path FILE_PRV = Paths.get("privateKey.key");
    private final Path FILE_PUB = Paths.get("publicKey.pub");
    private final String[] TXT = {
            "\n-----BEGIN RSA PRIVATE KEY-----\n", "-----BEGIN RSA PUBLIC KEY-----\n",
            "PRIVATE KEY FORMAT", "PRIVATE KEY ALGORITHM", "\n-----END RSA PRIVATE KEY-----",
            "\n-----END RSA PUBLIC KEY-----", "PUBLIC KEY FORMAT", "PUBLIC KEY ALGORITHM"
    };
    private KeyPairGenerator keyPairGen;
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public static void main(String[] args) {
        RSAkeyGen keyGen = new RSAkeyGen();
        keyGen.prepareToGenerate();

        int keyLength = keyGen.KEYSIZE.get(keyGen.getKeyFromUser());

        keyGen.generator(keyLength);
        keyGen.displayInfoAboutKeys();
        keyGen.mainFlowControl();

    }

    private void prepareToGenerate() {
        KEYSIZE.put(0, 1024);
        KEYSIZE.put(1, 2048);
        KEYSIZE.put(2, 4096);
        KEYSIZE.put(3, 8192);
        KEYSIZE.put(4, 12288);
        KEYSIZE.put(5, 16384);
    }

    private int getKeyFromUser() {
        OUT.println();
        OUT.println("Please type a digit for strength of RSA:");
        OUT.println();

        for (Integer k : KEYSIZE.keySet()) {
            Integer v = KEYSIZE.get(k);
            OUT.print(k + ". " + v + " / ");
        }

        OUT.println();

        int key;
        IN.hasNextInt();
        key = IN.nextInt();
        IN.nextLine();
        return key;
    }

    private void mainFlowControl() {
        String ans = null;

        OUT.println();
        OUT.println(
                "Do you want print buffer in this console(buffer are cleared " +
                        "after end of program)? [ Y/N ]\nIf 'N' keys will automatically " +
                        "saved to files without printing in this console.");
        OUT.println();
        OUT.println("'Q' terminate process.");

        try {
            while (ans == null) {
                ans = IN.findInLine("[ynqYNQ]");
                IN.nextLine();
                if (ans.equalsIgnoreCase("Y")) {
                    printKeysToConsole();
                } else if (ans.equalsIgnoreCase("N")) {
                    saveKeysToFiles();
                } else if (ans.equalsIgnoreCase("Q")) {
                    IN.close();
                    OUT.close();
                    System.exit(0);
                }
            }
        } catch (InputMismatchException e) {
            System.err.println("Bad choice. Try again.. or quit 'Q'");
        } finally {
            mainFlowControl();
        }
    }

    private void generator(int keyLenght) {
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No such algorithm");
        }

        keyPairGen.initialize(keyLenght);

        keyPair = keyPairGen.generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }


    private void displayInfoAboutKeys() {
        OUT.println();
        OUT.println(TXT[2].toLowerCase() + ": " + privateKey.getFormat());
        OUT.println(TXT[3].toLowerCase() + ": " + privateKey.getAlgorithm());
        OUT.println();
        OUT.println(TXT[6].toLowerCase() + ": " + publicKey.getFormat());
        OUT.println(TXT[7].toLowerCase() + ": " + publicKey.getAlgorithm());
    }

    private void printKeysToConsole() {
        OUT.print(TXT[0]);
        OUT.print(ENCODER.encodeToString(privateKey.getEncoded()));
        OUT.print(TXT[4]);

        OUT.println();
        OUT.println();

        OUT.print(TXT[1]);
        OUT.print(ENCODER.encodeToString(publicKey.getEncoded()));
        OUT.print(TXT[5]);
        OUT.println();
    }

    private void saveKeysToFiles() {

        try (FileOutputStream fOutPrv = new FileOutputStream(FILE_PRV.toFile());
             FileOutputStream fOutPub = new FileOutputStream(FILE_PUB.toFile())) {

            fOutPrv.write(TXT[0].getBytes());
            fOutPrv.write(ENCODER.encode(privateKey.getEncoded()));
            fOutPrv.write(TXT[4].getBytes());

            OUT.println();
            OUT.println();

            fOutPub.write(TXT[1].getBytes());
            fOutPub.write(ENCODER.encode(publicKey.getEncoded()));
            fOutPub.write(TXT[5].getBytes());

            OUT.println("Keys are saved in program directory.");
            OUT.println();
            OUT.println();
        } catch (IOException e) {
            OUT.println("Input/output error");
            OUT.println(e.getMessage());
        }
    }
}
