package com.borntocode;

import java.io.IOException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class FlowControl {

    private final PrintStream out;
    private final Scanner in;
    private final ResourceBundle messages;
    private KeysProcessor keysProcessor;
    private int keyLength;

    FlowControl() throws IOException {
        Locale currentLocale = new Locale("en", "US");
        messages = ResourceBundle.getBundle("Messages", currentLocale);
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
    }

    void startMainLoop() {
        firstDialog();
        secondDialog();
        restartProgram();
    }

    private void firstDialog() {
        var listSizesOfKeys = List.of(1024, 2048, 4096, 8192, 12288, 16384);
        var digFromUser = 0;


        out.println();
        out.println(messages.getString("dialog.please.type.a.digit"));
        out.println();

        printKeySizeToConsole(listSizesOfKeys);

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
                case 4:
                case 5:
                    keyLength = listSizesOfKeys.get(digFromUser);
                    break;
                default:
                    out.println(messages.getString("dialog.bad.digit") + digFromUser);
            }
        } catch (InputMismatchException e) {
            out.println(messages.getString("dialog.incorrect.choice"));
        }
    }

    private void secondDialog() {
        final String[] prvPub = {"private", "public"};

        out.println();
        out.println(messages.getString("dialog.do.you.want.print"));
        out.println();
        out.println(messages.getString("dialog.q.terminate.process"));

        try {
            var strFromUser = in.findInLine("[ynqYNQ]");
            in.nextLine();

            switch (strFromUser.toUpperCase()) {
                case "Y":
                    printViewOfKeysToConsole(prvPub);
                    break;
                case "N":
//                    new OutBuffer().saveKeysToFiles();
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

    private void printKeySizeToConsole(List<Integer> listSizesOfKeys) {
        var count = 0;
        for (int size : listSizesOfKeys) {
            out.print(count + ". " + size + " / ");
            count++;
        }
    }


    private void printViewOfKeysToConsole(String[] prvPub) throws IOException, NoSuchAlgorithmException {

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                out.println(messages.getString("dialog.separator"));
                out.write('\n');
            }
            out.write('\n');
            out.println(messages.getString("key.begin.rsa." + prvPub[i] + ".key"));

            processAndBuildViewOfKeys(i);

            out.write('\n');
            out.println(messages.getString("key.end.rsa." + prvPub[i] + ".key"));

            out.write('\n');
            out.write('\n');
        }
    }

    private void processAndBuildViewOfKeys(int whichKey) throws IOException, NoSuchAlgorithmException {
        new Generator().generateKeys(keyLength);
        keysProcessor = new KeysProcessor();
        keysProcessor.buildViewOfKeys(whichKey);
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
