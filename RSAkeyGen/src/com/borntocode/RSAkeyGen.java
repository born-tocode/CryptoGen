package com.borntocode;

import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

class RSAkeyGen {

    private Generator generator = new Generator();
    private OutBuffer outBuffer = new OutBuffer();
    private PrintWriter out = new PrintWriter(System.out, true);
    private Scanner in = new Scanner(System.in);

    //todo: Internationalization + deleting Util

    public static void main(String[] args) {
        RSAkeyGen keyGen = new RSAkeyGen();

        keyGen.mainFlowControl();
        keyGen.closeIO();
    }

    private void mainFlowControl() {
        int digFromUser;
        String strFromUser;

/*
first dialog with user
*/
        out.println();
        out.println("Please type a digit for strength of RSA:");
        out.println();

        for (Integer k : generator.getKeysize().keySet()) {
            Integer v = generator.getKeysize().get(k);
            out.print(k + ". " + v + " / ");
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
                    generator.setKeyLength(digFromUser);
                    generator.generateKeys();
                    generator.extractKeys();
                    break;
                default:
                    out.println("Your digit: " + digFromUser);
            }
        } catch (InputMismatchException e) {
            out.println("Incorrect choice.");
        }


        outBuffer.displayInfoAboutKeys();

/*
second dialog with user
*/

        out.println();
        out.println(
                "Do you want print buffer in this console(buffer are cleared " +
                        "after end of program)? [ Y/N ]\nIf 'N' keys will automatically " +
                        "saved to files without printing in this console.");
        out.println();
        out.println("'Q' terminate process.");

        try {
             /*
            second switch statement, second response from user
         */
            strFromUser = in.findInLine("[ynqYNQ]");
            in.nextLine();

            switch (strFromUser.toUpperCase()) {
                case "Y":
                    outBuffer.printKeysToConsole();
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
            System.err.println("Bad choice. Try again.. or quit 'Q'");
        }
    }

    private void closeIO() {
        in.close();
        out.close();
    }
}
