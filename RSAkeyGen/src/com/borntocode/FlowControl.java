package com.borntocode;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class FlowControl {

    private final PrintStream out = new PrintStream(System.out);
    private final Scanner in = new Scanner(System.in);
    private final Locale currentLocale = new Locale("en", "US");
    private final ResourceBundle messages = ResourceBundle.getBundle("Messages", currentLocale);
    private List<ByteBuffer> keysBuffer = new ArrayList<>();
    private boolean flag = true;


    void startMainLoop() {
//        do {
            firstDialog();
            secondDialog();
//        } while (flag);

        closeIO(in, out);
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
        var strFromUser = "";

        out.println();
        out.println(messages.getString("dialog.do.you.want.print"));
        out.println();
        out.println(messages.getString("dialog.q.terminate.process"));

        try {
            strFromUser = in.findInLine("[ynqYNQ]");
            in.nextLine();

            switch (strFromUser.toUpperCase()) {
                case "Y":
                    buildViewOfKeysToConsole(keysBuffer, prvPub);
                    break;
                case "N":
                    new OutBuffer().saveKeysToFiles(keysBuffer);
                    break;
                case "Q":
                    flag = false;
                    closeIO(in, out);
                    System.exit(0);
                default:
                    out.println("Your " + strFromUser);
            }
        } catch (InputMismatchException | IOException e) {
            System.err.println(messages.getString("dialog.bad.choice.try.again.or.quit.q"));
        }
    }

    private void displayInfoAboutKeys(KeyPair keys) {
        out.println();
        out.println(messages.getString("info.private.key.format").toLowerCase() + ": "
                + keys.getPrivate().getFormat());
        out.println(messages.getString("info.private.key.algorithm").toLowerCase() + ": "
                + keys.getPrivate().getAlgorithm());
        out.println();
        out.println(messages.getString("info.public.key.format").toLowerCase() + ": "
                + keys.getPublic().getFormat());
        out.println(messages.getString("info.public.key.algorithm").toLowerCase() + ": "
                + keys.getPublic().getAlgorithm());
        out.print(messages.getString("dialog.separator"));
    }

    private void buildViewOfKeysToConsole(List<ByteBuffer> keysBuffer, String[] prvPub) {
        var index = 0;

        try {
            for (ByteBuffer key : keysBuffer) {
                if (index == 0) out.println(messages.getString("dialog.separator"));
                out.write('\n');
                out.println(messages.getString("key.begin.rsa." + prvPub[index] + ".key"));
                out.write(key.array());
                out.write('\n');
                out.println(messages.getString("key.end.rsa." + prvPub[index] + ".key"));

                index++;

                out.write('\n');
                out.write('\n');
            }
        } catch (IOException e) {
            out.println("I/O exception");
        }
    }

    private void closeIO(Scanner in, PrintStream out) {
        in.close();
        out.close();
    }
}
