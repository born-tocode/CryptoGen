package com.borntocode;

import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class FlowControl {

    //todo: local variables(var), return result to local variables, KeyDisplayer with hardcoded texts

    private final Generator generator = new Generator();
    private final OutBuffer outBuffer = new OutBuffer();
//    private final PrintWriter out = new PrintWriter(System.out, true);
    private final Scanner in = new Scanner(System.in);
    private final Base64.Encoder encoder = Base64.getEncoder();
    private final List<Integer> listSizesOfKeys = List.of(1024, 2048, 4096, 8192, 12288, 16384);
    private final ListIterator<Integer> listIterator = listSizesOfKeys.listIterator();
    private final Locale currentLocale = new Locale("en", "US");
    private final ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);


    public void startMainLoop() {
        firstDialog();
        displayInfoAboutKeys();
        secondDialog();
//        closeIO();
    }

    private void firstDialog() {
        PrintWriter out = new PrintWriter(System.out, true);
        int count = 0;
        int digFromUser;


        out.println();
        out.println(messages.getString("dialog.please.type.a.digit"));
        out.println();

        while (listIterator.hasNext()) {
            int sizeOfKeys = listIterator.next();
            out.print(count + ". " + sizeOfKeys + " / ");
            count++;
        }

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
                    generator.generateKeys(listSizesOfKeys.get(digFromUser));
                    generator.extractKeys();
                    break;
                default:
                    out.println(messages.getString("dialog.bad.digit") + digFromUser);
            }
        } catch (InputMismatchException | NoSuchAlgorithmException e) {
            out.println(messages.getString("dialog.incorrect.choice"));
        }
        out.close();
    }

    private void secondDialog() {
        PrintWriter out = new PrintWriter(System.out, true);
        String strFromUser;

        out.println();
        out.println(messages.getString(messages.getString("dialog.do.you.want.print")));
        out.println();
        out.println(messages.getString("dialog.q.terminate.process"));

        try {
            strFromUser = in.findInLine("[ynqYNQ]");
            in.nextLine();

            switch (strFromUser.toUpperCase()) {
                case "Y":
                    printKeysToConsole();
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

    private void displayInfoAboutKeys() {
        PrintWriter out = new PrintWriter(System.out, true);
        out.println();
        out.println(messages.getString("info.private.key.format").toLowerCase() + ": "
                + generator.getPrivateKey().getFormat());
        out.println(messages.getString("info.private.key.algorithm").toLowerCase() + ": "
                + generator.getPrivateKey().getAlgorithm());
        out.println();
        out.println(messages.getString("info.public.key.format").toLowerCase() + ": "
                + generator.getPublicKey().getFormat());
        out.println(messages.getString("info.public.key.algorithm").toLowerCase() + ": "
                + generator.getPublicKey().getAlgorithm());
        out.print(messages.getString("dialog.separator"));
    }

    private void printKeysToConsole() {
        PrintWriter out = new PrintWriter(System.out, true);
        out.println(messages.getString("dialog.separator"));
        out.println();
        out.println(messages.getString("key.begin.rsa.private.key"));
        out.println(encoder.encodeToString(generator.getPrivateKey().getEncoded()));
        out.println(messages.getString("key.end.rsa.private.key"));

        out.println();
        out.println();

        out.println(messages.getString("key.begin.rsa.public.key"));
        out.println(encoder.encodeToString(generator.getPublicKey().getEncoded()));
        out.println(messages.getString("key.end.rsa.public.key"));
        out.println();
        out.print(messages.getString("dialog.separator"));
    }

//    private void closeIO() {
//        in.close();
//        out.close();
//    }
}
