package com.borntocode;

import java.io.IOException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class FlowControl {

    //todo: local variables(var), return result to local variables, KeysProcessor with hardcoded texts

    private final Generator generator = new Generator();
    private final OutBuffer outBuffer = new OutBuffer();
    private final KeysProcessor keysProcessor = new KeysProcessor();
    private final PrintStream out = new PrintStream(System.out);
    private final Scanner in = new Scanner(System.in);
    private final Locale currentLocale = new Locale("en", "US");
    private final ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);


    public void startMainLoop() {
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
                    var keys = generator.generateKeys(keyLength);
                    keysProcessor.processKeys(keys);
                    break;
                default:
                    out.println(messages.getString("dialog.bad.digit") + digFromUser);
            }
        } catch (InputMismatchException | NoSuchAlgorithmException | IOException e) {
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
//                    printKeysToConsole();
                    break;
                case "N":
                    outBuffer.saveKeysToFiles();
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

    private void printKeysToConsole() throws IOException {
        out.println(messages.getString("dialog.separator"));
        out.println();
        out.println(messages.getString("key.begin.rsa.private.key"));
        out.println(keysProcessor.getPrivateKeyStream().byteValue());
        out.println(messages.getString("key.end.rsa.private.key"));

        out.println();
        out.println();

        out.println(messages.getString("key.begin.rsa.public.key"));
        out.println(keysProcessor.getPublicKeyStream().byteValue());
        out.println(messages.getString("key.end.rsa.public.key"));
        out.println();
        out.print(messages.getString("dialog.separator"));
    }

    private void closeIO() {
        in.close();
        out.close();
    }
}
