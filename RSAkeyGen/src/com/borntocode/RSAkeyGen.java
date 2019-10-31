package com.borntocode;

class RSAkeyGen implements Util {

    //fields 'in', 'out' and 'txt' are implemented by interface
    private Generator generator = new Generator();
    private OutBuffer outBuffer = new OutBuffer();


    public static void main(String[] args) {
        RSAkeyGen keyGen = new RSAkeyGen();

        keyGen.mainFlowControl();

    }

    private void mainFlowControl() {
        int digFromUser;
        out.println();
        out.println("Please type a digit for strength of RSA:");
        out.println();

        for (Integer k : generator.getKeysize().keySet()) {
            Integer v = generator.getKeysize().get(k);
            out.print(k + ". " + v + " / ");
        }

        out.println();

        in.hasNextInt();
        digFromUser = in.nextInt();
        in.nextLine();

        switch (digFromUser) {


        }


        String ans = null;

        out.println();
        out.println(
                "Do you want print buffer in this console(buffer are cleared " +
                        "after end of program)? [ Y/N ]\nIf 'N' keys will automatically " +
                        "saved to files without printing in this console.");
        out.println();
        out.println("'Q' terminate process.");

//        try {
//            while (ans == null) {
//                ans = in.findInLine("[ynqYNQ][0-9]");
//                in.nextLine();
//                if (ans.equalsIgnoreCase("Y")) {
//                    printKeysToConsole();
//                } else if (ans.equalsIgnoreCase("N")) {
//                    outBuffer.saveKeysToFiles();
//                } else if (ans.equalsIgnoreCase("Q")) {
//                    in.close();
//                    out.close();
//                    System.exit(0);
//                }
//            }
//        } catch (InputMismatchException e) {
//            System.err.println("Bad choice. Try again.. or quit 'Q'");
//        } finally {
//            mainFlowControl();
//        }
//    }
//
//    private int getKeyFromUser() {
//
//
//        int key;
//        in.hasNextInt();
//        key = in.nextInt();
//        in.nextLine();
//        return key;
    }
}
