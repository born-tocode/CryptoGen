package com.borntocode;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class FlowControl {

    private final PrintStream out = new PrintStream(System.out);
    private final Scanner in = new Scanner(System.in);
    private final Locale currentLocale = new Locale("en", "US");
    private final ResourceBundle messages = ResourceBundle.getBundle("Messages", currentLocale);
    private List<ByteBuffer> keysBuffer = new ArrayList<>();

    void startMainLoop() {
        firstDialog();
        secondDialog();
        restartProgram();
    }

    private void firstDialog() {
        var listSizesOfKeys = List.of(1024, 2048, 4096, 8192, 12288, 16384);
        var listIterator = listSizesOfKeys.listIterator();
        var count = 0;
        var digFromUser = 0;


        out.println();
        out.println(messages.getString("dialog.please.type.a.digit"));
        out.println();

        while (listIterator.hasNext()) {
            var sizeOfKeys = listIterator.next();
            out.print(count + ". " + sizeOfKeys + " / ");
            count++;
        }

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
                    var keyLength = listSizesOfKeys.get(digFromUser);
                    var keys = new Generator().generateKeys(keyLength);
                    keysBuffer = new KeysProcessor().processKeys(keys);
                    break;
                default:
                    out.println(messages.getString("dialog.bad.digit") + digFromUser);
            }
        } catch (InputMismatchException | NoSuchAlgorithmException e) {
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
                    buildViewOfKeysToConsole(keysBuffer, prvPub);
                    break;
                case "N":
                    new OutBuffer().saveKeysToFiles(keysBuffer);
                    break;
                case "Q":
                    closeIOAndExit(in, out);
                default:
                    out.println("Your " + strFromUser);
            }
        } catch (InputMismatchException e) {
            System.err.println(messages.getString("dialog.bad.choice.try.again.or.quit.q"));
        } catch (FileNotFoundException e) {
            out.println("");
        }
    }

    private void buildViewOfKeysToConsole(List<ByteBuffer> keysBuffer, String[] prvPub) {
        var nextString = 0;
        var splitStream = 55;

        for (ByteBuffer key : keysBuffer) {
            if (nextString == 0) out.println(messages.getString("dialog.separator"));
            out.write('\n');
            out.println(messages.getString("key.begin.rsa." + prvPub[nextString] + ".key"));

            for (int i = 0, x = 0; i < key.array().length; i++, x++) {
                if (x == splitStream) {
                    out.write('\n');
                    x = 0;
                }
                out.write(key.get(i));
            }
            out.write('\n');
            out.println(messages.getString("key.end.rsa." + prvPub[nextString] + ".key"));

            nextString++;

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
