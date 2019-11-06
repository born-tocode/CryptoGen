package com.borntocode;

import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.*;

class RSAkeyGen {

    private Generator generator = new Generator();
    private OutBuffer outBuffer = new OutBuffer();
    private PrintWriter out = new PrintWriter(System.out, true);
    private Scanner in = new Scanner(System.in);
    private Base64.Encoder encoder = Base64.getEncoder();
    private List<Integer> listSizesOfKeys = List.of(1024, 2048, 4096, 8192, 12288, 16384);
    private ListIterator<Integer> listIterator = listSizesOfKeys.listIterator();
    private Locale currentLocale = new Locale("en", "US");
    private ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
    private int keyLength;
    



    public static void main(String[] args) {
        RSAkeyGen keyGen = new RSAkeyGen();

        keyGen.mainFlowControl();
        keyGen.closeIO();
    }

    private void mainFlowControl() {
        int count = 0;
        int digFromUser;
        String strFromUser;

/*
first dialog with user
*/
        out.println();
        out.println(messages.getString("please.type.a.digit"));
        out.println();

        while (listIterator.hasNext()) {
            int sizeOfKeys = listIterator.next();
            out.print(count + ". " + sizeOfKeys + " / ");
            count++;
        }

        out.println();

        try {
             /*
            first switch statement, first response from user
         */
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
                    setKeyLength(digFromUser);
                    generator.generateKeys(getKeyLength());
                    generator.extractKeys();
                    break;
                default:
                    out.println("Your digit: " + digFromUser);
            }
        } catch (InputMismatchException | NoSuchAlgorithmException e) {
            out.println("Incorrect choice.");
        }


        displayInfoAboutKeys();

/*
second dialog with user
*/

        out.println();
        out.println(messages.getString("do.you.want.print"));
        out.println();
        out.println(messages.getString("q.terminate.process"));

        try {
             /*
            second switch statement, second response from user
         */
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
            System.err.println(messages.getString("bad.choice.try.again.or.quit.q"));
        }
    }


    private void setKeyLength(int keyLength) {
        this.keyLength = keyLength;
    }

    private int getKeyLength() {
        return listSizesOfKeys.get(keyLength);
    }

    private void displayInfoAboutKeys() {
        out.println();
        out.println(messages.getString("private.key.format").toLowerCase() + ": "
                + generator.getPrivateKey().getFormat());
        out.println(messages.getString("private.key.algorithm").toLowerCase() + ": "
                + generator.getPrivateKey().getAlgorithm());
        out.println();
        out.println(messages.getString("public.key.format").toLowerCase() + ": "
                + generator.getPublicKey().getFormat());
        out.println(messages.getString("public.key.algorithm").toLowerCase() + ": "
                + generator.getPublicKey().getAlgorithm());
        out.print(messages.getString("separator"));
    }

    private void printKeysToConsole() {
        out.println(messages.getString("separator"));
        out.println();
        out.println(messages.getString("begin.rsa.private.key"));
        out.println(encoder.encodeToString(generator.getPrivateKey().getEncoded()));
        out.println(messages.getString("end.rsa.private.key"));

        out.println();
        out.println();

        out.println(messages.getString("begin.rsa.public.key"));
        out.println(encoder.encodeToString(generator.getPublicKey().getEncoded()));
        out.println(messages.getString("end.rsa.public.key"));
        out.println();
        out.print(messages.getString("separator"));
    }

    private void closeIO() {
        in.close();
        out.close();
    }
}
