package com.borntocode;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class FlowControl {

    //todo: local variables(var), return result to local variables, KeysProcessor with hardcoded texts

    private final PrintStream out = new PrintStream(System.out);
    private final Scanner in = new Scanner(System.in);
    private final Locale currentLocale = new Locale("en", "US");
    private final ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
    private List<ByteBuffer> keysBuffer = new ArrayList<>();


    void startMainLoop() {
        firstDialog();
//        displayInfoAboutKeys();
        secondDialog();
        closeIO();
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
                    buildViewOfKeysToConsole(keysBuffer.get(0), keysBuffer.get(1));
                    break;
                case "N":
                    new OutBuffer().saveKeysToFiles(keysBuffer.get(0), keysBuffer.get(1));
                    break;
                case "Q":
                    in.close();
                    out.close();
                    System.exit(0);
                    break;
                default:
                    out.println("Your " + strFromUser);
            }
        } catch (InputMismatchException e) {
            System.err.println(messages.getString("dialog.bad.choice.try.again.or.quit.q"));
        }
    }

//    private void displayInfoAboutKeys() {
//        out.println();
//        out.println(messages.getString("info.private.key.format").toLowerCase() + ": "
//                + generator.getPrivateKey().getFormat());
//        out.println(messages.getString("info.private.key.algorithm").toLowerCase() + ": "
//                + generator.getPrivateKey().getAlgorithm());
//        out.println();
//        out.println(messages.getString("info.public.key.format").toLowerCase() + ": "
//                + generator.getPublicKey().getFormat());
//        out.println(messages.getString("info.public.key.algorithm").toLowerCase() + ": "
//                + generator.getPublicKey().getAlgorithm());
//        out.print(messages.getString("dialog.separator"));
//    }

    private void buildViewOfKeysToConsole(ByteBuffer privateKey, ByteBuffer publicKey) {
        out.println(messages.getString("dialog.separator"));
        out.println();
        out.println(messages.getString("key.begin.rsa.private.key"));
        out.println(privateKey.get());
        out.println(messages.getString("key.end.rsa.private.key"));

        out.println();
        out.println();

        out.println(messages.getString("key.begin.rsa.public.key"));
        out.println(publicKey.get());
        out.println(messages.getString("key.end.rsa.public.key"));
        out.println();
        out.print(messages.getString("dialog.separator"));
    }

    private void closeIO() {
        in.close();
        out.close();
    }
}
