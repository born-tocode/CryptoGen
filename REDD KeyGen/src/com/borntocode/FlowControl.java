package com.borntocode;

import java.io.IOException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class FlowControl {

    private KeysProcessor keysProcessor;
    private final PrintStream out;
    private final Scanner in;
    private final ResourceBundle messages;
    private Map<String, Set<Integer>> keySizeRestrictions;
    private Integer keySize;
    private String algorithmName;

    FlowControl() {
        Locale currentLocale = new Locale("en", "US");
        messages = ResourceBundle.getBundle("Messages", currentLocale);
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
        keySizeRestrictions = new LinkedHashMap<>();
    }

    void startMainLoop() {
        firstDialog();
        secondDialog();
        thirdDialog();
        restartProgram();
    }

    private void firstDialog() {
        keySizeRestrictions.put("RSA", Set.of(1024, 2048, 4096, 8192, 12288, 16384));
        keySizeRestrictions.put("EC", Set.of(112, 256, 571));
        keySizeRestrictions.put("DiffieHellman", Set.of(512, 1024, 1536, 2048, 3072, 4096, 6144, 8192));
        keySizeRestrictions.put("DSA", Set.of(512, 1024, 2048, 3072));

        var digFromUser = 0;

        out.println();
        out.println(messages.getString("dialog.please.select.algorithm"));
        out.println();

        printAlgorithmsToConsole(keySizeRestrictions);

        out.println();

        try {
            in.hasNextInt();
            digFromUser = in.nextInt();
            in.nextLine();
            switch (digFromUser) {
                case 0:
                case 1:
                case 2:
                case 3:
                    var algorithmArray = keySizeRestrictions.keySet().toArray();
                    algorithmName = algorithmArray[digFromUser].toString();
                    break;
                default:
                    out.println(messages.getString("dialog.bad.digit") + digFromUser);
            }
        } catch (InputMismatchException e) {
            out.println(messages.getString("dialog.incorrect.choice"));
        }
    }

    private void secondDialog() {
        var digFromUser = 0;

        out.println();
        out.println(messages.getString("dialog.please.type.a.digit." + algorithmName));

        printKeySizeToConsole(keySizeRestrictions, algorithmName);

        out.println();

        try {
            in.hasNextInt();
            digFromUser = in.nextInt();
            in.nextLine();

            var keySizeSet = keySizeRestrictions.get(algorithmName);
            keySize = Integer.parseInt(keySizeSet.toArray()[digFromUser].toString());
        } catch (InputMismatchException e) {
            out.println(messages.getString("dialog.incorrect.choice"));
        }
    }

    private void thirdDialog() {

        out.println(messages.getString("dialog.separator"));
        out.println();
        out.println(messages.getString("dialog.do.you.want.print"));
        out.println();
        out.println(messages.getString("dialog.q.terminate.process"));

        try {
            var strFromUser = in.findInLine("[ynqYNQ]");
            in.nextLine();

            switch (strFromUser.toUpperCase()) {
                case "Y":
                    printViewOfKeysToConsole();
                    break;
                case "N":
                    new OutBuffer().saveKeysToFiles(algorithmName, keySize);
                    break;
                case "Q":
                    closeIOAndExit(in, out);
                default:
                    out.println("Your " + strFromUser);
            }
        } catch (InputMismatchException | IOException | NoSuchAlgorithmException e) {
            System.err.println(messages.getString("dialog.bad.choice.try.again.or.quit.q"));
        }
    }

    private void printAlgorithmsToConsole(Map<String, Set<Integer>> keySizeRestrictions) {
        var count = 0;
        for (String algorithm : keySizeRestrictions.keySet()) {
            out.print(count + ". " + algorithm + " / ");
            count++;
        }
    }

    private void printKeySizeToConsole(Map<String, Set<Integer>> keySizeRestrictions, String algorithm) {
        var count = 0;
        for (Integer keySize : keySizeRestrictions.get(algorithm)) {
            out.print(count + ". " + keySize + " / ");
            count++;
        }
    }


    private void printViewOfKeysToConsole() throws IOException, NoSuchAlgorithmException {
        final String[] keyPairView = {"private", "public"};

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                out.println(messages.getString("dialog.separator"));
                out.write('\n');
            }
            out.write('\n');
            out.println(messages.getString("key.begin.rsa." + keyPairView[i] + ".key"));

            keysProcessor = new KeysProcessor();
            keysProcessor.generateKeys(algorithmName, keySize);
            keysProcessor.processKeys();
            keysProcessor.buildViewOfKeysToConsole(i);

            out.write('\n');
            out.println(messages.getString("key.end.rsa." + keyPairView[i] + ".key"));

            out.write('\n');
            out.write('\n');
        }
    }

    private void restartProgram() {
        var flag = true;
        out.println(messages.getString("dialog.separator"));
        out.println(messages.getString("dialog.restart.program.or.quit"));

        do {
            try {
                var strFromUser = in.findInLine("[ynYN]");
                in.nextLine();

                switch (strFromUser.toUpperCase()) {
                    case "Y":
                        startMainLoop();
                        break;
                    case "N":
                    default:
                        flag = false;
                        closeIOAndExit(in, out);
                }
            } catch (InputMismatchException e) {
                System.err.println(messages.getString("dialog.bad.choice.try.again.or.quit.q"));
            }
        } while (flag);
    }

    private void closeIOAndExit(Scanner in, PrintStream out) {
        in.close();
        out.close();
        System.exit(0);
    }
}
