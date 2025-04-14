package banking;

import banking.ui.MainUI;

public class Main {
    public static void main(String[] args) {
        try {
            String fileName = parseFileName(args);
            MainUI main = new MainUI(fileName);
            main.mainMenu();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String parseFileName(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-fileName")) {
                if (i + 1 < args.length) {
                    return args[i + 1];
                } else {
                    throw new IllegalArgumentException("Error: Missing file name after -fileName");
                }
            }
        }
        throw new IllegalArgumentException("Error: -fileName option not found");
    }
}