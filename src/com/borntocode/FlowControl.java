package com.borntocode;

import java.io.IOException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class FlowControl {

    private final PrintStream OUT;
    private final Scanner IN;
    private final ResourceBundle MESSAGES;
    private KeysProcessor keysProcessor;
    private Map<String, List<Integer>> keySizeRestrictions;
    private Integer keySize;
    private String algorithmName;

    public FlowControl() {
        Locale currentLocale = new Locale("en", "US");
        MESSAGES = ResourceBundle.getBundle("Messages", currentLocale);
        IN = new Scanner(System.in);
        OUT = new PrintStream(System.out);
        keySizeRestrictions = new LinkedHashMap<>();
    }

    void startMainLoop() {
        firstDialog();
        secondDialog();
        thirdDialog();
        restartProgram();
    }

    private void firstDialog() {
        keySizeRestrictions.put("RSA", List.of(1024, 2048, 4096, 8192, 12288, 16384));
        keySizeRestrictions.put("EC", List.of(112, 256, 571));
        keySizeRestrictions.put("DiffieHellman", List.of(512, 1024, 1536, 2048, 3072, 4096, 6144, 8192));
        keySizeRestrictions.put("DSA", List.of(512, 1024, 2048, 3072));

        OUT.println();
        OUT.println(MESSAGES.getString("dialog.please.select.algorithm"));

        printAlgorithmsToConsole(keySizeRestrictions);

        OUT.println();

        try {
            IN.hasNextInt();
            var digFromUser = IN.nextInt();
            IN.nextLine();

            var algorithmArray = keySizeRestrictions.keySet().toArray();
            algorithmName = algorithmArray[digFromUser].toString();

        } catch (InputMismatchException e) {
            OUT.println(MESSAGES.getString("dialog.incorrect.choice"));
        }
    }

    private void secondDialog() {

        OUT.println();
        OUT.println(MESSAGES.getString("dialog.please.type.a.digit." + algorithmName));

        printKeySizeToConsole(keySizeRestrictions, algorithmName);

        OUT.println();

        try {
            IN.hasNextInt();
            var digFromUser = IN.nextInt();
            IN.nextLine();

            var keySizeSet = keySizeRestrictions.get(algorithmName);
            var keySizeString = keySizeSet.toArray()[digFromUser].toString();
            keySize = Integer.parseInt(keySizeString);
        } catch (InputMismatchException e) {
            OUT.println(MESSAGES.getString("dialog.incorrect.choice"));
        }
    }

    private void thirdDialog() {

        OUT.println(MESSAGES.getString("dialog.separator"));
        OUT.println();
        OUT.println(MESSAGES.getString("dialog.do.you.want.print"));
        OUT.println();
        OUT.println(MESSAGES.getString("dialog.q.terminate.process"));

        try {
            var strFromUser = IN.findInLine("[ynqYNQ]");
            IN.nextLine();

            switch (strFromUser.toUpperCase()) {
                case "Y":
                    printViewOfKeysToConsole();
                    break;
                case "N":
                    new OutBuffer().saveKeysToFiles(algorithmName, keySize);
                    printInfoAfterSavingKeysToFiles(algorithmName, keySize);
                    break;
                case "Q":
                    closeIOAndExit(IN, OUT);
                default:
                    OUT.println("Your " + strFromUser);
            }
        } catch (InputMismatchException | IOException | NoSuchAlgorithmException e) {
            System.err.println(MESSAGES.getString("dialog.bad.choice.try.again.or.quit.q"));
        }
    }

    private void printAlgorithmsToConsole(Map<String, List<Integer>> keySizeRestrictions) {
        var count = 0;
        for (String algorithm : keySizeRestrictions.keySet()) {
            OUT.println(" / " + count + ". " + algorithm);
            count++;
        }
    }

    private void printKeySizeToConsole(Map<String, List<Integer>> keySizeRestrictions, String algorithm) {
        var count = 0;
        for (Integer keySize : keySizeRestrictions.get(algorithm)) {
            OUT.println(" / " + count + ". " + keySize);
            count++;
        }
    }

    public void printInfoAfterSavingKeysToFiles(String algorithmName, Integer keySize) {
        OUT.println();
        OUT.println(MESSAGES.getString("dialog.generated.and.saved.keys") + " " + algorithmName + " / " + keySize);
    }

    private void printViewOfKeysToConsole() throws IOException, NoSuchAlgorithmException {
        final String[] keyPairView = {"private", "public"};

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                OUT.println(MESSAGES.getString("dialog.separator"));
                OUT.write('\n');
            }
            OUT.write('\n');
            OUT.println(MESSAGES.getString("key.begin." + keyPairView[i] + ".key"));

            keysProcessor = new KeysProcessor();
            keysProcessor.generateKeyPair(algorithmName, keySize);
            keysProcessor.processKeys();
            keysProcessor.buildViewOfKeysToConsole(i);

            OUT.write('\n');
            OUT.println(MESSAGES.getString("key.end." + keyPairView[i] + ".key"));

            OUT.write('\n');
            OUT.write('\n');
        }
    }

    private void restartProgram() {
        var flag = true;
        OUT.println(MESSAGES.getString("dialog.separator"));
        OUT.println(MESSAGES.getString("dialog.restart.program.or.quit"));

        do {
            try {
                var strFromUser = IN.findInLine("[ynYN]");
                IN.nextLine();

                switch (strFromUser.toUpperCase()) {
                    case "Y":
                        startMainLoop();
                        break;
                    case "N":
                    default:
                        flag = false;
                        closeIOAndExit(IN, OUT);
                }
            } catch (InputMismatchException e) {
                System.err.println(MESSAGES.getString("dialog.bad.choice.try.again.or.quit.q"));
            }
        } while (flag);
    }

    public void closeIOAndExit(Scanner in, PrintStream out) {
        in.close();
        out.close();
        System.exit(1);
    }
}